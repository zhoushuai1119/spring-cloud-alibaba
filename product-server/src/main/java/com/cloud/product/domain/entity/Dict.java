package com.cloud.product.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-09
 */
@Data
@TableName("t_dict")
@ApiModel(value = "Dict对象", description = "")
public class Dict extends Model {

    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("字典code")
    @TableField("code")
    private String code;

    @ApiModelProperty("字典名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("类型简拼")
    @TableField("lxjp")
    private String lxjp;


}
