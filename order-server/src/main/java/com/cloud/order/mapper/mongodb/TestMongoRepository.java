package com.cloud.order.mapper.mongodb;

import com.cloud.order.domain.dto.MongoTestDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/1 17:49
 * @version: v1
 */
@Repository
public interface TestMongoRepository extends MongoRepository<MongoTestDTO,Long> {


}
