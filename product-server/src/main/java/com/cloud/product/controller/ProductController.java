package com.cloud.product.controller;


import com.cloud.common.beans.exception.BusinessException;
import com.cloud.common.enums.ErrorCodeEnum;
import com.cloud.platform.common.domain.response.BaseResponse;
import com.cloud.product.domain.entity.Product;
import com.cloud.product.service.DictService;
import com.cloud.product.service.ProductService;
import io.seata.core.context.RootContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
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

    /**
     * 保存产品信息
     * @return
     */
    @PostMapping("save")
    public BaseResponse saveProduct(){
        productService.saveProduct();
        return BaseResponse.createSuccessResult(null);
    }

    /**
     * 获取产品信息
     * @return
     */
    @PostMapping("detail")
    public BaseResponse<Product> getProductInfo(Long productId){
        Product product = productService.getProduct(productId);
        return BaseResponse.createSuccessResult(product);
    }

    /**
     * 保存字典信息
     * @return
     */
    @PostMapping("/dict/save")
    public BaseResponse saveDict(){
        dictService.saveDict();
        return BaseResponse.createSuccessResult(null);
    }

    /**
     *
     * seata测试
     * @return
     */
    @PostMapping("/seata/test")
    public BaseResponse seataTest(){
        log.info("seata test .....");
        String xid = RootContext.getXID();
        log.info("seataTest全局事务ID:{}",xid);
        dictService.saveDict();
        if (true) {
            throw new BusinessException(ErrorCodeEnum.NOT_TASK_APPROVAL_PERSON);
        }
        return BaseResponse.createSuccessResult(null);
    }

}

