package com.cloud.order.domain.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/3/17 15:34
 * @version: v1
 */
@Data
public class Car implements Serializable {

    private static final long serialVersionUID = 8877204566682876370L;

    private int maxSpeed;

    private String brand;

    private double price;

}
