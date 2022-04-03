package com.cloud.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/1 17:43
 * @version: v1
 */
@Data
public class MapperTestDTO implements Serializable {

    private static final long serialVersionUID = -6385245380010107470L;

    private long id;

    private String name;

    private Integer age;

    private String address;

    private LocalDate time;

    private LocalDateTime localDate;

    private LocalDateTime localDateTime;

}
