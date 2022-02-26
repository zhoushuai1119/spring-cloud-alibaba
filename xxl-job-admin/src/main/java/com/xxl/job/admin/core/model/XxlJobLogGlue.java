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
 * xxl-job log for glue, used to track job code process
 * @author xuxueli 2016-5-19 17:57:46
 */
@Data
@TableName("xxl_job_logglue")
public class XxlJobLogGlue extends Model<XxlJobLogGlue> {

	/**
	 * 主键ID
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 任务，主键ID
	 */
	@TableField("job_id")
	private int jobId;
	/**
	 * GLUE类型 #com.xxl.job.core.glue.GlueTypeEnum
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
	 * 创建时间
	 */
	@TableField("add_time")
	private Date addTime;
	/**
	 * 更新时间
	 */
	@TableField("update_time")
	private Date updateTime;

}
