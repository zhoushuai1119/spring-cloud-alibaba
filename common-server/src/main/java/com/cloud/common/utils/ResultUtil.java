package com.cloud.common.utils;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cloud.common.commons.beans.KeyValuePair;
import com.cloud.common.commons.beans.MultiResult;
import com.cloud.common.commons.beans.Result;

import java.util.List;
import java.util.Map;

/**
 * <p><返回值工具类>
 * <p><返回值工具类>
 */
public class ResultUtil {

    /**
     * 获取MultiResult
     * @param page ： 分页
     * @param <T> ： 泛型
     * @return
     */
    public static <T> MultiResult<T> getMultiResult(IPage<T> page){
        MultiResult<T> result = new MultiResult<T>();
        result.setTotal(page.getTotal());
        result.setRows(page.getRecords());
        return result;
    }

    /**
     * 获取MultiResult
     * @param datas ： 分页
     * @param <T> ： 泛型
     * @return
     */
    public static <T> MultiResult<T> getMultiResult(long total, List<T> datas){
        MultiResult<T> result = new MultiResult<T>();
        result.setTotal(total);
        result.setRows(datas);
        return result;
    }

    /**
     * 获取MultiResult
     * @param datas
     * @param dataMap
     * @return
     */
    public static <T> MultiResult<T> getMultiResult(int total, List<T> datas, Map<Object, Object> dataMap){
        MultiResult<T> result = new MultiResult<T>();
        result.setTotal(total);
        result.setRows(datas);
        result.setData(dataMap);
        return result;
    }


    /**
     * 获取不成功的MultiResult
     * @param flag ： 提示code
     * @param msg ： 提示信息
     * @return
     */
    public static <T> MultiResult<T> getMultiResult(boolean flag, String msg){
        MultiResult<T> result = new MultiResult<T>();
        result.setFlag(flag);
        result.setDesc(msg);
        return result;
    }

    /**
     * 获取Result
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Result<T> getResult(T t){
        Result<T> result = new Result<T>();
        result.setData(t);
        return result;
    }

    /**
     * 获取keyValuePair构造的Result
     * @param keyValuePair
     * @param <T>
     * @return
     */
    public static <T> MultiResult<T> getMultiResult(KeyValuePair keyValuePair){
        return new MultiResult<T>(keyValuePair);
    }

    /**
     * 获取keyValuePair构造的Result
     * @param keyValuePair
     * @param <T>
     * @return
     */
    public static <T> Result<T> getResult(KeyValuePair keyValuePair){
        return new Result<T>(keyValuePair);
    }

    /**
     * 获取自定义的Result
     * @param code
     * @param desc
     * @param <T>
     * @return
     */
    public static <T> Result<T> getResult(int code, String desc) {
        return new Result<T>(code, desc);
    }


    /**
     * 自定义的Result
     * @param code
     * @param desc
     * @param flag
     * @param <T>
     * @return
     */
    public static <T> Result<T> getResult(int code, String desc, boolean flag) {
        return new Result<T>(code, desc,flag);
    }
}
