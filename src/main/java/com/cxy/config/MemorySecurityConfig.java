package com.cxy.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 基于内存的认证
 *   EnableGlobalMethodSecurity 开启方法级别安全控制(注解)
 *  （1）prePostEnabled :决定注解[@PreAuthorize,@PostAuthorize]是否可用，一前一后(方法)
 *   PreAuthorize指定访问该方法需要的角色
 *
 * （2）secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用。
 *
 * （3）jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed..] 是否可用。
 *
 */
@Configuration
@Slf4j(topic = "Logger")
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class MemorySecurityConfig extends WebSecurityConfigurerAdapter {



    /**
     * 必须指定加密方式,测试
     * {@link PasswordEncoder} 也可以自己实现
     * @return
     */
//    @Bean
//    public PasswordEncoder noEncrypt(){
//       return NoOpPasswordEncoder.getInstance();
//    }

    /**
     * BCypt强哈希加密,相当于随机盐 也放在了密码里,方便安全,省掉维护salt字段和迭代次数的烦恼
     * strength :迭代次数(4-31,默认10)
     * {@link PasswordEncoder} 也可以自己实现
     * @return
     */
    @Bean(name = "BCEncryptPasswordEncoder")
    public PasswordEncoder BCEncrypt(){
        return new BCryptPasswordEncoder(10);
    }
    /**
     * 内存中配置用户认证信息,并添加角色 注释,设置两个用户,配置角色不需要加 ROLE_前缀
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //password:123
        auth.inMemoryAuthentication().withUser("cxy").password("$2a$10$JwcjhIKY1R1OYROjwxiWE.Jp17mG.wC0/6oZmrZTheX43TY.WxvwG").roles("ADMIN","USER")
                .and().withUser("cxy2").password("$2a$10$E/h.WwG4zjrYNhSrZ00Z5ujAjDjbPPyUBULO2UJdR6brO2QyTg7ly").roles("USER")
                .and().withUser("cxy3").password("$2a$10$VVMjfZa6Idg4dIEx90hciOoR2fmjh6JO3w7Pp1tzTOJvmIoN5rwb.").roles("ADMIN","DBA")
                 .and().withUser("special").password("$2a$10$Bbxgh6/RQcfBiflOBwMJgeTA9e2G1lWstowsX92yQklOwDopzat7C").roles("SPECIAL","USER");
    }

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
                //login_page为页面，发送request为/login且带参数的登录请求
                .loginPage("/login_page")
                .loginProcessingUrl("/login")
                .usernameParameter("name")//username->name
                .passwordParameter("psd")//password->psd
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



}
