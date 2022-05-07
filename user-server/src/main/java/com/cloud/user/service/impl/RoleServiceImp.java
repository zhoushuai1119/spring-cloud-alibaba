package com.cloud.user.service.impl;

import com.cloud.user.domain.entity.Role;
import com.cloud.user.mapper.RoleMapper;
import com.cloud.user.service.RoleService;
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
public class RoleServiceImp extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
