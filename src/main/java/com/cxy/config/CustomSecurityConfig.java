//package com.cxy.config;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.ObjectPostProcessor;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
///**
// * @author chengxinyu
// * @version 1.1.0
// * @description CustomSecurityConfig
// * @date 2019年06月04日17:29
// * EnableGlobalMethodSecurity 开启方法级别安全控制
// *  （1）prePostEnabled :决定Spring Security的前注解是否可用 [@PreAuthorize,@PostAuthorize,..]
//     PreAuthorize指定访问该方法需要的角色
//
//    （2）secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用。
//
//    （3）jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed..] 是否可用。
// **/
//@Configuration
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
//
////    @Autowired
////    MyAccessDecisionManager myAccessDecisionManager;
////
////    @Autowired
////    MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;
////
////    @Autowired
////    MySecurityFilter mySecurityFilter;
//
//
////    @Override
////    public void configure(HttpSecurity http)throws Exception {
////        // 定义白名单:哪些URL需要被保护、哪些不需要被保护
////        http.authorizeRequests()
////                // 设置所有人都可以访问hello页面
////                .antMatchers("/hello").permitAll()
////                //test,test1下所有文件
////                .antMatchers("/test/**","/test1/**").permitAll()
////                .withObjectPostProcessor(new MyObjectPostProcessor())
////                //res下,js,html文件
////                .antMatchers("/res/**/*.{js,html}").permitAll()
////                .and();
////        // 配置请求拦截器(UsernamePasswordAuthenticationFilter之前)
////          http.addFilterBefore(mySecurityFilter, UsernamePasswordAuthenticationFilter.class);
////    }
//
////    private class MyObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
////        @Override
////        public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
////            fsi.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
////            fsi.setAccessDecisionManager(myAccessDecisionManager);
////            return fsi;
////        }
////    }
//
//
//}
