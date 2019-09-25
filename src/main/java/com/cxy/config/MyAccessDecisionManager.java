package com.cxy.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.Collection;

/**
 * @author chengxinyu
 * @version 1.1.0
 * @description MyAccessDecisionManager
 * 获取被拦截的url和被拦截url所需的全部权限，
 * 然后根据所配的策略（有：一票决定，一票否定，少数服从多数等）
 * 如果权限足够，则返回，权限不够则报错并调用权限不足页面。
 * @date 2019年06月06日9:37
 **/
@Component
@Slf4j(topic = "Logger")
public class MyAccessDecisionManager implements AccessDecisionManager {
    /**
     * 判定是否拥有权限的角色，
     * (1)authentication :CustomUserService中循环添加到 GrantedAuthority 对象中的权限信息集合.
     * (2)object 包含客户端发起的请求的requset信息
     * 可转换为 HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
     * (3)configAttributes 为MyFilterInvocationSecurityMetadataSource的
     * getAttributes(Object)方法返回的结果，此方法是为了判定用户请求的url
     * 是否在权限表中，如果在权限表中，则返回给 decide 方法
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
       log.info("MyAccessDecisionManager.decide()");
        if(configAttributes == null  || configAttributes.size()==0) {
            throw new AccessDeniedException("permission denied");
        }
        String needRole;
        //遍历基于URL获取的权限信息和用户自身的角色信息进行对比.
        for(ConfigAttribute cfa: configAttributes) {
            //当前请求url需要的角色
            needRole = cfa.getAttribute();
            //登录过了，且访问的url没要求权限
            if("ROLE_VISTOR".equals(needRole)&&authentication instanceof UsernamePasswordAuthenticationToken){
                return;
            }
            //当前用户具备该url的角色
            for(GrantedAuthority grantedAuthority:authentication.getAuthorities()) {
                if(needRole.equals(grantedAuthority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
