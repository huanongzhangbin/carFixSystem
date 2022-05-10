package com.example.carfixsystem.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.carfixsystem.entity.Employee;
import com.example.carfixsystem.service.IEmployeeService;
import com.example.carfixsystem.util.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

    @Slf4j
    public class AuthenticationInterceptor implements HandlerInterceptor {
        @Autowired
        IEmployeeService iEmployeeServiceService;
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String token = request.getHeader("Authorization");
          //  log.info(token+"Jjjjjjjjjjjjjj");
            Map<String,Object> map = new HashMap<>();
            try {
                DecodedJWT jwt= JWTUtils.parseToken(token);
                String id= String.valueOf(jwt.getClaim("id"));
               // log.info("id"+id);
             //  String t= (String) request.getSession().getAttribute("token"+id);
               //  if(t.equals(token)) {
                 //    log.info("通过");
                     JWTUtils.verify(token);
                     return true;
                 //}
            } catch (TokenExpiredException e) {
                map.put("state", false);
                map.put("msg", "Token已经过期!!!请重新登录");
            } catch (SignatureVerificationException e){
                map.put("state", false);
                map.put("msg", "签名错误!!!");
            } catch (AlgorithmMismatchException e){
                map.put("state", false);
                map.put("msg", "加密算法不匹配!!!");
            } catch (Exception e) {
                e.printStackTrace();
                map.put("state", false);
                map.put("msg", "无效token~~");
            }
            map.put("code","407");
            String json = new ObjectMapper().writeValueAsString(map);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            return false;
        }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
