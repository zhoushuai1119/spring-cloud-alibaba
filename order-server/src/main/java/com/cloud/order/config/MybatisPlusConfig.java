package com.cloud.order.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/26 19:21
 * @version: V1.0
 */
@Configuration
@MapperScan("com.cloud.order.dao")
public class MybatisPlusConfig {

    /**
     * 新的分页插件,一缓和二缓遵循mybatis的规则,
     * 需要设置 MybatisConfiguration#useDeprecatedExecutor = false
     * 避免缓存出现问题(该属性会在旧插件移除后一同移除)
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        /*TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor();
        TenantLineHandler myTenantLineHandler = new MyTenantLineHandler();
        tenantLineInnerInterceptor.setTenantLineHandler(myTenantLineHandler);
        //添加租户插件；如果用了分页插件注意先添加租户插件再添加分页插件
        interceptor.addInnerInterceptor(tenantLineInnerInterceptor);*/
        //分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        //动态表名插件
        /*DynamicTableNameInnerInterceptor dynamicTableNameInnerInterceptor = new DynamicTableNameInnerInterceptor();
        dynamicTableNameInnerInterceptor.setTableNameHandler(new MyTableNameHandler());
        //添加动态表名插件
        interceptor.addInnerInterceptor(dynamicTableNameInnerInterceptor);*/
        //防止全表更新与删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

}
