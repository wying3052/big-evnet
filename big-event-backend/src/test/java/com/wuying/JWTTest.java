package com.wuying;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;

public class JWTTest {

    //生成token
    @Test
    public void testCreate() {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("id", 1);
        claims.put("username", "wuying");

        String token = JWT.create().withClaim("user", claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))//设置过期时间 - 7天
                .sign(Algorithm.HMAC256("wuuying"));//设置签名算法、密钥

        System.out.println(token);
    }

    /**
     * 测试解析JWT（JSON Web Token）的功能。
     */
    @Test
    public void testParse() {
        // 定义一个JWT字符串，包含用户信息和过期时间等数据
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6Ind1eWluZyJ9LCJleHAiOjE3NDQxNjkxODh9.Ysx3W8pNdCjJUBjQ7xta4vcHF8yCNk1_fUEo4QLzLnw";
        // 使用HMAC256算法和指定的密钥创建一个JWT验证器
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("wuuying")).build();
        // 使用验证器对JWT进行验证，并解析出其中的声明（Claims）
        DecodedJWT jwt = jwtVerifier.verify(token);
        System.out.println(jwt.getClaims().get("user"));
    }
}
