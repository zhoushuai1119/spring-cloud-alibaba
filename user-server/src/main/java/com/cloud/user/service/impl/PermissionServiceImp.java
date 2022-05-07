package com.cloud.user.service.impl;

import com.cloud.user.domain.entity.Permission;
import com.cloud.user.mapper.PermissionMapper;
import com.cloud.user.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-07
 */
@Service
public class PermissionServiceImp extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
