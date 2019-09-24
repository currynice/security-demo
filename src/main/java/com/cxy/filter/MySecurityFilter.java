//package com.cxy.filter;
//
//
//import com.cxy.config.MyAccessDecisionManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.SecurityMetadataSource;
//import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.GenericFilterBean;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// *
// * @description 请求之前的拦截器 访问url时，会通过AbstractSecurityInterceptor拦截器拦截
// */
////@Component
//public class MySecurityFilter extends OncePerRequestFilter {
//	@Override
//	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//		System.out.println("MySecurityFilter过滤器");
//
//		filterChain.doFilter(httpServletRequest,httpServletResponse);
//	}
//
//
////	@Override
////	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
////			throws ServletException, IOException {
////		response.setHeader("Access-Control-Allow-Origin", "*");
////		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
////		response.setHeader("Access-Control-Max-Age", "3600");
////		response.setHeader("Access-Control-Allow-Headers", "*");
////		response.setHeader("Access-Control-Allow-Credentials", "true");
////		String authToken = request.getHeader("$authToken");
////		if (authToken != null) {
////			// 验证authToken是否(数据库验证真伪)TODO
////			//序列化直接取出来
////			//String userInfo = redisService.get(authToken);
////			String loginInfo =null;
////			if (userInfo != null && !userInfo.isEmpty()) {
////				List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
////				simpleGrantedAuthorities.addAll(userInfo.().stream()
////						.filter(role -> StringUtils.isNotBlank(role.getRoleCode()))
////						.map(role -> new SimpleGrantedAuthority(role.getRoleCode())).collect(Collectors.toList()));
////				simpleGrantedAuthorities.add(new SimpleGrantedAuthority("HAS_LOGIN"));
////				// 设置超级管理员的账号
////				if ("admin".equalsIgnoreCase(userLoginInfo.getAccount())) {
////					simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
////				}
////				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
////						userLoginInfo, null, simpleGrantedAuthorities);
////				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
////				// 将登录信息写进redis
////				redisService.set(authToken, userInfo, Long.valueOf(loginDate));
////			}
////		}
////		request.setAttribute("databaseName", databaseName);
////		chain.doFilter(request, response);
////	}
//
//
//}
