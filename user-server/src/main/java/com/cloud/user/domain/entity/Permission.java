package com.cloud.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *  权限表
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-07
 */
@Getter
@Setter
@TableName("permission")
@ApiModel(value = "Permission对象", description = "")
public class Permission extends Model {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("权限操作")
    @TableField("permission")
    private String permission;

    @ApiModelProperty("角色ID")
    @TableField("role_id")
    private Long roleId;


}
