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
    int insert(User user);
}
