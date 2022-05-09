package com.cloud.product.common.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @description: LocalDateTime是为了解决shardingResultSet转换LocalDateTime的问题
 * @author: zhou shuai
 * @date: 2022/5/9 17:50
 * @version: v1
 */
@Component
@MappedTypes(LocalDate.class)
@MappedJdbcTypes(value = JdbcType.DATE, includeNullJdbcType = true)
public class LocalDateTypeHandler extends BaseTypeHandler<LocalDate> {


    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LocalDate parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public LocalDate getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        if (null == resultSet.getObject(columnName)) {
            return null;
        }
        return LocalDate.parse(resultSet.getObject(columnName).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public LocalDate getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        if (null == resultSet.getObject(columnIndex)) {
            return null;
        }
        return LocalDate.parse(resultSet.getObject(columnIndex).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @Override
    public LocalDate getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        if (null == cs.getObject(columnIndex)) {
            return null;
        }
        return LocalDate.parse(cs.getObject(columnIndex).toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
