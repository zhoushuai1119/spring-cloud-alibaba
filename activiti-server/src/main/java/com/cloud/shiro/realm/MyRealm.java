package com.cloud.shiro.realm;

import com.cloud.common.entity.activiti.ShiroUser;
import com.cloud.common.service.activiti.ShiroUserService;
import com.cloud.common.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Set;

/**
 * @Author:zhoushuai
 * @Description:
 * @Date:2018-05-18 14:13
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private ShiroUserService shiroUserService;

    /**
     * 为当前登录成功的用户授予权限和角色，已经登录成功了
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
        String username = shiroUser.getUserName();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = shiroUserService.getRoles(username);
        Set<String> permiss = shiroUserService.getPermissions(username);
        if (CommonUtil.isNotEmpty(roles)){
            log.info("当前登录用户:"+username+"***角色信息为:"+ Arrays.asList(roles));
            authorizationInfo.setRoles(roles);
        }
        if (CommonUtil.isNotEmpty(permiss)){
            log.info("当前登录用户:"+username+"***操作权限信息为:"+ Arrays.asList(permiss));
            authorizationInfo.setStringPermissions(permiss);
        }
        return authorizationInfo;
    }

    /**
     * 验证当前登录的用户，获取认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取用户名
        String username = (String) token.getPrincipal();
        ShiroUser shiroUser = shiroUserService.findByUsername(username);
        if (shiroUser != null) {
            log.info("***********进入MyRealm获取用户信息:"+shiroUser+"************");
            /**交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现*/
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                    //SecurityUtils.getSubject().getPrincipal()获取的信息在这里配置
                    shiroUser,
                    shiroUser.getPassword(),
                    ByteSource.Util.bytes(shiroUser.getCredentialsSalt()),
                    getName()
            );
            return authcInfo;
        }
        return null;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
