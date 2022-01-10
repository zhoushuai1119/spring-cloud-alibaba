package com.cloud.elasticsearch.dao.impl;

import com.cloud.common.entity.order.Category;
import com.cloud.elasticsearch.dao.IEsCategoryMapper;
import com.cloud.elasticsearch.impl.EsDocServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/10 16:23
 * @version: v1
 */
@Repository
public class EsCategoryMapperImpl extends EsDocServiceImpl<Category> implements IEsCategoryMapper {

}
