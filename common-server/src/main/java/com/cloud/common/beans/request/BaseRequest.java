package com.cloud.common.beans.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-27 16:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseRequest<T> implements Serializable {

    private static final long serialVersionUID = 5561142612592553739L;

    @Valid
    protected T model;

}
