package com.wuying.service.impl;

import com.wuying.exception.BigEventException;
import com.wuying.mapper.UserMapper;
import com.wuying.pojo.User;
import com.wuying.service.UserService;
import com.wuying.utils.JwtUtil;
import com.wuying.utils.Md5Util;
import com.wuying.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册
     *
     * @param username
     * @param password
     */
    public void register(String username, String password) {
        //先判断该用户名是否存在
        if (userMapper.findByUsername(username) == null) {
            //不存在，则进行注册
            password = Md5Util.getMD5String(password);//密码转换
            userMapper.register(username, password);
        } else {
            //存在，则提示用户名已存在
            throw new BigEventException(888, "用户名已存在");
        }

    }

    /**
     * 用户登录函数，根据用户名和密码进行登录验证。
     *
     * @param username 用户名，用于查找对应的用户信息。
     * @param password 用户输入的密码，将进行MD5加密后与数据库中的密码进行比对。
     * @return 返回登录结果，如果登录成功则返回token，否则抛出异常。
     * @throws BigEventException 如果用户名或密码错误，抛出异常，异常代码为999，异常信息为"用户名或密码错误"。
     */
    public String login(String username, String password) {
        return Optional.ofNullable(userMapper.findByUsername(username))
                .filter(user -> Md5Util.getMD5String(password).equals(user.getPassword()))
                .map(user -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("id", user.getId());
                    map.put("username", user.getUsername());
                    return JwtUtil.genToken(map);
                })
                .orElseThrow(() -> new BigEventException(999, "用户名或密码错误"));
    }


    public User getUserInfo(Integer id) {
        return userMapper.getUserInfoById(id);
    }


    public void update(User user) {
        userMapper.updateUserInfo(user);
    }


    public void updateAvatar(String avatarUrl) {
        Integer id = (Integer) ((Map<String, Object>) ThreadLocalUtil.get()).get("id");
        userMapper.updateAvatar(avatarUrl, id);
    }


    /**
     * 更新用户密码
     *
     * @param pwdMap 包含旧密码、新密码和重复新密码的映射
     * @throws BigEventException 如果密码为空、两次输入的新密码不一致或旧密码不正确
     */
    public void updatePwd(Map<String, String> pwdMap) {
        // 从映射中获取旧密码、新密码和重复的新密码
        String oldPwd = pwdMap.get("oldPwd");
        String newPwd = pwdMap.get("newPwd");
        String rePwd = pwdMap.get("rePwd");

        // 检查密码是否为空
        if (oldPwd == null || oldPwd.isEmpty()
                || newPwd == null || newPwd.isEmpty()
                || rePwd == null || rePwd.isEmpty())
            throw new BigEventException(999, "密码不能为空");

        // 检查两次输入的新密码是否一致
        if (!newPwd.equals(rePwd)) throw new BigEventException(999, "两次密码不一致");

        // 从ThreadLocal中获取用户ID
        Integer id = (Integer) ((Map<String, Object>) ThreadLocalUtil.get()).get("id");
        // 根据用户ID查询数据库中的密码
        String dbPwd = userMapper.getUserInfoById(id).getPassword();
        // 检查输入的旧密码是否与数据库中的密码匹配
        if (!Md5Util.getMD5String(oldPwd).equals(dbPwd)) throw new BigEventException(999, "旧密码错误");

        // 更新数据库中的密码为新的MD5加密密码
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }
}