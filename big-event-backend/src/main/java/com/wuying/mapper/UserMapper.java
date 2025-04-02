package com.wuying.mapper;

import com.wuying.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where username = #{username}")
    User findByUsername(String username);

    void register(String username, String password);

    @Select("select * from user where id = #{id}")
    User getUserInfoById(Integer id);

    @Update("update user set nickname = #{nickname}, email = #{email}, user_pic = #{userPic},update_time=now() where id = #{id}")
    void updateUserInfo(User user);


    @Update("update user set user_pic = #{avatarUrl},update_time=now() where id = #{id}")
    void updateAvatar(String avatarUrl, Integer id);

    @Update("update user set password = #{md5String},update_time=now() where id = #{id}")
    void updatePwd(String md5String, Integer id);
}
