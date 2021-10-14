package com.cloud.config;

import com.cloud.common.constants.CommonConstant;
import com.cloud.filters.KickoutSessionControlFilter;
import com.cloud.filters.MyFormAuthenticationFilter;
import com.cloud.shiro.credentials.RetryLimitHashedCredentialsMatcher;
import com.cloud.shiro.realm.MyRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro核心
 *
 * @author ZhouShuai
 * @date 2018/06/06
 */
@Configuration
@Slf4j
public class ShiroConfig {

    /**
     * shiro过滤器
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter() {
        log.info("*************shiro过滤器*****************");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //需要登录的接口:如果访问某个接口,需要登录却没有登录,则调用此接口,如果前端后端不分离,则跳转到html页面
        //如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功 跳转url,如果前后端分离,则没这个调用 --这里设置为首页就行了
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //登录成功,但是没有权限,未授权就会调用这个接口,如果不是前后端分离,则跳转到403页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        //自定义拦截器.
        Map<String, Filter> filtersMap = new LinkedHashMap<>();
        filtersMap.put("authc",myFormAuthenticationFilter());
        filtersMap.put("kickout",kickoutSession());
        shiroFilterFactoryBean.setFilters(filtersMap);
        //Shiro连接约束配置，即过滤链的定义
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/captcha-image", "anon");
        filterChainDefinitionMap.put("/zhuce", "anon");
        filterChainDefinitionMap.put("/save", "anon");
        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/login","authc");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/admin","authc");
        filterChainDefinitionMap.put("/student","authc,roles[\"admin\"]");
        filterChainDefinitionMap.put("/teacher","authc,perms[\"user:query\"]");
        filterChainDefinitionMap.put("/**", "authc, kickout, user");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * shiro的缓存技术，用于缓存授权信息
     * 当用户正常退出,清空缓存。
     */
    @Bean
    public EhCacheManager shiroCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:shiro/ehcache.xml");
        return em;
    }

    /**
     * Shiro密码匹配器
     */
    @Bean
    public RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher =
                new RetryLimitHashedCredentialsMatcher(shiroCacheManager());
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(2);
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
        return hashedCredentialsMatcher;
    }

    /**
     * 会话ID生成器
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        JavaUuidSessionIdGenerator sessionIdGenerator = new JavaUuidSessionIdGenerator();
        return sessionIdGenerator;
    }

    /**
     * 会话Cookie
     */
    @Bean
    public SimpleCookie sessionIdCookie() {
        SimpleCookie sessionIdCookie = new SimpleCookie("sid");
        sessionIdCookie.setHttpOnly(true);
        sessionIdCookie.setMaxAge(-1);
        return sessionIdCookie;
    }

    /**
     * 持久cookie(记住我)
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie rememberMeCookie = new SimpleCookie("rememberMe");
        rememberMeCookie.setHttpOnly(true);
        rememberMeCookie.setMaxAge(2592000);
        return rememberMeCookie;
    }

    /**
     * 配置RememberMe功能的程序管理类
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.decode("4AvVhmFLUs0KTA3Kprsdag=="));
        return cookieRememberMeManager;
    }

    /**
     * 配置session的定时验证检测程序类，以让无效的session释放
     */
    @Bean
    public ExecutorServiceSessionValidationScheduler sessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(180000);
        return scheduler;
    }

    /**
     * 会话管理器 ,时间单位是毫秒
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setCacheManager(shiroCacheManager());
        //删除在session过期跳转页面时自动在URL中添加JSESSIONID
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        //定义的是全局的session会话超时时间，此操作会覆盖web.xml文件中的超时时间配置
        sessionManager.setGlobalSessionTimeout(1800000);
        //删除所有无效的Session对象，此时的session被保存在了内存里面
        sessionManager.setDeleteInvalidSessions(true);
        //需要让此session可以使用该定时调度器进行检测
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //定义要使用的无效的Session定时调度器
        //sessionManager.setSessionValidationScheduler(sessionValidationScheduler());
        sessionManager.setSessionDAO(sessionDAO());
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(sessionIdCookie());
        return sessionManager;
    }

    /**
     * 自定义Realm
     */
    @Bean
    public MyRealm myShiroRealm() {
        MyRealm myShiroRealm = new MyRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        myShiroRealm.setCachingEnabled(true);
        //打开身份认证缓存
        myShiroRealm.setAuthenticationCachingEnabled(true);
        myShiroRealm.setAuthenticationCacheName("authenticationCache");
        //打开授权缓存
        myShiroRealm.setAuthorizationCachingEnabled(true);
        myShiroRealm.setAuthorizationCacheName("authorizationCache");
        return myShiroRealm;
    }

    /**
     * 安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        securityManager.setSessionManager(sessionManager());
        securityManager.setCacheManager(shiroCacheManager());
        securityManager.setRememberMeManager(cookieRememberMeManager());
        return securityManager;
    }

    /**
     * 会话DAO
     */
    @Bean
    public EnterpriseCacheSessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO en = new EnterpriseCacheSessionDAO();
        en.setActiveSessionsCacheName("shiro-activeSessionCache");
        en.setSessionIdGenerator(sessionIdGenerator());
        return en;
    }


    /**
     * 踢出登录拦截器
     */
    @Bean
    public KickoutSessionControlFilter kickoutSession() {
        log.info("*************kickoutFilter*****************");
        KickoutSessionControlFilter kickout = new KickoutSessionControlFilter();
        kickout.setSessionManager(sessionManager());
        kickout.setCacheManager(shiroCacheManager());
        //默认是false；即后者登录的用户踢出前者登录的用户
        kickout.setKickoutAfter(false);
        //默认1；比如2的意思是同一个用户允许最多同时两个人登录
        kickout.setMaxSession(1);
        //被踢出后重定向到的地址
        kickout.setKickoutUrl("/login?kickout=1");
        return kickout;
    }

    /**
     * 基于Form表单的身份验证过滤器
     */
    @Bean
    public MyFormAuthenticationFilter myFormAuthenticationFilter() {
        log.info("*************myFormAuthenticationFilter*****************");
        MyFormAuthenticationFilter myFormAuthenticationFilter = new MyFormAuthenticationFilter();
        myFormAuthenticationFilter.setUsernameParam("userName");
        myFormAuthenticationFilter.setPasswordParam("password");
        myFormAuthenticationFilter.setRememberMeParam("rememberMe");
        myFormAuthenticationFilter.setFailureKeyAttribute(CommonConstant.ShiroError.LOGIN_ERROR);
        //myFormAuthenticationFilter.setLoginUrl("/login");
        return myFormAuthenticationFilter;
    }

    /**
     * Shiro生命周期处理器
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,
     * 并在必要时进行安全逻辑验证 * 配置以下两个bean
     * (DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(securityManager());
        return aasa;
    }

    /**
     * 相当于调用SecurityUtils.setSecurityManager(securityManager)
     */
    @Bean
    public MethodInvokingFactoryBean getMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
        factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
        factoryBean.setArguments(securityManager());
        return factoryBean;
    }

}