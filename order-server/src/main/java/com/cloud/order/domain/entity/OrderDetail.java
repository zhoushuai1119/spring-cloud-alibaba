package com.cloud.order.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author zhoushuai
 * @since 2022-08-12
 */
@Data
@TableName("order_detail")
@ApiModel(value = "Order对象", description = "订单表")
public class OrderDetail extends Model {

    private static final long serialVersionUID = 5358846553568173732L;

    @ApiModelProperty("订单ID")
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("下单途径(1-app下单；2-web下单；3-小程序下单)")
    @TableField("order_way")
    private Integer orderWay;

    @ApiModelProperty("订单总金额")
    @TableField("order_amount")
    private BigDecimal orderAmount;

    @ApiModelProperty("订单备注")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("是否删除（0：正常，1：删除）")
    @TableField("is_deleted")
    @JsonIgnore
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty("下单用户id")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("创建时间")
    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @ApiModelProperty("更新时间")
    @TableField("gmt_modify")
    private LocalDateTime gmtModify;

}
