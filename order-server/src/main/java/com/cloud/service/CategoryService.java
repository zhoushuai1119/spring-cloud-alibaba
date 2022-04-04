package com.cloud.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.entity.Category;
import com.cloud.platform.common.request.PageQueryRequest;

import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/26 19:15
 * @version: V1.0
 */
public interface CategoryService extends IService<Category> {

    List<Category> categoryList();

    Page<Category> categoryPageList(PageQueryRequest pageQueryRequest);

    void updateCategory();

    void delCategory();

    void asyncSendMq(Integer categoryId);

    void saveCategory(Category category);

}
