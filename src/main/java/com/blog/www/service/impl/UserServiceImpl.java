package com.blog.www.service.impl;

import com.blog.www.mapper.CollectMapper;
import com.blog.www.mapper.UserMapper;
import com.blog.www.model.User;
import com.blog.www.service.MailService;
import com.blog.www.service.UserService;
import com.blog.www.utils.MD5Utils;
import com.blog.www.utils.RandomCodeUtils;
import com.blog.www.utils.UUIDUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * @author chenyu
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private MailService mailService;

    @Autowired
    CollectMapper collectMapper;

    /**
     * 注册用户
     *
     * @param user 用户 用户
     * @return 是否增加成功
     */
    @Override
    public boolean insert(User user) {
        user.setPassword(MD5Utils.encode(user.getPassword()));
        String code = UUIDUtils.getUUID();
        String subject = "验证你的邮箱";
        String context = "尊敬的" + user.getName() + "你好" +
                "点击该链接进行注册" +
                " http://39.97.252.246:8080/checkCode?code=" + code;
        if (mailService.sendMail(user.getMail(), subject, context)) {
            userMapper.insertUser(user);
            userMapper.insertCode(user.getId(), code);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findByAccountAndPassword(User user) {
        user.setPassword(MD5Utils.encode(user.getPassword()));
        return userMapper.findByAccountAndPassword(user);

    }

    @Override
    public User findUserByMail(String mail) {
        if (mail != null) {
            User user = userMapper.findUserByMail(mail);
            return user;
        }
        return null;
    }

    @Override
    public boolean accountExit(User user) {
        return userMapper.findByAccount(user) != 0;
    }

    @Override
    public boolean mailExit(User user) {
        return userMapper.findByMail(user) != 0;
    }

    @Override
    public boolean accountAndMailExist(User user) {
        return userMapper.findByAccountOrMail(user) != 0;
    }


    @Override
    public boolean findCode(String code) {
        int id = userMapper.findCode(code);
        if (id != 0) {
            userMapper.updateStatus(id);
            userMapper.deleteCode(id);
            collectMapper.insertDCollect(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean findCodeInForget(User user, String code) {
        if (userMapper.findCode(code) != null) {
            int id = userMapper.findCode(code);
            if (id == user.getId()) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean updatePassword(User user, String newPassword) {
        int count = userMapper.updatePassword(user.getId(), newPassword);
        if (count > 0) {
            return true;
        }
        return false;
    }


    @Override
    public boolean sendRandomCode(String mail) {
        if (mail != null) {
            User user = userMapper.findUserByMail(mail);
            if (user != null) {
                String code = RandomCodeUtils.getRandomCode();
                String subject = "博客|修改你的密码";
                String context = "尊敬的" + user.getName() + "你好" +
                        "你的验证码是:" +
                        code + "你可以用这个验证码修改你的博客密码,如果不是你的本人操作请忽略此条信息！";
                //将此验证码插入数据库，稍后进行验证
                //先删除数据库中的此条数据
                userMapper.deleteCode(user.getId());
                userMapper.insertCode(user.getId(), code);
                mailService.sendMail(user.getMail(), subject, context);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean forgetPassword(User user, String newPassword) {

        int count = userMapper.updatePassword(user.getId(), MD5Utils.encode(newPassword));
        if (count > 0) {
            user.setPassword(MD5Utils.encode(newPassword));
            userMapper.deleteCode(user.getId());

            return true;
        }
        return false;
    }
}
