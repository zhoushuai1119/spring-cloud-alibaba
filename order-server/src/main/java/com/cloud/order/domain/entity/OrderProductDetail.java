package com.cloud.order.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>
 * 订单产品详情表
 * </p>
 *
 * @author zhoushuai
 * @since 2022-08-12
 */
@Data
@TableName("order_product_detail")
@ApiModel(value = "OrderProductDetail对象", description = "订单产品详情表")
public class OrderProductDetail extends Model {

    private static final long serialVersionUID = 3063863696756654061L;

    @ApiModelProperty("主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("订单ID")
    @TableField("order_id")
    private String orderId;

    @ApiModelProperty("产品ID")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty("产品数量")
    @TableField("product_quantity")
    private Integer productQuantity;

    @ApiModelProperty("产品单价")
    @TableField("product_price")
    private BigDecimal productPrice;

}
