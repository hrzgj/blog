package com.blog.www.controller;

import com.blog.www.model.User;
import com.blog.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author cy
 */
@Controller
public class UserControl {

    @Autowired
    UserService userService;

    @ResponseBody
    @PostMapping("/register")
    public int register(@RequestBody User user){
        return userService.insert(user);
    }

}
