package com.cloud.order.domain.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/1 17:43
 * @version: v1
 */
@Data
@Document(collection = "test_mongo")
public class MongoTestDTO implements Serializable {

    private static final long serialVersionUID = -5393534928146192660L;

    @Id
    private long id;

    private String name;

    private Integer age;

    private String address;

    private String time;

    private Date localDate;

    private LocalDateTime localDateTime;

}
