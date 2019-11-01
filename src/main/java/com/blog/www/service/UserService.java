package com.blog.www.service;

import com.blog.www.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author cy
 */

public interface UserService {

    /**
     * 注册用户
     * @param user 用户
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    boolean insert(User user);

    /**
     * 登录
     * @param user 用户
     * @return true用户可以登录 false没有该用户，账户密码错误
     */
    User findByAccountAndPassword(User user);


    /**
     * 判断是否账户和邮箱是否存在
     * @param user 用户
     * @return 是否存在
     */
    boolean accountAndMailExist(User user);

    /**
     * 验证用户注册
     * @param code  验证码
     * @return  是否找到
     */
    boolean findCode(String code);


}
