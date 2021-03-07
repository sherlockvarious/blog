package com.sherlockvarious.blog.interceptor;

import com.sherlockvarious.blog.entity.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author sunchao
 * @created at 2020-11-12-22:15
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/page/login/login");
            return false;
        }

        User user = (User) request.getSession().getAttribute("user");
        if (user.getId() != 10002) {
            response.sendRedirect("/page/login/login");
            return false;
        }
        return true;
    }
}
