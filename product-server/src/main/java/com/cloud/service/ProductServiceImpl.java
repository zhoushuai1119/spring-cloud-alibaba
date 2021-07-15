package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.product.Product;
import com.cloud.common.service.product.ProductService;
import com.cloud.dao.ProductMapper;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/19 10:45
 * @version: V1.0
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public void saveProduct() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setProductName("产品"+i);
            product.setPrice(new BigDecimal(10.00));
            product.setProductType(i+1);
            product.setCreateTime(LocalDateTime.now());
            save(product);
        }
    }

    @Override
    public Product getProduct(Long productId) {
        return getById(productId);
    }
}
