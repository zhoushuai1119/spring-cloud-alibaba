package com.cloud.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.beans.request.PageQueryRequest;
import com.cloud.common.entity.order.Category;
import com.cloud.common.entity.payment.User;
import com.cloud.common.service.order.CategoryService;
import com.cloud.common.service.payment.UserService;
import com.cloud.dao.CategoryMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: 周帅
 * @date: 2021/1/26 19:15
 * @version: V1.0
 */
@DubboService
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @DubboReference
    private UserService userService;

    @Resource
    private ApplicationEventPublisher publisher;

    @Override
    public List<Category> categoryList() {
        return list();
    }

    @Override
    public Page<Category> categoryPageList(PageQueryRequest pageQueryRequest) {
        Page page = new Page(pageQueryRequest.getPageIndex(),pageQueryRequest.getPageSize());
        Page<Category> pageList = baseMapper.selectPage(page,new QueryWrapper<>());
        return pageList;
    }

    @GlobalTransactional
    @Override
    public void dubboTest() throws Exception {
        Category category = baseMapper.selectById("16");
        category.setParentCategoryId("1111");
        updateById(category);
        System.out.println("****开始Dubbbo调用远程服务接口****");
        userService.saveUser(new User());
    }

    @Override
    @Async("batteryOperationExecutor")
    @Transactional(rollbackFor = Exception.class)
    public void asyncSendMq(Integer categoryId) {
        Category category = baseMapper.selectById(categoryId);
        publisher.publishEvent(category);
    }

}
