package com.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.dao.CategoryMapper;
import com.cloud.entity.Category;
import com.cloud.enums.OrderEnum;
import com.cloud.platform.common.request.PageQueryRequest;
import com.cloud.platform.common.utils.JsonUtil;
import com.cloud.proxy.PaymentProxy;
import com.cloud.service.CategoryService;
import com.cloud.utils.ThreadLocalUtil;
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
        log.info("oredrEnum:{}", OrderEnum.getEnumByValue(1));
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
    public void updateCategory() {
        Category category = baseMapper.selectById("16");
        category.setParentCategoryId("2222");
        updateById(category);
    }

    @Override
    public void delCategory() {
        baseMapper.deleteById(57);
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
        baseMapper.insert(category);
    }

}
