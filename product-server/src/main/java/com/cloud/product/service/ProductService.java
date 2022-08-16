package com.cloud.product.service;

import com.cloud.product.domain.dto.PurchaseProductDTO;
import com.cloud.product.domain.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-09
 */
public interface ProductService extends IService<Product> {

    /**
     * 保存产品信息
     */
    void saveProduct();

    /**
     * 获取产品信息
     *
     * @param productId
     * @return
     */
    Product getProduct(Long productId);

    /**
     * 购买产品扣减库存
     *
     * @param purchaseProductDTO
     */
    void purchaseProduct(PurchaseProductDTO purchaseProductDTO);

}
