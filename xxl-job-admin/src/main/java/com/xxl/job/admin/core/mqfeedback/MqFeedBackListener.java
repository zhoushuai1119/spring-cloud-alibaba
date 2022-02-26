package com.xxl.job.admin.core.mqfeedback;

import com.cloud.annotation.ConsumeTopic;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.utils.JsonUtil;
import com.cloud.core.TopicListener;
import com.cloud.dto.CloudMessage;
import com.cloud.timedjob.TimeBasedJobFeedback;
import com.xxl.job.admin.core.model.XxlJobLog;
import com.xxl.job.admin.dao.XxlJobLogDao;
import com.xxl.job.admin.service.ElasticsearchService;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/12 16:26
 * @version: v1
 */
@Slf4j
@ConsumeTopic(topic = CommonConstant.FeedBackTopic.FEEDBACK_TASK_TOPIC,
        eventCode = CommonConstant.FeedBackTopic.FEEDBACK_TASK_EVENTCODE, log = true)
public class MqFeedBackListener implements TopicListener<TimeBasedJobFeedback> {

    @Autowired
    private XxlJobLogDao xxlJobLogDao;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    public void onMessage(CloudMessage<TimeBasedJobFeedback> feedbackMessage) {
        TimeBasedJobFeedback timeBasedJobFeedback = feedbackMessage.getPayload();
        if (Objects.isNull(timeBasedJobFeedback)) {
            return;
        }
        log.info("接收到定时任务回调消息:{}", JsonUtil.toString(timeBasedJobFeedback));
        long xxlJobLogId = timeBasedJobFeedback.getLogId();
        if (Objects.nonNull(xxlJobLogId)) {
            XxlJobLog xxlJobLog = xxlJobLogDao.selectById(xxlJobLogId);
            if (Objects.isNull(xxlJobLog)) {
                log.error("XxlJobLog is empty, xxlJobLogId: {}", xxlJobLogId);
                return;
            }
            xxlJobLog.setCallbackTime(new Date());
            if (timeBasedJobFeedback.getSuccess()) {
                xxlJobLog.setCallbackCode(ReturnT.SUCCESS_CODE);
            } else {
                xxlJobLog.setCallbackCode(ReturnT.FAIL_CODE);
                xxlJobLog.setCallbackMsg(timeBasedJobFeedback.getMsg());
            }
            xxlJobLogDao.updateCallbackInfo(xxlJobLog);

            //创建索引
            //elasticsearchService.createIndex(XxlJobLog.class);
            elasticsearchService.saveJobLog(xxlJobLog);
            log.info("xxlJobLogId:{}存入elasticsearch成功", xxlJobLogId);

        }
    }

}
