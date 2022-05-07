package com.cloud.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cloud.platform.web.utils.CommonUtil;
import com.cloud.user.domain.dto.UserRegisterDTO;
import com.cloud.user.domain.entity.Permission;
import com.cloud.user.domain.entity.Role;
import com.cloud.user.domain.entity.User;
import com.cloud.user.mapper.PermissionMapper;
import com.cloud.user.mapper.RoleMapper;
import com.cloud.user.mapper.UserMapper;
import com.cloud.user.service.UserService;
import com.cloud.user.utils.PasswordHelper;
import com.github.dozermapper.core.Mapper;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoushuai
 * @since 2022-05-07
 */
@Service
public class UserServiceImp extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private Mapper mapper;

    @Override
    public void userRegister(UserRegisterDTO userRegister) {
        passwordHelper.encryptPassword(userRegister);
        User user = mapper.map(userRegister,User.class);
        save(user);
    }

    @Override
    public User findUserByName(String username) {
        return getOne(new QueryWrapper<User>().eq("username",username));
    }

    @Override
    public Set<String> getUserRoleList(String username) {
        User user = findUserByName(username);
        List<String> roleIdList = CommonUtil.strToListStr(user.getRoleId());
        List<Role> roleList = roleMapper.selectBatchIds(roleIdList);
        if (CommonUtil.isNotEmpty(roleList)) {
            return roleList.stream().map(role -> role.getRoleName()).collect(Collectors.toSet());
        }
        return Sets.newHashSet();
    }

    @Override
    public Set<String> getUserPermissionList(String username) {
        User user = findUserByName(username);
        List<String> roleIdList = CommonUtil.strToListStr(user.getRoleId());
        if (CommonUtil.isNotEmpty(roleIdList)) {
            List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>().in("role_id",roleIdList));
            return permissionList.stream().map(permission -> permission.getPermission()).collect(Collectors.toSet());
        }
        return Sets.newHashSet();
    }

}
