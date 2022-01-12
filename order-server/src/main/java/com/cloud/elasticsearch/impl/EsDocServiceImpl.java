package com.cloud.elasticsearch.impl;

import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.cloud.common.aop.annotation.elasticsearch.EsDocPrivateKey;
import com.cloud.common.aop.annotation.elasticsearch.EsIndex;
import com.cloud.common.utils.JsonUtil;
import com.cloud.common.utils.ReflectionUtil;
import com.cloud.elasticsearch.IEsDocService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.HttpAsyncResponseConsumerFactory;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/8 13:27
 * @version: v1
 */
@Slf4j
public class EsDocServiceImpl<T> implements IEsDocService<T> {

    @Autowired
    public RestHighLevelClient client;

    protected static final RequestOptions COMMON_OPTIONS;

    /**
     * 泛型类的class
     */
    protected Class<T> entityClass;
    /**
     * 索引名称
     */
    private String index;
    /**
     * 泛型类字段
     */
    private List<Field> fieldList;

    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // 默认缓冲限制为100MB，此处修改为30MB。
        builder.setHttpAsyncResponseConsumerFactory(new HttpAsyncResponseConsumerFactory.HeapBufferedResponseConsumerFactory(30 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    public EsDocServiceImpl(){
        log.info("加载EsDocServiceImpl构造方法...");
        this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
        this.fieldList = new ArrayList<>();
        //获取实体类索引注解
        EsIndex index = this.entityClass.getAnnotation(EsIndex.class);
        if (Objects.nonNull(index) && StringUtils.isNotBlank(index.value())) {
            this.index = index.value();
        } else {
            this.index = this.entityClass.getSimpleName();
        }
    }

    /**
     * 构造方法之后执行
     */
    @PostConstruct
    public void init() {
        log.info("加载EsDocServiceImpl -- init构造方法...");
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields){
            if(Modifier.isStatic(field.getModifiers())){
                continue;
            }
            this.fieldList.add(field);
        }
    }

    @Override
    public T selectDoc(String index, String id) {
        Assert.notNull(index,"index不能为空");
        Assert.notNull(id,"id不能为空");
        GetRequest getRequest = new GetRequest(index,id);
        try {
            GetResponse getResponse =  client.get(getRequest, COMMON_OPTIONS);
            if (getResponse.isExists()) {
                T t = BeanUtils.mapToBean(getResponse.getSourceAsMap(), entityClass);
                return t;
            }
        } catch (IOException e) {
            log.error("获取索引{}文档失败",index);
        }
        return null;
    }

    @Override
    public DocWriteResponse.Result insertDoc(T t) throws IllegalAccessException {
        Object docId = getDocPrivateKey(t);
        IndexRequest indexRequest = new IndexRequest(index).id(docId.toString()).source(BeanUtils.beanToMap(t), XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(indexRequest, COMMON_OPTIONS);
            return indexResponse.getResult();
        } catch (IOException e) {
            log.error("创建索引{}文档失败,数据{}",index, JsonUtil.toString(t));
        }
        return null;
    }

    @Override
    public DocWriteResponse.Result deleteDoc(T t) throws IllegalAccessException {
        Object docId = getDocPrivateKey(t);
        DeleteRequest indexRequest = new DeleteRequest(index).id(docId.toString());
        try {
            DeleteResponse deleteResponse = client.delete(indexRequest, COMMON_OPTIONS);
            return deleteResponse.getResult();
        } catch (IOException e) {
            log.error("删除索引{}文档失败,数据{}",index, JsonUtil.toString(t));
        }
        return null;
    }

    /**
     * 获取对象中被@Id注解的字段的值
     * @param t
     * @return
     * @throws IllegalAccessException
     */
    private Object getDocPrivateKey(T t) throws IllegalAccessException {
        Object id = null;
        for (Field field : this.fieldList) {
            field.setAccessible(true);
            EsDocPrivateKey annotation = field.getAnnotation(EsDocPrivateKey.class);
            if (annotation != null) {
                id = field.get(t);
                break;
            }
        }
        return id;
    }

}
