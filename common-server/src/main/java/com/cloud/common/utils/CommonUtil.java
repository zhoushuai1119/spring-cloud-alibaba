package com.cloud.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.cloud.common.constants.CommonConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;


public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);


    /**
     * 判断字符串不为空
     *
     * @param text
     * @return
     */
    public static boolean isNotEmpty(String text) {
        return StringUtils.isNotBlank(text);
    }

    /**
     * 判断集合不为空
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection collection) {
        return CollectionUtils.isNotEmpty(collection);
    }

    /**
     * 判断字符串为空
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        return StringUtils.isBlank(text);
    }

    /**
     * 判断集合为空
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection collection) {
        return CollectionUtils.isEmpty(collection);
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        return DateFormatUtils.format(System.currentTimeMillis(), CommonConstant.dateFormat.DATE_FORMAT);
    }

    /**
     * 时间转日期字符串
     *
     * @param date
     * @return
     */
    public static String dateToStr(Date date) {
        if (null == date) {
            return null;
        }
        return DateFormatUtils.format(date, CommonConstant.dateFormat.DATE_FORMAT);
    }

    /**
     * 字符串转日期
     *
     * @param str
     * @return
     */
    public static Date strToDate(String str) {
        DateFormat format = new SimpleDateFormat(CommonConstant.dateFormat.DATE_FORMAT);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            LOGGER.error("CommonUtil.str2Date parseException", "error", e);
        }
        return date;
    }

    /**
     * 将url参数转换成map
     *
     * @param param aa=11&bb=22&cc=33
     * @return
     */
    public static Map<String, String> getUrlParams(String param) {
        Map<String, String> map = new HashMap<String, String>();
        if (StringUtils.isBlank(param)) {
            return map;
        }
        String[] params = param.split("&");
        for (int i = 0; i < params.length; i++) {
            String[] p = params[i].split("=");
            if (p.length == 2) {
                map.put(p[0], p[1]);
            }
        }
        return map;
    }

    /**
     * 生成指定长度的随机字符串
     *
     * @param length 成字符串的长度
     * @return
     */
    public static String getRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 对象字符串转换为List集合
     */
    public static <T> List<T> strToList(String str, Class<T> clazz) {
        return JSONArray.parseArray(str, clazz);
    }

    /**
     * 对象字符串转换为对象
     */
    public static <T> T strToBean(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }

    /**
     * 字符串转数组
     * @param str
     * @return
     */
    public static String[] strToArray(String str){
        return str.split(CommonConstant.SymbolParam.COMMA);
    }
    /**
     * 数组转字符串
     * @param arrays
     * @return
     */
    public static String arrayToStr(String[] arrays){
        return StringUtils.join(arrays,CommonConstant.SymbolParam.COMMA);
    }
    /**
     * 集合转字符串
     * @param list
     * @return
     */
    public static String listToStr(List<String> list){
        return StringUtils.join(list);
    }
    /**
     * 数组转集合
     * @param array
     * @return
     */
    public static List<String> arrayToList(String[] array){
        return Arrays.asList(array);
    }
    /**
     * 集合转数组
     * @param list
     * @return
     */
    public static String[] listToArray(List<String> list){
        String[] str = new String[list.size()];
        return list.toArray(str);
    }

    /**
     * 对象转json字符串
     * @param object
     * @return
     */
    public static String objectTojsonStr(Object object){
        return JSONObject.toJSONString(object);
    }


    /**
     * [手机号码] 前三位，后四位，其他隐藏<例子:138******1234>
     */
    public static String mobilePhone(final String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.left(num, 3).concat(StringUtils
                .removeStart(StringUtils.leftPad(StringUtils.right(num, 4), StringUtils.length(num), "*"),
                        "***"));

    }

    /**
     * 功能描述: 当表达式为true时执行，否则不执行
     *
     * @param arg      参数
     * @param consumer 执行函数
     */
    public static <T> void doWhenTrue(Boolean expression, T arg, Consumer<T> consumer) {
        Objects.requireNonNull(consumer);
        if (expression) {
            consumer.accept(arg);
        }
    }

}
