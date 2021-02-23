package com.cloud.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.common.entity.activiti.ShiroUser;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface ShiroUserMapper extends BaseMapper<ShiroUser> {

    /**
     * @description : 获取用户角色
     * @author : shuaizhou4
     * @date : 2020-01-14 11:52
     * @param userName
     * @return :
     * @throws :
     */
    Set<String> getRoles(@Param("userName") String userName);

    /**
     * @description : 获取用户操作权限
     * @author : shuaizhou4
     * @date : 2020-01-14 11:52
     * @param userName
     * @return :
     * @throws :
     */
    Set<String> getPermissions(@Param("userName") String userName);

}