package com.cloud.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.order.mapper.CategoryMapper;
import com.cloud.order.domain.dto.BatchDelDTO;
import com.cloud.order.domain.entity.Category;
import com.cloud.platform.common.domain.request.PageQueryRequest;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.order.proxy.PaymentProxy;
import com.cloud.order.service.CategoryService;
import com.cloud.order.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/26 19:15
 * @version: V1.0
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    private ApplicationEventPublisher publisher;

    @Autowired
    private PaymentProxy paymentProxy;

    @Override
    public List<Category> categoryList() {
        log.info("parmsTestDto:{}", JsonUtil.toString(ThreadLocalUtil.get()));
        ThreadLocalUtil.remove();
        log.info("parmsTestDto:{}", JsonUtil.toString(ThreadLocalUtil.get()));
        return list();
    }

    @Override
    public Page<Category> categoryPageList(PageQueryRequest pageQueryRequest) {
        Page page = new Page(pageQueryRequest.getPageIndex(), pageQueryRequest.getPageSize());
        Page<Category> pageList = baseMapper.selectPage(page, new QueryWrapper<>());
        return pageList;
    }

    @Override
    @Transactional
    public void updateCategory(String categoryId) {
        Category category = baseMapper.selectById(categoryId);
        category.setParentCategoryId("888");
        updateById(category);
    }

    @Override
    public void batchDelCategory(BatchDelDTO categoryIdList) {
        baseMapper.deleteBatchIds(categoryIdList.getCategoryIdList());
    }

    @Override
    public Category getCategory(String categoryId) {
        return baseMapper.selectById(categoryId);
    }

    @Override
    @Async("batteryOperationExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void asyncSendMq(Integer categoryId) {
        Category category = baseMapper.selectById(categoryId);
        publisher.publishEvent(category);
    }


    @Override
    public void saveCategory(Category category) {
        saveOrUpdate(category);
    }

}
