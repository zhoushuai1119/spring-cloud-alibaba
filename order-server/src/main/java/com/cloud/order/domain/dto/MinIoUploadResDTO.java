package com.cloud.order.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/6/24 11:21
 * @version: v1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MinIoUploadResDTO implements Serializable {

    private static final long serialVersionUID = -1821099029132922618L;
    /**
     * 文件名
     */
    private String minFileName;
    /**
     * 文件地址
     */
    private String minFileUrl;

}
