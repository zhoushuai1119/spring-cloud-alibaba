package com.cloud.product.service;

import com.cloud.product.domain.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-09
 */
public interface DictService extends IService<Dict> {

    /**
     * 保存字典
     */
    void saveDict();

}
