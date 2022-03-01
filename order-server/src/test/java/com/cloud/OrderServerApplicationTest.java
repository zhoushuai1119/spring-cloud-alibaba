package com.cloud;

import com.cloud.dao.mongodb.TestMongoRepository;
import com.cloud.dto.MongoTestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(properties = {"spring.profiles.active=dev"})
@RunWith(SpringRunner.class)
@Slf4j
public class OrderServerApplicationTest {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TestMongoRepository testMongoRepository;

    @Test
    public void testKey() {
        stringRedisTemplate.opsForValue().set("name","周帅");
    }


    @Test
    public void testMongoSave() {
        MongoTestDTO mongoTestDTO = new MongoTestDTO();
        mongoTestDTO.setId(6266L);
        mongoTestDTO.setName("test");
        mongoTestDTO.setAge(30);
        mongoTestDTO.setAddress("上海市青浦区");

        testMongoRepository.save(mongoTestDTO);
    }

}

