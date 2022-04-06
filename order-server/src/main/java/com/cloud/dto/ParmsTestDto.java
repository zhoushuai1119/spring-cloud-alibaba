package com.cloud.dto;

import com.cloud.enums.EnumCheckTest;
import com.cloud.enums.OrderEnum;
import com.cloud.platform.web.aop.annotation.EnumCheck;
import com.cloud.platform.web.pattern.RegRule;
import com.cloud.platform.web.validate.Save;
import com.cloud.platform.web.validate.Update;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/2/8 15:21
 * @version: V1.0
 */
@Data
public class ParmsTestDto implements Serializable {

    private static final long serialVersionUID = 467804102089578526L;

    @NotBlank(groups = {Save.class})
    private String str;

    @Email(message = "不是邮件格式", groups = {Save.class})
    private String email;

    @Pattern(regexp = RegRule.MOBILE, message = "手机号格式不正确", groups = {Save.class})
    private String mobile;

    @Length(max = 10, min = 3, groups = {Save.class})
    private String length;

    @NotNull(groups = {Save.class})
    @Max(value = 6, groups = {Save.class})
    private Integer max;

    @NotNull(groups = {Save.class})
    private Integer groupTest;

    @Valid
    //@NotNull(groups = {TestGroup.class})
    private ParmsTestDto parmsTestDto;

    /**
     * {@link com.cloud.enums.OrderEnum}
     */
    @EnumCheck(clazz = EnumCheckTest.class, groups = {Update.class})
    private Integer checkEnum;

    private LocalDateTime localDateTime;

    private LocalDate localDate;

    private Date date;

    private OrderEnum order;

    /**
     * 当前登录人姓名
     */
    private String currentUserName;

}
