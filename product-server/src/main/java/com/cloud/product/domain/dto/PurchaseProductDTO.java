package com.cloud.product.domain.dto;

import com.cloud.platform.common.domain.dto.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/8/16 9:53
 * @version: v1
 */
@Data
public class PurchaseProductDTO extends BaseDTO {

    private static final long serialVersionUID = -5799582733583549078L;

    /**
     * 订单ID
     */
    @NotBlank(message = "orderId不能为空")
    private String orderId;

    /**
     * 购买的商品列表
     */
    @NotEmpty(message = "商品列表不能为空")
    private List<OrderProductDTO> purchaseProductList;

}
