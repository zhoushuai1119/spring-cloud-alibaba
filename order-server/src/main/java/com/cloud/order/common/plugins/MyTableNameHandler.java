package com.cloud.order.common.plugins;

import com.baomidou.mybatisplus.extension.plugins.handler.TableNameHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/4/4 19:42
 * @version: v1
 */
@Slf4j
public class MyTableNameHandler implements TableNameHandler {

    @Override
    public String dynamicTableName(String sql, String tableName) {
        // 获取参数方法
        log.info("执行sql: {}", sql);
        log.info("tableName: {}",tableName);
        return tableName;
    }

}
