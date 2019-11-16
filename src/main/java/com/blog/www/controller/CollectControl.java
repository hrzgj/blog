package com.blog.www.controller;

import com.blog.www.model.Collect;
import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.UserCollect;
import com.blog.www.service.CollectService;
import com.blog.www.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: chenyu
 * @date: 2019/11/16 20:12
 */
@CrossOrigin
@RestController
public class CollectControl {

    @Autowired
    CollectService collectService;

    /**
     * 用户修改一篇博客的收藏夹
     * @param collect 博客收藏夹的中间表
     * @param request 获取user
     * @return 结果
     */
    @PostMapping("/changeCollect")
    public Result changeBlogCollect(@RequestBody Collect collect, HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        if(collectService.changeCollect(collect)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("更换成功");
            return result;
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("更换失败");
            return result;
        }

    }

    /**
     * 登录用户新增一个收藏夹
     * @param userCollect 收藏夹
     * @param request   获取user
     * @return  结果
     */
    @PostMapping("/addCollect")
    public Result addUserCollect(@RequestBody UserCollect userCollect, HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        if(collectService.insertUserCollect(userCollect)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("新增收藏夹成功");
            return result;
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("新增收藏夹失败");
            return result;
        }

    }
}
