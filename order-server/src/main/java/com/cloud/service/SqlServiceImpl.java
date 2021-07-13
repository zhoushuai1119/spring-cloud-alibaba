package com.cloud.service;

import com.cloud.common.service.order.SqlService;
import com.cloud.common.utils.SqlUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @Description:
 * @Author: ZhouShuai
 * @Date: 2021-06-29 20:22
 */
@Service
public class SqlServiceImpl implements SqlService {

    @Resource
    private JdbcTemplate jdbcTemplate;


    @Override
    public void reNameTable(String oldTableName, String newTableName) throws SQLException {
        String amountRenameSql = SqlUtils.renameTableSql("user_role", "user_role_202106");
        // 同一事务执行SQL
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute(amountRenameSql);
            }
            connection.commit();
        }
    }

    @Override
    public void copyNameTable(String oldTableName, String newTableName) throws SQLException {
        String amountCopySql = SqlUtils.copyTableTableSql("user_role_202106", "user_role");
        // 同一事务执行SQL
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.execute(amountCopySql);
            }
            connection.commit();
        }
    }

}
