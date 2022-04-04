package com.cloud.common.plugins;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import org.apache.commons.lang.ObjectUtils;

import java.util.List;
import java.util.Random;

/**
 * @description: 多租户插件实现类
 * @author: zhou shuai
 * @date: 2022/4/4 17:22
 * @version: v1
 */
@Slf4j
public class MyTenantLineHandler implements TenantLineHandler {

    /**
     * 获取租户ID 实际应该从用户信息中获取
     *
     * @return
     */
    @Override
    public Expression getTenantId() {
        // 模拟ID
        log.info("==========================getTenantId");
        String userTenantId = "000" + (new Random().nextInt(3) + 1);
        return new StringValue(userTenantId);
    }

    /**
     * 获取租户表字段 默认为tenant_id
     *
     * @return
     */
    @Override
    public String getTenantIdColumn() {
        log.info("==========================getTenantIdColumn");
        //默认tenant_id
        return TenantLineHandler.super.getTenantIdColumn();
    }

    /**
     * 表过滤，返回true，表示当前表不进行租户过滤
     *
     * @param tableName 表名
     * @return
     */
    @Override
    public boolean ignoreTable(String tableName) {
        // 排除除了category的其他表
        log.info("==========================ignoreTable");
        return ObjectUtils.notEqual("category",tableName);
    }


    /**
     * 判断是否手动设置了租户ID的值，设置了返回 true，插入手动设置的值
     * 未设置使用 getTenantId()返回的租户 ID，一般不建议手动设置租户ID的值
     * 疑问？ 如果手动设置了租户ID的值，应该如何使用手动设置的租户ID查询呢？
     * @param columns 列名列表
     * @param tenantIdColumn 租户ID的列名
     * @return
     */
    @Override
    public boolean ignoreInsert(List<Column> columns, String tenantIdColumn) {
        log.info("==========================ignoreInsert");
        boolean ignoreInsert = TenantLineHandler.super.ignoreInsert(columns, tenantIdColumn);
        log.info("ignoreInsert:{}",ignoreInsert);
        return ignoreInsert;
    }

}
