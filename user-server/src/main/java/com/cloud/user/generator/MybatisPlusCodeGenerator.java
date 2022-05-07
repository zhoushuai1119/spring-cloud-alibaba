package com.cloud.user.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/6 17:24
 * @version: v1
 */
public class MybatisPlusCodeGenerator {

    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://139.196.208.53:3306/cloud?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false", "root", "Zs11195310")
            .dbQuery(new MySqlQuery())
            .schema("");

    // 多个用,  拼接
    public static final String TABLE_NAMES = "permission";
    //包名路径
    public static final String PACKAGE_PATH = "com.cloud.user";
    //服务名
    public static final String APPLICATION_NAME = "user-server";

    /**
     * 执行 run
     */
    public static void main(String[] args) {
        //获取当前项目的 文件夹地址
        String projectPath = System.getProperty("user.dir") + '\\' + APPLICATION_NAME;

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                // 全局配置
                .globalConfig((scanner, builder) ->
                        builder.author("zhoushuai")
                                //开启swagger模式
                                .enableSwagger()
                                //.outputDir("D:\\mybatis-plus-generator")
                                .outputDir(projectPath + "/src/main/java")
                                .fileOverride())

                // 包配置
                .packageConfig((scanner, builder) ->
                        builder.parent(PACKAGE_PATH)
                                //.moduleName(PACKAGE_PATH)
                                .entity("domain.entity")
                                .service("service")
                                .serviceImpl("service.impl")
                                .mapper("mapper")
                                .xml("mapper.xml")
                                .controller("controller")
                                .other("other"))

                //模板配置
                /*.templateConfig((scanner, builder) -> builder.disable(TemplateType.ENTITY)
                        .entity("/templates/entity.java")
                        .service("/templates/service.java")
                        .serviceImpl("/templates/serviceImpl.java")
                        .mapper("/templates/mapper.java")
                        .mapperXml("/templates/mapper.xml")
                        .controller("/templates/controller.java"))*/

                // 策略配置
                .strategyConfig((scanner, builder) -> builder.enableCapitalMode()
                        //设置生成的表名
                        .addInclude(TABLE_NAMES)
                        //Entity 策略配置
                        .entityBuilder()
                        .superClass(Model.class)
                        .disableSerialVersionUID()
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .idType(IdType.AUTO)
                        //数据库表映射到实体的命名策略(默认下划线转驼峰命名)
                        .naming(NamingStrategy.underline_to_camel)
                        //.addTableFills(new Column("gmt_create", FieldFill.INSERT))
                        //.addTableFills(new Property("gmt_modify", FieldFill.INSERT_UPDATE))

                        //Mapper 策略配置
                        .mapperBuilder()
                        .superClass(BaseMapper.class)
                        .enableBaseResultMap()
                        .enableBaseColumnList()

                        //Service 策略配置
                        .serviceBuilder()
                        .superServiceClass(IService.class)
                        .superServiceImplClass(ServiceImpl.class)
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImp")

                        //Controller 策略配置
                        .controllerBuilder()
                        .enableRestStyle()
                ).execute();
    }

}
