package com.cxy.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @description 未登录或者token过期的跳转界面
 */
@Component
public class AuthenticationFailureHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(203);
		Map<String,Object> map = new HashMap<>();
		ObjectMapper objectMapper = new ObjectMapper();
		map.put("code", 203);
		map.put("msg", "请登陆");
		// 跳转地址
		map.put("data","/index.html");
		PrintWriter out = response.getWriter();
		out.write(objectMapper.writeValueAsString(map));
		out.flush();
		out.close();
	}
}
