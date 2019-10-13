package com.cxy.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;


/**
 * json方式登录,拦截/login 接口的request
 * {@link UsernamePasswordAuthenticationFilter}
 */
@Slf4j(topic = "Logger")
public class CustomUserPasswordFilter extends UsernamePasswordAuthenticationFilter {



    /**
     * 可以增加别的登录信息
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException  (抛出子类表示验证码出错或状态不对等，拒绝请求)
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //json请求，POST
            if (!"POST".equals(request.getMethod())) {
                throw new AuthenticationServiceException(
                        "Authentication method not supported: " + request.getMethod());
            }
            if (MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType()) || MediaType.APPLICATION_JSON_UTF8_VALUE.equals(request.getContentType())) {
                UsernamePasswordAuthenticationToken authRequest = null;
                try(InputStream in = request.getInputStream()){
                        ObjectMapper objectMapper= new ObjectMapper();
                        Map<String,Object> requestMap = objectMapper.readValue(in, Map.class);
                        String username = (String) requestMap.get("username");
                        String password = (String) requestMap.get("password");
                        if(username == null ||password == null ){
                            authRequest = new UsernamePasswordAuthenticationToken(username,password);
                        }else {
                            username = username.trim();
                            authRequest = new UsernamePasswordAuthenticationToken(username,password);
                        }
                    }catch (IOException e) {
                    authRequest = new UsernamePasswordAuthenticationToken("","");
                }finally {
                    setDetails(request, authRequest);
                }
                return this.getAuthenticationManager().authenticate(authRequest);

            }else {
                return super.attemptAuthentication(request, response);
            }
    }

}
