package com.wuying.controller;

import com.wuying.pojo.Result;
import com.wuying.pojo.User;
import com.wuying.service.UserService;
import com.wuying.utils.ThreadLocalUtil;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Value("${jwt.expiration-time}")
    private static long expirationTime;
    @Autowired
    private UserService userService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        userService.register(username, password);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$") String password) {
        String token = userService.login(username, password);
        //将token存入redis中
        stringRedisTemplate.opsForValue().set(token, token, expirationTime);
        return Result.success(token);
    }


    @GetMapping("/userInfo")
    public Result<User> getUserInfo() {
        Integer id = (Integer) ((Map<String, Object>) ThreadLocalUtil.get()).get("id");
        User user = userService.getUserInfo(id);
        return Result.success(user);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        // 如果用户ID为空或无效，则从ThreadLocal中获取当前用户ID并设置
        if (user.getId() == null || user.getId().toString().trim().isEmpty()) {
            Integer currentUserId = (Integer) ((Map<String, Object>) ThreadLocalUtil.get()).get("id");
            user.setId(currentUserId);
        }
        userService.update(user);
        return Result.success();
    }

    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> pwdMap, @RequestHeader("Authorization") String token) {
        userService.updatePwd(pwdMap);
        //从redis中移除token
        stringRedisTemplate.opsForValue().getOperations().delete(token);
        return Result.success();
    }

}
