package com.cloud.order.domain.dto;

import com.cloud.order.enums.OrderWayEnum;
import com.cloud.platform.common.domain.dto.BaseDTO;
import com.cloud.platform.web.aop.annotation.EnumCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description: 下单参数实体类
 * @author: zhou shuai
 * @date: 2022/8/12 14:15
 * @version: v1
 */
@Data
public class OrderParamDTO extends BaseDTO {

    private static final long serialVersionUID = -2848821611812216949L;

    @ApiModelProperty("下单途径(1-app下单；2-web下单；3-小程序下单)")
    @NotNull
    @EnumCheck(clazz = OrderWayEnum.class)
    private Integer orderWay;

    @ApiModelProperty("下单商品明细")
    @NotEmpty(message = "下单商品明细不能为空")
    @Valid
    List<OrderDetailDTO> orderDetailList;

    @ApiModelProperty("订单备注")
    private String remark;

}
