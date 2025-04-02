package com.wuying.interceptors;

import com.wuying.exception.BigEventException;
import com.wuying.utils.JwtUtil;
import com.wuying.utils.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptors implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 登录拦截器，用于验证请求中的JWT（JSON Web Token）是否有效。
     * 该拦截器在请求处理之前执行，通过检查请求头中的Authorization字段来获取JWT，
     * 并尝试解析该JWT。如果解析失败，则抛出未登录异常。
     *
     * @param request  HTTP请求对象，包含请求头信息
     * @param response HTTP响应对象，用于设置响应状态
     * @param handler  当前请求的处理器对象
     * @return 如果JWT验证成功，返回true，继续执行后续请求；否则抛出异常，中断请求
     * @throws BigEventException 如果JWT解析失败，抛出401未登录异常
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取Authorization字段的值，即JWT
        String token = request.getHeader("Authorization");

        //从redis获取token
        String redisToken = stringRedisTemplate.opsForValue().get(token);
        if (redisToken == null)
            throw new BigEventException(401, "未登录");

        // 尝试解析JWT，如果解析失败则抛出未登录异常
        try {
            Map<String, Object> map = JwtUtil.parseToken(token);
            //把数据存入TreadLocal中
            ThreadLocalUtil.set(map);
        } catch (Exception e) {
            throw new BigEventException(401, "未登录");
        }

        // JWT验证成功，继续执行后续请求
        return true;
    }


    /**
     * 在请求处理完成后执行的操作
     * 此方法主要用于处理请求结束后的一些清理工作，确保每个请求的环境变量得到正确清除
     * 防止信息泄露或相互干扰
     *
     * @param request  HttpServletRequest对象，表示当前请求
     * @param response HttpServletResponse对象，表示当前响应
     * @param handler  处理当前请求的处理器对象
     * @param ex       请求处理过程中发生的异常（如果有）
     * @throws Exception 根据具体实现可能会抛出的异常
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求处理完成后，移除线程本地变量，以避免内存泄漏和数据污染
        ThreadLocalUtil.remove();
    }
}

