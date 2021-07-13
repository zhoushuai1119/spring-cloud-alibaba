package com.cloud.common.utils;

import java.text.MessageFormat;

/**
 * @ClassName SqlUtils
 * @Description
 * @Date 2019/6/23 14:50
 * @Version 1.0
 **/
public class SqlUtils {

    private SqlUtils() {
    }

    ;

    /**
     * 重命名表sql
     */
    public static final String RENAME_TABLE_NAME = "RENAME TABLE {0} TO {1};";

    /**
     * 拷贝表sql
     */
    public static final String COPY_TABLE = "CREATE TABLE {0} LIKE {1};";

    /**
     * 获取重命名表SQL
     *
     * @param oldName
     * @param newName
     * @return
     */
    public static String renameTableSql(String oldName, String newName) {
        return MessageFormat.format(RENAME_TABLE_NAME, oldName, newName);
    }

    /**
     * 获取拷贝表SQL
     *
     * @param sourceTable
     * @param newName
     * @return
     */
    public static String copyTableTableSql(String sourceTable, String newName) {
        return MessageFormat.format(COPY_TABLE, newName, sourceTable);
    }

}
