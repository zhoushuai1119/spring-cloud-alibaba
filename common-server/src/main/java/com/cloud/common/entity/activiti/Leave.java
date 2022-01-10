package com.cloud.common.entity.activiti;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@TableName("leave_process")
public class Leave extends Model<Leave> {

    @TableId
    private String id;

    @TableField("APPLY_USER_ID")
    private String applyUserId;

    @TableField("START_TIME")
    private String startTime;

    @TableField("END_TIME")
    private String endTime;

    @TableField("LEAVE_DAYS")
    private Integer leaveDays;

    @TableField("REASON")
    private String reason;

    @TableField("TYPE")
    private String type;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;

}