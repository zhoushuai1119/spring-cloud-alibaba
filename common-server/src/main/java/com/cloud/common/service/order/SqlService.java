package com.cloud.common.service.order;

import java.sql.SQLException;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-29 20:22
 */
public interface SqlService {

    /**
     * 表重命名
     * @param oldTableName
     * @param newTableName
     */
    void reNameTable(String oldTableName,String newTableName) throws SQLException;

    /**
     * 表拷贝
     * @param oldTableName
     * @param newTableName
     */
    void copyNameTable(String oldTableName,String newTableName) throws SQLException;

}
