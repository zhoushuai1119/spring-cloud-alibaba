package com.cloud.product.domain.dto;

import com.cloud.platform.common.domain.dto.BaseDTO;
import lombok.Data;

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
    private Long productId;

    /**
     * 产品数量
     */
    private Integer productQuantity;

}
