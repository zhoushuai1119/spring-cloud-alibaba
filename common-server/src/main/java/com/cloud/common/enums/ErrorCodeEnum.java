package com.cloud.common.enums;

import com.cloud.common.beans.exception.BaseExceptionCode;
import lombok.Getter;

/**
 * 错误信息enum
 *
 * @Author 施文
 * @Date 2019/06/23
 * @Time 13:46:19
 */
@Getter
public enum ErrorCodeEnum implements BaseExceptionCode {

    /**
     * 成功
     */
    SUCCESS("000000", "success"),

    /**
     * 系统错误
     */
    SYSTEM_ERROR("100000", "system error"),

    /**
     * 参数错误
     */
    PARAM_ERROR("100001", "参数错误"),

    /**
     * Json解析错误
     */
    JSON_PARSER_ERROR("100003", "json parse error"),

    ENTITY_NOT_FOUND("100100", "未找到实体类"),
    BILL_NOT_FOUND("200100", "未找到单"),
    CALL_MSG_FAILED("CALL_MSG_FAILED", "调用msg-ms失败"),
    CALL_CPN_FAILED("CALL_CPN_FAILED", "调用cpn-ms失败"),
    CALL_OBS_FAILED("CALL_OBS_FAILED", "调用obs-ms失败"),
    CALL_PMS_FAILED("CALL_PMS_FAILED", "调用pms-ms失败"),
    CALL_PRD_FAILED("CALL_PMS_FAILED", "调用prd-ms失败"),
    CALL_CAM_FAILED("CALL_CAM_FAILED", "调用cam-ms失败"),
    CALL_CPM_FAILED("CALL_CPM_FAILED", "调用cpm-ms失败"),
    CALL_ACT_FAILED("CALL_ACT_FAILED", "调用act-ms失败"),
    CALL_SOS_FAILED("CALL_SOS_FAILED", "调用sos-ms失败"),
    CALL_OPB_FAILED("CALL_OPB_FAILED", "调用opb-ms失败"),
    CALL_BAS_FAILED("CALL_BAS_FAILED", "调用bas-ms失败"),
    CALL_EAQ_FAILED("CALL_EAQ_FAILED", "调用eaq-ms失败"),
    CALL_ABO_FAILED("CALL_ABO_FAILED", "调用abo-ms失败"),
    CALL_ABQ_FAILED("CALL_ABQ_FAILED", "调用abq-ms失败"),
    CALL_ACQ_FAILED("CALL_ACQ_FAILED", "调用acq-ms失败"),
    CALL_ASQ_FAILED("CALL_ASQ_FAILED", "调用asq-ms失败"),
    CALL_EMS_FAILED("CALL_EMS_FAILED", "调用ems-ms失败"),
    CALL_ABC_FAILED("CALL_ABC_FAILED", "调用abc-ms失败"),
    CALL_AIQ_FAILED("CALL_ABC_FAILED", "调用aiq-ms失败"),
    CALL_WOM_FAILED("CALL_WOM_FAILED", "调用wom-ms失败"),
    CALL_LMS_FAILED("CALL_LMS_FAILED", "调用lms-ms失败"),
    CALL_AAS_FAILED("CALL_AAS_FAILED", "调用aas-ms失败"),
    CALL_MBS_FAILED("CALL_MBS_FAILED", "调用mbs-ms失败"),
    CALL_SLS_FAILED("CALL_SLS_FAILED", "调用sls-ms失败"),
    CALL_BMS_FAILED("CALL_BMS_FAILED", "调用bms-ms失败"),
    CALL_FLS_FAILED("CALL_FLS_FAILED", "调用fls-ms失败"),
    CALL_ABM_FAILED("CALL_ABM_FAILED", "调用abm-ms失败"),
    CALL_BOS_FAILED("CALL_BOS_FAILED", "调用bos-ms失败"),
    CALL_PBM_FAILED("CALL_PBM_FAILED", "调用pbm-ms失败"),
    CALL_DOT_FAILED("CALL_DOT_FAILED", "调用dot-ms失败"),
    CALL_DOM_FAILED("CALL_DOM_FAILED", "调用dom-ms失败"),
    CALL_NPS_FAILED("CALL_NPS_FAILED", "调用人员系统失败"),
    ABO_NOT_RESULT("DEVICE_CATEGORY_NOT_FOUND", "向abo发起执行单,没有返回结果"),
    DEVICE_CATEGORY_NOT_FOUND("DEVICE_CATEGORY_NOT_FOUND", "设备类型不存在"),

    COUPON_NUMBER_OUT_OF_LIMIT("COUPON_NUMBER_OUT_OF_LIMIT", "发券数量超过限制"),
    DEVICE_NUMBER_OUT_OF_LIMIT("COUPON_NUMBER_OUT_OF_LIMIT", "设备数量超过限制"),
    DEPARTMENT_NOT_EXISTS("DEPARTMENT_NOT_EXISTS", "组织不存在"),
    AGENT_NOT_SUPPORTED("AGENT_NOT_SUPPORTED", "渠道暂不支持该功能"),
    EBOSS_AMOUNT_GROUP_NOT_SUPPORT("EBOSS_AMOUNT_GROUP_NOT_SUPPORT", "该功能不支持同一组织同时增减操作"),
    EBOSS_AMOUNT_NOT_SUPPORT("EBOSS_AMOUNT_NOT_SUPPORT", "扣除金额需小于当前可配置金额"),
    EBOSS_AMOUNT_NOT_EQUALS("EBOSS_AMOUNT_NOT_EQUALS", "扣除总金额需等于当前新增总金额"),
    PERMISSION_DENIED("PERMISSION_DENIED", "当前登录人无操作权限"),
    SENDING_AMOUNT_OUT_OF_LIMIT("SENDING_AMOUNT_OUT_OF_LIMIT", "发券金额超限"),
    REPORT_OUT_OF_LIMIT("SENDING_AMOUNT_OUT_OF_LIMIT", "今日报备超限,请明天再来"),
    DEVICE_HAS_OFFLINE_REPORT("DEVICE_HAS_OFFLINE_REPORT", "设备有待审批离线报备单,解绑该设备可以解除该状态"),
    DEVICE_HAS_ACTIVE_REPORT("DEVICE_HAS_OFFLINE_REPORT", "设备有生效中的离线报备单"),
    DEVICE_NO_PASS("DEVICE_NO_PASS", "设备校验没通过,请重新校验"),
    LONG_REPORT_HAS_FILE("LONG_REPORT_HAS_FILE", "长期报备需要凭证,请上传"),
    SYSTEM_NOW_START("SYSTEM_NOW_START", "系统正在发版,请15分钟后重试"),
    CALL_UCS_FAILED("CALL_UCS_FAILED", "调用ucs-ms失败"),
    NOT_EM_USER("NOT_EM_USER", "该手机号尚未注册怪兽用户"),
    BD_NOT_EXISTS("BD_NOT_EXISTS", "BD不存在"),
    ALLOC_AMOUNT_OUT_OF_LIMIT("ALLOC_AMOUNT_OUT_OF_LIMIT", "派发金额超限"),
    CANNOT_SEND_TO_BD("CANNOT_SEND_TO_BD", "优惠券不能发送给BD"),
    NO_ACCEPTOR("NO_ACCEPTOR", "没有指定金额接收派发者"),
    INVALID_FILE_TYPE("INVALID_FILE_TYPE", "文件格式有误"),
    FAIL_COMPLETE_CHECK("FAIL_COMPLETE_CHECK", "上月完成额尚未生成，请确认生成后再试"),

    EXCEL_ROWS_OUT_OF_LIMIT("EXCEL_ROWS_OUT_OF_LIMIT", "数据量过大，请分批导入"),
    DATA_ROWS_OUT_OF_LIMIT("DATA_ROWS_OUT_OF_LIMIT", "数据量过大，请分批"),
    INVALID_FILE("INVALID_FILE", "文件格式非法"),
    MORE_AGENT_NO("MORE_AGENT_NO", "该组织对应了多个渠道商编码"),
    NO_INTO_AGENT("NO_INTO_AGENT", "没有录入渠道商系统"),
    NO_HAS_BATCH("NO_HAS_BATCH", "当前渠道商无优惠券额度"),
    PASS_MAX_AMOUNT_AGENT("PASS_MAX_AMOUNT_AGENT", "超过接收方本月最高可被派发金额"),
    NO_RECORD_MOUTH_AGENT("NO_RECORD_MOUTH_AGENT", "当前没有这个组织金额数据"),
    TRANSFER_AMOUNT_NOT_SUPPORTED("TRANSFER_AMOUNT_NOT_SUPPORTED", "操作失败，当前组织可配置金额不足，请刷新后重试"),
    CHANNEL_CODE_ERROR("CHANNEL_CODE_ERROR", "解析错误"),
    AGENT_CAN_USER("AGENT_CAN_USER", "该功能不支持直营金额调整"),
    DIRECT_CAN_USER("DIRECT_CAN_USER", "该功能仅支持直营组织金额调整"),
    ASSET_BIND_SHOP_NOT_FOUND("ASSET_BIND_SHOP_NOT_FOUND", "设备绑定门店未找到"),
    ASSET_BIND_SHOP_BD_NOT_FOUND("ASSET_BIND_SHOP_NOT_FOUND", "门店所属BD不存在"),
    ASSET_BIND_SHOP_BD_NOT_AUTH("ASSET_BIND_SHOP_NOT_FOUND", "您无权操作该门店设备解绑"),
    ASSET_UNBIND_APPLY_CREATE_ERROR("ASSET_UNBIND_APPLY_CREATE_ERROR", "设备解绑创建申请单失败"),
    ASSET_UNBIND_MAC_CREATE_ERROR("ASSET_UNBIND_MAC_CREATE_ERROR", "设备已存在有效的申请单，创建申请单失败"),
    GROUP_ININTE_FAIL("GROUP_ININTE_FAIL", "直营组织不支持劵批次初始化"),
    AMOUNT_CHECK_FAIL("AMOUNT_CHECK_FAIL","该BD没有组织信息"),
    AMOUNT_CHECK_MAIN_FAIL("AMOUNT_CHECK_MAIN_FAIL","该BD没有主组织"),
    /**
     * 调用超时
     */
    NO_DEPARTMENT("OMS012002", "组织不存在"),
    NO_EMPLOYEE("OMS012003", "人员信息不存在"),
    NO_SUPPORT_TYPE("OMS012004", "不支持该类型"),
    ERROR_MATCH_TYPE("OMS012005", "暂不支持此处理类型"),
    NO_LOGIN_EMPLOYEE("OMS012006", "没有登录人"),
    NO_OPERATOR_ERROR("OMS012006", "操作人为空"),
    NO_MANAGER_DEPART("OMS012007", "没有负责组织"),

    ;

    private String code;
    private String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    @Override
    public String getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

    @Override
    public String getErrorTips() {
        return message;
    }
}
