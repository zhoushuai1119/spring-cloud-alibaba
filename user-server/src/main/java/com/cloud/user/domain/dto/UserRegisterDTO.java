package com.cloud.user.domain.dto;

import com.cloud.platform.common.domain.dto.BaseDTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/5/5 22:56
 * @version: v1
 */
@Data
public class UserRegisterDTO extends BaseDTO {

    private static final long serialVersionUID = -4199986833307574962L;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
    /**
     * 盐
     */
    private String salt;

    public String getCredentialsSalt() {
        return username + salt;
    }

}
