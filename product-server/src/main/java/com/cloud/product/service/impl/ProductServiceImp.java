package com.cloud.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.beans.exception.BusinessException;
import com.cloud.common.config.EnvConfig;
import com.cloud.common.constants.CommonConstant;
import com.cloud.common.utils.RedisLockKeyUtil;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.product.common.exception.ProductErrorCodeEnum;
import com.cloud.product.domain.dto.OrderProductDTO;
import com.cloud.product.domain.dto.PurchaseProductDTO;
import com.cloud.product.domain.entity.Product;
import com.cloud.product.mapper.ProductMapper;
import com.cloud.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-09
 */
@Service
@Slf4j
public class ProductServiceImp extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Resource
    private RedissonClient redissonClient;

    @Autowired
    private EnvConfig envConfig;

    /**
     * 保存产品信息
     */
    @Override
    public void saveProduct() {
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setProductName("产品" + i);
            product.setPrice(new BigDecimal(10.00));
            product.setProductType(i + 1);
            product.setInventory(100);
            save(product);
        }
    }

    /**
     * 获取产品信息
     *
     * @param productId
     * @return
     */
    @Override
    public Product getProduct(Long productId) {
        return getById(productId);
    }

    /**
     * 购买产品扣减库存
     *
     * @param purchaseProductDTO
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void purchaseProduct(PurchaseProductDTO purchaseProductDTO) {
        log.info("purchaseProduct parms : {}", JsonUtil.toString(purchaseProductDTO));
        //redis分布式锁的lockKey
        String lockKey = RedisLockKeyUtil.getLockKey(CommonConstant.SystemCode.PRODUCT_SERVER, envConfig.getEnv(),
                CommonConstant.RedisLockKey.PURCHASE_PRODUCT_KEY,
                purchaseProductDTO.getOrderId());
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            List<OrderProductDTO> purchaseProductList = purchaseProductDTO.getPurchaseProductList();
            purchaseProductList.forEach(purchaseProduct -> {
                Product product = getById(purchaseProduct.getProductId());
                if (product.getInventory() < purchaseProduct.getProductQuantity()) {
                    throw new BusinessException(ProductErrorCodeEnum.PRODUCT_INVENTORY_INSUFFICIENT_ERROR);
                }
                UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
                updateWrapper.set("inventory", product.getInventory() - purchaseProduct.getProductQuantity());
                updateWrapper.eq("id", purchaseProduct.getProductId());
                baseMapper.update(null, updateWrapper);
            });
        } finally {
            lock.unlock();
        }
    }

}
