package com.wuying.config;

import com.wuying.interceptors.LoginInterceptors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类，用于配置Spring MVC的拦截器。
 * 该类实现了WebMvcConfigurer接口，通过重写addInterceptors方法，
 * 将登录拦截器（LoginInterceptors）注册到拦截器链中，并排除不需要拦截的路径。
 *
 * 主要功能：
 * 1. 自动注入登录拦截器实例。
 * 2. 将登录拦截器添加到拦截器注册表中，并排除登录和注册路径。
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptors loginInterceptors;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 将登录拦截器添加到拦截器注册表中，并排除登录和注册路径
        registry.addInterceptor(loginInterceptors).excludePathPatterns("/user/login", "/user/register");
    }
}

