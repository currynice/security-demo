//package com.cxy.config;
//
//import com.cxy.service.PermissionService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.access.SecurityConfig;
//import org.springframework.security.web.FilterInvocation;
//import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//import org.springframework.stereotype.Component;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//
///**
// * @author chengxinyu
// * @version 1.1.0 //TODO 缓存实现
// * @description 认证数据规则源数据,  获取被拦截url所需的全部权限
// * @date 2019年06月05日17:02
// **/
////@Component
//public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
//    @Autowired
//    private PermissionService permissionService;
//
//    //URL匹配.
//    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
//
//    /**
//     * TODO swagger,webjars等等
//     * @return
//     */
//    private static List<String> allowedRequest(){
//        return Arrays.asList("/login","/css/**","/fonts/**","/js/**","/scss/**","/img/**");
//    }
//
//    /**
//     *  在我们初始化的权限数据中找到对应当前url的权限数据
//     */
//    @Override
//    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//        Map<RequestMatcher, Collection<ConfigAttribute>> map = permissionService.getPermissionMap();
//        FilterInvocation filterInvocation = (FilterInvocation) o;
//
//        System.out.println("当前请求:"+filterInvocation.getFullRequestUrl());
//
//        //允许访问de不做拦截
//        if (isMatcherAllowedRequest(filterInvocation)) {
//            return null;
//        }
//        //getRequest()一样的
//        HttpServletRequest request = filterInvocation.getHttpRequest();
//        //遍历初始化的权限数据，找到url对应的权限
//        for(Map.Entry<RequestMatcher,Collection<ConfigAttribute>> entry : map.entrySet()) {
//           if(entry.getKey().matches(request)){
//               System.out.println("对应的权限找到了");
//                return entry.getValue();
//        }
//
//    }
//        //方式一：没有匹配到,直接是白名单了.不登录也是可以访问的。
//        //return null;
//
//        //方式二：配有匹配到，需要指定相应的角色：()
//        return SecurityConfig.createList("ROLE_admin");
//    }
//
//    @Override
//    public Collection<ConfigAttribute> getAllConfigAttributes() {
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return FilterInvocation.class.isAssignableFrom(aClass);
//    }
//
//
//    /**
//     * 判断当前请求是否在允许请求的范围内
//     * @param filterInvocation 当前请求
//     * @return 是否在范围中
//     */
//    private boolean isMatcherAllowedRequest(FilterInvocation filterInvocation){
////        return allowedRequest().stream().map(AntPathRequestMatcher::new)
////                .filter(requestMatcher -> requestMatcher.matches(filterInvocation.getHttpRequest()))
////                .toArray().length > 0;
//        //遍历设定的表达式
//        for(String str:allowedRequest()){
//            if(antPathMatcher.match(str,filterInvocation.getFullRequestUrl())){
//                return true;
//            }
//        }
//            return false;
//    }
//
//}
