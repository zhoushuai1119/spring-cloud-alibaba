package com.cloud.dto;

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

    private static final long serialVersionUID = 467804102089578526L;

    @NotBlank
    private String aaa;

    @NotEmpty
    private String bbb;

    @NotNull
    private Integer ccc;

}
