package com.cxy.config;


import com.cxy.userDetailService.CustomUserDteailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengxinyu
 * SpringSecurity配置，数据库方式
 **/
@Configuration
@Slf4j(topic = "Logger")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("BCEncryptPasswordEncoder")
    private PasswordEncoder bcEpasswordEncoder;

    @Autowired
    private CustomUserDteailService customUserDteailService;


//    @Autowired
//    MyAccessDecisionManager myAccessDecisionManager;
//
//    @Autowired
//    MyFilterInvocationSecurityMetadataSource myFilterInvocationSecurityMetadataSource;
//
//    @Autowired
//    MySecurityFilter mySecurityFilter;

    /**
     * 配置UserService
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDteailService);
    }

    /**
     * 可以form表单和httpBasic(不安全)
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //开启配置
        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasRole("ADMIN")
                .antMatchers("/user/**")
                .hasAnyRole("ADMIN","USER")
                //              controller中配置
                .antMatchers("/db/**")
                .access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest()
                .authenticated()
                .and()
//                //默认表单登录页面
                .formLogin()
                //发送request为/login且带参数的登录请求
                .loginProcessingUrl("/login")
                //可以处理成功/失败跳转或自定义处理逻辑
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                //login接口不用登录即可访问
                .permitAll()
                .and()
                //配置登出
                .logout()
                .logoutUrl("/logout")
                //清除身份信息
                .clearAuthentication(true)
                //使Session失效
                .invalidateHttpSession(true)
                //退出工作//todo springsecurity定义了LogoutHandler的一些默认实现
                .addLogoutHandler((a,b,c)->{})
                .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                //csrf关闭
                .csrf()
                .disable();
    }


    private AuthenticationSuccessHandler successHandler(){
        return (request,response,auth)->{
            //登录信息，不包含密码
            Object principal = auth.getPrincipal ();
            response.setContentType ("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            response.setStatus(200);
            Map<String, Object> map= new HashMap<>() ;
            map.put ("status", 200);
            map.put ("principal", principal);
            ObjectMapper om = new ObjectMapper();
            //map->json对象
            out.write(om.writeValueAsString(map));
            out.flush();
            out.close();
        };

    }

    private AuthenticationFailureHandler failureHandler(){
        return new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                //登录信息，不包含密码
                response.setContentType ("application/json;charset=utf-8");
                PrintWriter out = response.getWriter();
                response.setStatus(401);//认证失败
                Map<String, Object> map= new HashMap<>() ;
                map.put ("status", 401);
                map.put("msg","登录失败");
                ObjectMapper om = new ObjectMapper();
                //map->json对象
                out.write(om.writeValueAsString(map));
                out.flush();
                out.close();
            }
        };
    }


    private LogoutSuccessHandler logoutSuccessHandler(){
        return   (request,response,auth)->{
            response.sendRedirect("/erroe_page");
        };
    }


//    @Override
//    public void configure(HttpSecurity http)throws Exception {
//        // 定义白名单:哪些URL需要被保护、哪些不需要被保护
//        http.authorizeRequests()
//                // 设置所有人都可以访问hello页面
//                .antMatchers("/hello").permitAll()
//                //test,test1下所有文件
//                .antMatchers("/test/**","/test1/**").permitAll()
//                .withObjectPostProcessor(new MyObjectPostProcessor())
//                //res下,js,html文件
//                .antMatchers("/res/**/*.{js,html}").permitAll()
//                .and();
//        // 配置请求拦截器(UsernamePasswordAuthenticationFilter之前)
//          http.addFilterBefore(mySecurityFilter, UsernamePasswordAuthenticationFilter.class);
//    }

//    private class MyObjectPostProcessor implements ObjectPostProcessor<FilterSecurityInterceptor> {
//        @Override
//        public <O extends FilterSecurityInterceptor> O postProcess(O fsi) {
//            fsi.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
//            fsi.setAccessDecisionManager(myAccessDecisionManager);
//            return fsi;
//        }
//    }


}
