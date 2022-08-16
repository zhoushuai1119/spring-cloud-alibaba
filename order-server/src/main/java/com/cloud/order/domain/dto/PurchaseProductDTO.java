package com.cloud.order.domain.dto;

import com.cloud.platform.common.domain.dto.BaseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/8/16 9:53
 * @version: v1
 */
@Data
@Builder
@AllArgsConstructor
public class PurchaseProductDTO extends BaseDTO {

    private static final long serialVersionUID = -5799582733583549078L;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 购买的商品列表
     */
    private List<OrderDetailDTO> purchaseProductList;

}
