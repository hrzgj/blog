package com.blog.www.service;

import com.blog.www.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cy
 */

public interface UserService {

    /**
     * 注册用户
     * @param user 用户
     * @return "1"--用户存在，插入失败             "2"--用户不存在，插入成功
     */
    @Transactional
    String insert(User user);

    /**
     * 登录
     * @param user 用户
     * @return "1" 用户存在，可以登录 "0"用户不存在，无法登录
     */
    String findByAccountAndPassword(User user);


    /**
     * 判断是否账户和邮箱是否存在
     * @param user 用户
     * @return 是否存在
     */
    boolean accountAndMailExist(User user);
}
