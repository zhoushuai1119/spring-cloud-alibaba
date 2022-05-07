package com.cloud.user.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 *  用户表
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-07
 */
@Data
@TableName("user")
@ApiModel(value = "User对象", description = "用户表")
public class User extends Model {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("姓名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("盐")
    @TableField("salt")
    private String salt;

    @ApiModelProperty("角色ID")
    @TableField("role_id")
    private String roleId;

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

    public String getCredentialsSalt() {
        return username + salt;
    }

}
