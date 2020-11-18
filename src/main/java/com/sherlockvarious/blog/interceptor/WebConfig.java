package com.sherlockvarious.blog.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        ArrayList<String> patterns = new ArrayList<>();
        patterns.add("/page/admin/**");
        patterns.add("/admin/**");

        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns(patterns);

//                .excludePathPatterns("/page/about")
//                .excludePathPatterns("/page/index");
    }
}
