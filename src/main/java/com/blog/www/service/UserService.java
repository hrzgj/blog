package com.blog.www.service;

import com.blog.www.model.User;
import org.omg.PortableInterceptor.USER_EXCEPTION;
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

    /**
     * 寻找随机验证码，用于用户忘记密码时使用
     * @param code 随机验证码
     * @param user 用户
     * @return 是否找到
     */
    boolean findCodeInForget(User user,String code);


    /**
     * 修改密码的操作
     * @param user 用户
     * @param newPsw 新密码
     * @return 是否修改成功
     */
    boolean updatePassword(User user,String newPsw);

    /**
     * 发送随机验证码
     * @param user 用户
     * @return 是否发送成功
     */
    boolean sendRandomCode(User user);

    /**
     * 忘记密码
     * @param user
     * @param newPassword
     * @return
     */
    boolean forgetPassword(User user,String newPassword);
}
