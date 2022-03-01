package com.cloud.service;

import com.cloud.dao.mongodb.TestMongoRepository;
import com.cloud.dto.MongoTestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/1 17:51
 * @version: v1
 */
@Service
public class MongoService {

    @Autowired
    private TestMongoRepository testMongoRepository;

    public void save() {
        MongoTestDTO mongoTestDTO = new MongoTestDTO();
        mongoTestDTO.setId(6266L);
        mongoTestDTO.setName("test");
        mongoTestDTO.setAge(30);
        mongoTestDTO.setAddress("上海市青浦区");

        testMongoRepository.save(mongoTestDTO);
    }
}
