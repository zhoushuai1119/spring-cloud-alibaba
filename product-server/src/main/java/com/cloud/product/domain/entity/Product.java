package com.cloud.product.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-09
 */
@Data
@TableName("t_product")
@ApiModel(value = "Product对象", description = "")
public class Product extends Model {

    @ApiModelProperty("ID")
    @TableId
    private Long id;

    @ApiModelProperty("产品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty("价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("产品类型")
    @TableField("product_type")
    private Integer productType;

    @ApiModelProperty("库存数量")
    @TableField("inventory")
    private Integer inventory;

    @TableLogic
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
