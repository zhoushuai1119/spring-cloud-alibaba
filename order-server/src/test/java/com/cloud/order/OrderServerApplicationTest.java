package com.cloud.order;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cloud.order.service.CategoryService;
import com.github.dozermapper.core.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServerApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CategoryService categoryService;

    @Resource
    private Mapper mapper;


    @Test
    public void testKey() {
        stringRedisTemplate.opsForValue().set("name", "周帅");
    }


    @Test
    public void getSpringVersion() {
        String version = SpringVersion.getVersion();
        String version1 = SpringBootVersion.getVersion();
        System.out.println(version);
        System.out.println(version1);
    }


    @Test
    public void updateTableTest() {
        //测试全表更新插件
        categoryService.remove(new UpdateWrapper<>());
    }


    @Test
    public void supplyAsyncTest() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "supplyAsync=>Integer");
            //int i = 10 / 0;
            return 200;
        });

        Integer x = completableFuture.whenComplete((r, e) -> {
            log.info("r-> " + r);//正常的返回结果
            log.info("e-> " + e);//错误信息
        }).exceptionally((error) -> {
            log.info("error msg : {}", error.getMessage());
            return 404;
        }).get();

        log.info("result : {}", x);
    }

}

