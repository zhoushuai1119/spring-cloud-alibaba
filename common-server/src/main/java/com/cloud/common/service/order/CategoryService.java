package com.cloud.common.service.order;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cloud.common.beans.request.PageQueryRequest;
import com.cloud.common.entity.order.Category;

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

    void asyncSendMq(Integer categoryId);

}
