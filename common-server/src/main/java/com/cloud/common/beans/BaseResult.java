package com.cloud.common.beans;

import com.cloud.common.utils.CommonUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p><请求返回对象>
 * <p><请求返回对象>
 * <p>
 * @version [V1.0, 12]
 * @see [相关类/方法]
 */
@Data
public class BaseResult {
    @ApiModelProperty(value = "状态位")
    private boolean flag;
    @ApiModelProperty(value = "编码")
    private int code;
    @ApiModelProperty(value = "信息")
    private String desc;

    public BaseResult() {
        this.flag = true;
        this.code = ReturnCode.SUCCESS.getKey();
        this.desc = ReturnCode.SUCCESS.getValue();
    }

    public BaseResult(int code,String desc,boolean flag) {
        this.flag = flag;
        this.code = code;
        this.desc = desc;
    }

    public BaseResult(String desc) {
        this.flag = true;
        this.code = ReturnCode.SUCCESS.getKey();
        this.desc = desc;
    }

    public BaseResult(int code, String desc) {
        this.flag = false;
        this.code = code;
        this.desc = desc;
    }


    public BaseResult(KeyValuePair keyValuePair) {
        if (keyValuePair.getKey() == 0) {
            this.flag = true;
        } else {
            this.flag = false;
        }
        this.code = keyValuePair.getKey();
        if (CommonUtil.isEmpty(keyValuePair.getDescr())) {
            this.desc = keyValuePair.getValue();
        } else {
            this.desc = keyValuePair.getDescr();
        }
    }

}
