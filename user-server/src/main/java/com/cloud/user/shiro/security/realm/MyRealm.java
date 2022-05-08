package com.cloud.user.shiro.security.realm;

import com.cloud.user.domain.entity.User;
import com.cloud.user.service.UserService;
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

/**
 * @Author:zhoushuai
 * @Description:
 * @Date:2018-05-18 14:13
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 为当前登录成功的用户授予权限和角色，已经登录成功了
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(userService.getUserRoleList(username));
        authorizationInfo.setStringPermissions(userService.getUserPermissionList(username));
        log.info("用户:{}角色权限为:{},操作权限为:{}",username,authorizationInfo.getRoles(),authorizationInfo.getStringPermissions());
        return authorizationInfo;
    }

    /**
     * 验证当前登录的用户，获取认证信息
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("=======执行MyRealm的doGetAuthenticationInfo方法开始验证===============");
        //获取用户名
        String username = (String) token.getPrincipal();
        User user = userService.findUserByName(username);
        if (user != null) {
            /**交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现*/
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                    user.getUsername(),
                    user.getPassword(),
                    ByteSource.Util.bytes(user.getCredentialsSalt()),
                    getName()
            );
            return authcInfo;
        }
        return null;
    }

}
