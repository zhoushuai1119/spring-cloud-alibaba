package com.cloud.order;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cloud.order.domain.dto.MongoTestDTO;
import com.cloud.order.domain.dto.MongoTestV2DTO;
import com.cloud.order.domain.entity.Category;
import com.cloud.order.enums.CategoryTypeEnum;
import com.cloud.order.mapper.elasticsearch.CategoryElasticRepository;
import com.cloud.order.mapper.mongodb.TestMongoRepository;
import com.cloud.order.service.CategoryService;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.SpringVersion;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServerApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private CategoryElasticRepository categoryElasticRepository;

    @Autowired
    private TestMongoRepository testMongoRepository;

    @Autowired
    private CategoryService categoryService;

    @Resource
    private Mapper mapper;


    @Test
    public void testKey() {
        stringRedisTemplate.opsForValue().set("name","周帅");
    }


    @Test
    public void getSpringVersion() {
        String version = SpringVersion.getVersion();
        String version1 = SpringBootVersion.getVersion();
        System.out.println(version);
        System.out.println(version1);
    }


    @Test
    public void testMongoSave() {
        MongoTestDTO mongoTestDTO = new MongoTestDTO();
        mongoTestDTO.setId(6266L);
        mongoTestDTO.setName("test");
        mongoTestDTO.setAge(30);
        mongoTestDTO.setAddress("上海市青浦区");
        mongoTestDTO.setTime("2021-12-31");
        mongoTestDTO.setLocalDate(new Date());
        mongoTestDTO.setLocalDateTime(LocalDateTime.now());
        testMongoRepository.save(mongoTestDTO);
    }


    @Test
    public void testListCopy() {
        List<MongoTestDTO> list = Lists.newArrayList();
        MongoTestDTO mongoTestDTO = new MongoTestDTO();
        mongoTestDTO.setId(6266L);
        mongoTestDTO.setName("test");
        mongoTestDTO.setAge(30);
        mongoTestDTO.setAddress("上海市青浦区");
        mongoTestDTO.setTime("2021-12-31");
        mongoTestDTO.setLocalDate(new Date());
        mongoTestDTO.setLocalDateTime(LocalDateTime.now());
        list.add(mongoTestDTO);

        List<MongoTestV2DTO> list2 = BeanUtil.copyToList(list,MongoTestV2DTO.class);
        List<MongoTestV2DTO> list3 = Convert.toList(MongoTestV2DTO.class,list);
        System.out.println(list2);
        System.out.println(list3);
        System.out.println(list2 == list3);
        System.out.println(list2.equals(list3));
    }


    @Test
    public void saveCategoryEs() {
        Category category = new Category();
        category.setCategoryId("2dd");
        category.setCategoryName("2ss");
        category.setCategoryLevel(0);
        category.setParentCategoryId("11");
        category.setParentCategoryName("2dd3");
        category.setCategoryType(CategoryTypeEnum.ORDINARY_ARCHIVE);
        categoryElasticRepository.save(category);
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
            int i=10/0;
            return 200;
        });

        Integer x = completableFuture.whenComplete((t, u) -> {
            System.out.println("t-> " + t);//正常的返回结果
            System.out.println("u-> " + u);//错误信息
        }).exceptionally((e) -> {
            System.out.println(e.getMessage());
            return 404;
        }).get();

        System.out.println(x);
    }

}

