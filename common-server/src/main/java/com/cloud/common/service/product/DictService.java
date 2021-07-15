package com.cloud.common.service.product;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.common.entity.product.Dict;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/20 9:22
 * @version: V1.0
 */
public interface DictService extends IService<Dict> {

    void saveDict();

    void sendMessage(String content);

}
