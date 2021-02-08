package com.cloud.common.entity.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "aaa不能为空")
    private String aaa;

    @NotBlank(message = "aaa不能为空")
    private String bbb;

    @NotNull(message = "aaa不能为空")
    private Integer ccc;

}
