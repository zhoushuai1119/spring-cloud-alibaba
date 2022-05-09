package com.cloud.product.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.product.domain.entity.Product;
import com.cloud.product.mapper.ProductMapper;
import com.cloud.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-09
 */
@Service
public class ProductServiceImp extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public void saveProduct() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setProductName("产品"+i);
            product.setPrice(new BigDecimal(10.00));
            product.setProductType(i+1);
            save(product);
        }
    }

    @Override
    public Product getProduct(Long productId) {
        return getById(productId);
    }

}
