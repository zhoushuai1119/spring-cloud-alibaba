package com.cloud.product.controller;


import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.product.config.WebRequestConfig;
import com.cloud.product.domain.dto.PurchaseProductDTO;
import com.cloud.product.domain.entity.Product;
import com.cloud.product.service.DictService;
import com.cloud.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * ProductController
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-09
 */
@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private DictService dictService;

    @Autowired
    private WebRequestConfig webRequestConfig;

    /**
     * 新增产品信息
     *
     * @return
     */
    @PostMapping("save")
    public BaseResponse saveProduct() {
        productService.saveProduct();
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 获取产品信息
     *
     * @return
     */
    @GetMapping("detail")
    public BaseResponse<Product> getProductInfo(@RequestParam("productId") Long productId) {
        Product product = productService.getProduct(productId);
        return BaseResponse.createSuccessResult(product);
    }

    /**
     * 购买产品扣减库存
     *
     * @return
     */
    @PostMapping("purchase")
    public BaseResponse<Product> purchaseProduct(@RequestBody @Validated PurchaseProductDTO purchaseProductDTO) {
        String seataXID = webRequestConfig.getSeataXID();
        log.info("******* seataXID:{}", seataXID);
        productService.purchaseProduct(purchaseProductDTO);
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 保存字典信息
     *
     * @return
     */
    @PostMapping("/dict/save")
    public BaseResponse saveDict() {
        dictService.saveDict();
        return BaseResponse.createSuccessResult(null);
    }

}

