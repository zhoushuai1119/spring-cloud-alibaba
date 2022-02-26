package com.xxl.job.admin.core.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.google.common.base.Splitter;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Data
@TableName("xxl_job_group")
public class XxlJobGroup extends Model<XxlJobGroup>  {

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 执行器AppName
     */
    @TableField("app_name")
    private String appname;
    /**
     * 执行器名称
     */
    @TableField("title")
    private String title;
    /**
     * 执行器地址类型：0=自动注册、1=手动录入
     */
    @TableField("address_type")
    private int addressType;
    /**
     * 执行器地址列表，多地址逗号分隔
     */
    @TableField("address_list")
    private String addressList;
    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 执行器地址列表(系统注册)
     */
    @TableField(exist = false)
    private List<String> registryList;

    public List<String> getRegistryList() {
        if (StringUtils.isNotBlank(addressList)) {
            registryList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(addressList);
        }
        return registryList;
    }

}
