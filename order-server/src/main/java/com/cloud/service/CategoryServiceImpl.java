package com.cloud.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.common.entity.order.Category;
import com.cloud.common.entity.payment.User;
import com.cloud.common.service.order.CategoryService;
import com.cloud.common.service.payment.UserService;
import com.cloud.dao.CategoryMapper;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;

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

    @Override
    public List<Category> categoryList() {
        return list();
    }

    @GlobalTransactional
    @Override
    public void updateTT() throws Exception {
        Category category = baseMapper.selectById("16");
        category.setParentCategoryId("1111");
        updateById(category);
        System.out.println("****开始调用远程服务接口****");
        userService.saveUser(new User());
    }
}
