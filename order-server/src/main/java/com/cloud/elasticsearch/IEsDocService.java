package com.cloud.elasticsearch;

import org.elasticsearch.action.DocWriteResponse;

/**
 * @description: es文档操作
 * @author: zhou shuai
 * @date: 2022/1/8 13:25
 * @version: v1
 */
public interface IEsDocService<T> {

    /**
     * 获取索引文档信息
     * @param index
     * @param id
     * @return
     */
    T selectDoc(String index, String id);

    /**
     * 保存文档
     * @param t
     */
    DocWriteResponse.Result insertDoc(T t) throws IllegalAccessException;

    /**
     * 删除文档
     * @param t
     */
    DocWriteResponse.Result deleteDoc(T t) throws IllegalAccessException;

}
