package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.entity.Product;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/7 17:17
 * @version: V1.0
 */
public interface ProductService extends IService<Product> {

    void saveProduct();

    Product getProduct(Long productId);

}
