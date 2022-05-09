package com.cloud.product.service.impl;

import com.cloud.product.domain.entity.Dict;
import com.cloud.product.mapper.DictMapper;
import com.cloud.product.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-09
 */
@Service
public class DictServiceImp extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Override
    public void saveDict() {
        Dict dict = new Dict();
        dict.setCode("HZP");
        dict.setName("化妆品");
        dict.setLxjp("HZ");
        save(dict);
    }

}
