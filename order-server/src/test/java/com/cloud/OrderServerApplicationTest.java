package com.cloud;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cloud.dao.elasticsearch.CategoryElasticRepository;
import com.cloud.dao.mongodb.TestMongoRepository;
import com.cloud.dto.MapperTestDTO;
import com.cloud.dto.MongoTestDTO;
import com.cloud.dto.test.*;
import com.cloud.entity.Category;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.service.CategoryService;
import com.github.dozermapper.core.Mapper;
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


//@SpringBootTest(properties = {"spring.profiles.active=dev"})
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

    @Autowired
    private TestA testA;

    @Autowired
    private TestB testB;

    @Autowired
    private TestC testC;

    @Autowired
    private TestD testD;

    @Autowired
    private TestE testE;

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

        MapperTestDTO mapperTest = mapper.map(mongoTestDTO,MapperTestDTO.class);
        log.info("mapperTest:{}", JsonUtil.toString(mapperTest));
        //testMongoRepository.save(mongoTestDTO);
    }


    @Test
    public void saveCategoryEs() {
        Category category = new Category();
        category.setId(10);
        category.setCategoryId("2dd");
        category.setCategoryName("2ss");
        category.setCategoryLevel(0);
        category.setParentCategoryId("11");
        category.setParentCategoryName("2dd3");
        categoryElasticRepository.save(category);
    }

    @Test
    public void updateTableTest() {
        //测试全表更新插件
        categoryService.remove(new UpdateWrapper<>());
    }

    @Test
    public void testA() {
        testA.print();
    }

    @Test
    public void testB() {
        testB.print();
    }

    @Test
    public void testC() {
        testC.print();
    }

    @Test
    public void testD() {
        testD.print();
    }

    @Test
    public void testE() {
        testE.print();
    }

}

