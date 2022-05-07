package com.cloud.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *  角色表
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-07
 */
@Data
@TableName("role")
@ApiModel(value = "Role对象", description = "")
public class Role extends Model {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("角色名")
    @TableField("role_name")
    private String roleName;

    @TableLogic //标注为逻辑删除字段
    @ApiModelProperty("是否删除（0：正常，1：删除）")
    @TableField("is_deleted")
    private Boolean isDeleted;

    @ApiModelProperty("创建时间")
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField("gmt_modify")
    private LocalDateTime gmtModify;


}
