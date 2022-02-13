package com.cloud.service;

import com.cloud.common.beans.response.BaseResponse;
import com.cloud.common.entity.order.Category;
import com.cloud.common.service.order.CategoryService;
import com.cloud.elasticsearch.dao.IEsCategoryMapper;
import org.elasticsearch.action.DocWriteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: zhou shuai
 * @date: 2022/1/8 14:38
 * @version: v1
 */
@Service
public class ElasticsearchService {

    @Autowired
    private IEsCategoryMapper categoryDao;
    @Autowired
    private CategoryService categoryService;

    public BaseResponse<DocWriteResponse.Result> saveCategory() throws IllegalAccessException {
        Category category = categoryService.getById("1");
        DocWriteResponse.Result result = categoryDao.insertDoc(category);
        return BaseResponse.createSuccessResult(result);
    }

    public BaseResponse<Category> getCategory() {
        Category category  = categoryDao.selectDoc("idx_category","1");
        return BaseResponse.createSuccessResult(category);
    }

    public BaseResponse<DocWriteResponse.Result> delCategory() throws IllegalAccessException {
        Category category = categoryService.getById("1");
        DocWriteResponse.Result result = categoryDao.deleteDoc(category);
        return BaseResponse.createSuccessResult(result);
    }

}
