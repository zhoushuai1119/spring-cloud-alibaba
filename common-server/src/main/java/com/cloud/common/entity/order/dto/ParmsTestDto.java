package com.cloud.common.entity.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/8 15:21
 * @version: V1.0
 */
@Data
public class ParmsTestDto implements Serializable {

    @NotBlank
    private String aaa;

    @NotEmpty
    private String bbb;

    @NotNull
    private Integer ccc;

}
