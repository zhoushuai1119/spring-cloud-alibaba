package com.cloud.order.domain.dto;

import com.cloud.platform.common.domain.dto.BaseDTO;
import lombok.Data;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/5/5 22:56
 * @version: v1
 */
@Data
public class UserRegisterDTO extends BaseDTO {

    private static final long serialVersionUID = 891172212569235316L;

    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

}
