package com.wuying.service;

import com.wuying.pojo.User;

import java.util.Map;

public interface UserService {
    void register(String username, String password);

    String login(String username, String password);

    User getUserInfo(Integer id);

    void update(User user);

    void updateAvatar(String avatarUrl);

    void updatePwd(Map<String, String> pwdMap);
}
