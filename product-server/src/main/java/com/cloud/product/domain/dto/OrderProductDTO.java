package com.cloud.product.domain.dto;

import com.cloud.platform.common.domain.dto.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 下单商品明细
 * @author: zhou shuai
 * @date: 2022/8/12 14:29
 * @version: v1
 */
@Data
public class OrderProductDTO extends BaseDTO {

    private static final long serialVersionUID = -7272473046957904432L;

    /**
     * 产品ID
     */
    @NotNull(message = "productId不能为空")
    private Long productId;

    /**
     * 产品数量
     */
    @NotNull(message = "productQuantity不能为空")
    private Integer productQuantity;

}
