package com.teradata.interceptor;

import com.teradata.utils.Constant;
import com.teradata.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ("OPTIONS".equals(request.getMethod())) {
            return true;
        }
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new ServletException("invalid Authorization header");
        }
        String token = authHeader.substring(7);
        try {
            JwtUtil.checkToken(token);
            return true;
        } catch (Exception e) {
            throw new ServletException("Invalid Token");
        }
    }
}
