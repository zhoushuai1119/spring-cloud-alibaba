package com.xxl.job.admin.core.shiro.realm;

import com.google.common.base.Splitter;
import com.xxl.job.admin.core.model.XxlJobUser;
import com.xxl.job.admin.dao.XxlJobUserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import java.util.List;
import java.util.Objects;

/**
 * @Author:zhoushuai
 * @Description:
 * @Date:2018-05-18 14:13
 */
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private XxlJobUserDao xxlJobUserDao;

    /**
     * 为当前登录成功的用户授予权限和角色，已经登录成功了
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        XxlJobUser xxlJobUser = xxlJobUserDao.loadByUserName(username);
        if (Objects.nonNull(xxlJobUser)) {
            log.info("当前登录用户为:{},角色:{},权限:{}",username,xxlJobUser.getRole(),xxlJobUser.getPermission());
            //添加角色
            authorizationInfo.addRole(String.valueOf(xxlJobUser.getRole()));

            String permissListStr = xxlJobUser.getPermission();
            if (StringUtils.isNotBlank(permissListStr)) {
                List<String> permissList = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(permissListStr);
                //添加权限
                permissList.forEach(permiss -> {
                    authorizationInfo.addStringPermission(permiss);
                });
            }
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
        XxlJobUser xxlJobUser = xxlJobUserDao.loadByUserName(username);
        if (xxlJobUser != null) {
            log.info("***进入MyRealm获取用户信息:{}***",username);
            /**交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现*/
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(
                    //SecurityUtils.getSubject().getPrincipal()获取的信息在这里配置
                    xxlJobUser,
                    xxlJobUser.getPassword(),
                    ByteSource.Util.bytes(xxlJobUser.getCredentialsSalt()),
                    getName()
            );
            return authcInfo;
        }
        return null;
    }

}
