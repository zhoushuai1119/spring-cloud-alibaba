package com.xxl.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * xxl-job info
 *
 * @author xuxueli  2016-1-12 18:25:49
 */
@Data
@TableName("xxl_job_info")
public class XxlJobInfo extends Model<XxlJobInfo> {

	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 执行器主键ID
	 */
	@TableField("job_group")
	private int jobGroup;
	/**
	 * 任务描述
	 */
	@TableField("job_desc")
	private String jobDesc;
	/**
	 * 添加时间
	 */
	@TableField("add_time")
	private Date addTime;
	/**
	 * 更新时间
	 */
	@TableField("update_time")
	private Date updateTime;
	/**
	 * 负责人
	 */
	@TableField("author")
	private String author;
	/**
	 * 报警邮件
	 */
	@TableField("alarm_email")
	private String alarmEmail;
	/**
	 * 调度类型
	 */
	@TableField("schedule_type")
	private String scheduleType;
	/**
	 * 调度配置，值含义取决于调度类型
	 */
	@TableField("schedule_conf")
	private String scheduleConf;
	/**
	 * 调度过期策略
	 */
	@TableField("misfire_strategy")
	private String misfireStrategy;
	/**
	 * 执行器路由策略
	 */
	@TableField("executor_route_strategy")
	private String executorRouteStrategy;
	/**
	 * 执行器，任务Handler名称
	 */
	@TableField("executor_handler")
	private String executorHandler;
	/**
	 *  执行器，任务参数
	 */
	@TableField("executor_param")
	private String executorParam;
	/**
	 * 系统编码
	 */
	@TableField("system_code")
	private String systemCode;
	/**
	 * 阻塞处理策略
	 */
	@TableField("executor_block_strategy")
	private String executorBlockStrategy;
	/**
	 * 任务执行超时时间，单位秒
	 */
	@TableField("executor_timeout")
	private int executorTimeout;
	/**
	 * 失败重试次数
	 */
	@TableField("executor_fail_retry_count")
	private int executorFailRetryCount;
	/**
	 * 运行模式 GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
	 */
	@TableField("glue_type")
	private String glueType;
	/**
	 * GLUE源代码
	 */
	@TableField("glue_source")
	private String glueSource;
	/**
	 * GLUE备注
	 */
	@TableField("glue_remark")
	private String glueRemark;
	/**
	 * GLUE更新时间
	 */
	@TableField("glue_updatetime")
	private Date glueUpdatetime;
	/**
	 * 子任务ID
	 */
	@TableField("child_jobid")
	private String childJobId;
	/**
	 * 调度状态：0-停止，1-运行
	 */
	@TableField("trigger_status")
	private int triggerStatus;
	/**
	 * 上次调度时间
	 */
	@TableField("trigger_last_time")
	private long triggerLastTime;
	/**
	 * 下次调度时间
	 */
	@TableField("trigger_next_time")
	private long triggerNextTime;

}
