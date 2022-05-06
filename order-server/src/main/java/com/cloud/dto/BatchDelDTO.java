package com.cloud.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/5/6 15:19
 * @version: v1
 */
@Data
public class BatchDelDTO implements Serializable {

    @NotEmpty(message = "categoryIdList不能为空")
    private List<String> categoryIdList;

}
