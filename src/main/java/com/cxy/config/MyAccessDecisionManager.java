//package com.cxy.config;
//
//
//import org.springframework.security.access.AccessDecisionManager;
//import org.springframework.security.access.AccessDeniedException;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.InsufficientAuthenticationException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.Iterator;
//
///**
// * @author chengxinyu
// * @version 1.1.0
// * @description MyAccessDecisionManager
// * 获取被拦截的url和被拦截url所需的全部权限，
// * 然后根据所配的策略（有：一票决定，一票否定，少数服从多数等）
// * 如果权限足够，则返回，权限不够则报错并调用权限不足页面。
// * @date 2019年06月06日9:37
// **/
////@Component
//public class MyAccessDecisionManager implements AccessDecisionManager {
//    /**
//     * 判定是否拥有权限的决策方法，
//     * (1)authentication :CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
//     * (2)object 包含客户端发起的请求的requset信息
//     * 可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
//     * (3)configAttributes 为MyFilterInvocationSecurityMetadataSource的
//     * getAttributes(Object object)这个方法返回的结果，此方法是为了判定用户请求的url
//     * 是否在权限表中，如果在权限表中，则返回给 decide 方法
//     */
//    @Override
//    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
//        System.out.println("MyAccessDecisionManager.decide()");
//        if(configAttributes == null  || configAttributes.size()==0) {
//            throw new AccessDeniedException("permission denied");
//        }
//
//        ConfigAttribute cfa;
//        String needRole;
//        //遍历基于URL获取的权限信息和用户自身的角色信息进行对比.
//        for(Iterator<ConfigAttribute> it = configAttributes.iterator(); it.hasNext();) {
//            cfa = it.next();
//            //当前请求url需要的权限
//            needRole = cfa.getAttribute();
//            System.out.println("decide,needRole:"+needRole+",authentication="+authentication);
//            //authentication 为CustomUserDetailService中添加的权限信息.
//            for(GrantedAuthority grantedAuthority:authentication.getAuthorities()) {
//                if(needRole.equals(grantedAuthority.getAuthority())) {
//                    return;
//                }
//            }
//        }
//        throw new AccessDeniedException("权限不足");
//    }
//
//    @Override
//    public boolean supports(ConfigAttribute configAttribute) {
//        return true;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//}
