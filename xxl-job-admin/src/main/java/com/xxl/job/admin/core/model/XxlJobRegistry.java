package com.xxl.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by xuxueli on 16/9/30.
 */
@Data
@TableName("xxl_job_registry")
public class XxlJobRegistry extends Model<XxlJobRegistry> {

    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * registryGroup
     */
    @TableField("registry_group")
    private String registryGroup;
    /**
     * registryKey
     */
    @TableField("registry_key")
    private String registryKey;
    /**
     * registryValue
     */
    @TableField("registry_value")
    private String registryValue;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

}
