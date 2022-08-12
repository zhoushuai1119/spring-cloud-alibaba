package com.cloud.order.domain.dto;

import com.cloud.platform.common.domain.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @description: 下单商品明细
 * @author: zhou shuai
 * @date: 2022/8/12 14:29
 * @version: v1
 */
@Data
public class OrderDetailDTO extends BaseDTO {

    @ApiModelProperty("订单ID")
    private String orderId;

    @ApiModelProperty("产品ID")
    @NotNull(message = "产品ID不能为空")
    private Long productId;

    @ApiModelProperty("产品数量")
    @NotNull(message = "产品数量不能为空")
    private Integer productQuantity;

    @ApiModelProperty("产品单价")
    @NotNull(message = "产品单价不能为空")
    private BigDecimal productPrice;

}
