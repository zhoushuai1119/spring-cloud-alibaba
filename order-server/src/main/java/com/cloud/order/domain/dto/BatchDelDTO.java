package com.cloud.order.domain.dto;

import com.cloud.platform.common.domain.dto.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/5/6 15:19
 * @version: v1
 */
@Data
public class BatchDelDTO extends BaseDTO {

    private static final long serialVersionUID = -206352555527430626L;

    @NotEmpty(message = "categoryIdList不能为空")
    private List<String> categoryIdList;

}
