package com.cloud.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dao.DictMapper;
import com.cloud.entity.Dict;
import com.cloud.service.DictService;
import com.cloud.service.NettySendServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/20 9:23
 * @version: V1.0
 */
@Service("dictServiceImpl")
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

  @Autowired
  private NettySendServiceImpl sendService;

  @Override
  public void saveDict() {
    Dict dict = new Dict();
    dict.setCode("HZP");
    dict.setName("化妆品");
    dict.setLxjp("HZ");
    save(dict);
  }

  @Override
  public void sendMessage(String content) {
    for (int i = 0; i < 5; i++) {
      sendService.sendMessage(content);
    }
  }
}
