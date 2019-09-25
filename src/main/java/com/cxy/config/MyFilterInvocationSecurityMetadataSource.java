package com.cxy.config;

import cn.hutool.core.util.StrUtil;
import com.cxy.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author chengxinyu
 * {@link DefaultFilterInvocationSecurityMetadataSource} 默认实现，参考
 * @version 1.1.0 //TODO 缓存实现
 * @description 认证数据规则源数据,  获取被拦截url所需的全部权限
 * @date 2019年06月05日17:02
 **/
@Component
@Slf4j(topic = "Logger")
public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private PermissionService permissionService;

    //URL匹配.
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    /**
     * TODO swagger,webjars等等
     * @return
     */
    private static List<String> allowedRequest(){
        return Arrays.asList("/login","/css/**","/fonts/**","/js/**","/scss/**","/img/**");
    }

    /**
     *  在我们初始化(缓存)的权限数据中找到对应当前url的权限数据
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o){
        Map<RequestMatcher, Collection<ConfigAttribute>> permissionMap = permissionService.getPermissionMap();
        FilterInvocation filterInvocation = (FilterInvocation) o;
        log.info("当前请求:"+filterInvocation.getFullRequestUrl());

        //getRequest()一样的
        HttpServletRequest request = filterInvocation.getHttpRequest();
        //允许访问de不做拦截
        if (isMatcherAllowedRequest(request)) {
            return null;
        }
        //遍历初始化的权限数据，找到url对应的权限
        for(Map.Entry<RequestMatcher,Collection<ConfigAttribute>> entry : permissionMap.entrySet()) {
           if(entry.getKey().matches(request)){
               log.info(StrUtil.format("{}对应的权限找到了{}",request.getRequestURL()),entry.getValue().toArray());
                return entry.getValue();
        }

    }
        //根据业务决定
        //方式一：没有匹配到,直接是白名单了.不登录也是可以访问的。
        //return null;

        //方式二：没有匹配到，需要指定相应的角色：()
        return SecurityConfig.createList("ROLE_VISTOR");
    }


    /**
     * getAllConfigAttributes 方法用来返回所有定义好的权限资源， Spring Security 在启动时会校验
     * 相关配置是否正确 ，如果 需要校验，那么该方法直接返回 null 即可
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 对象是否支持校验
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }


    /**
     * 判断当前请求是否在允许请求的范围内
     * @param request 当前请求
     * @return 是否在范围中
     */
    private boolean isMatcherAllowedRequest(HttpServletRequest request){
        return allowedRequest().stream().map(AntPathRequestMatcher::new)
                .filter(requestMatcher -> requestMatcher.matches(request))
                .toArray().length > 0;
        //遍历设定的表达式
//        boolean isInScope = false;
//        for(String str:allowedRequest()){
//            isInScope =  antPathMatcher.match(str,filterInvocation.getFullRequestUrl());
//            break;
//        }
//        return isInScope;
    }



}
