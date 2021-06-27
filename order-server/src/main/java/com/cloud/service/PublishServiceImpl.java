package com.cloud.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-05-28 14:34
 */
@Service
public class PublishServiceImpl implements PublishService {

    @Resource
    private ApplicationEventPublisher publisher;

    @Override
    public void test() {
        publisher.publishEvent("list()");
    }

}
