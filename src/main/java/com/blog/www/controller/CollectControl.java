package com.blog.www.controller;

import com.blog.www.model.Collect;
import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.UserCollect;
import com.blog.www.model.*;
import com.blog.www.service.CollectService;
import com.blog.www.utils.CheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
     * 用户修改一篇博客的收藏夹,从非默认移到非默认的收藏夹
     * @param userCollectId 新收藏夹id
     * @param blogId 博客id
     * @param oldCollectId 旧收藏夹id
     * @param request 获取user
     * @return 结果
     */
    @GetMapping("/changeCollect")
    public Result changeBlogCollect(@RequestParam(value = "userCollectId") Integer userCollectId,
                                    @RequestParam(value = "blogId")Integer blogId,
                                    @RequestParam(value = "oldCollectId") Integer oldCollectId,
                                    HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        if(userCollectId==null||blogId==null||oldCollectId==null){
            result.setCode(ResultCode.OBJECT_NULL);
            return result;
        }
        Collect collect=new Collect();
        collect.setBlogId(blogId);
        collect.setUserCollectId(userCollectId);
        User user= (User) request.getSession().getAttribute("user");
        int flag=collectService.changeCollect(collect,oldCollectId,user.getId());
        result.setCode(flag);
        if(flag==ResultCode.SUCCESS){
            result.setMsg("更换成功");
            return result;
        }else if(flag==ResultCode.BLOG_NOT_EXIT){
            result.setMsg("原收藏夹博客不存在");
            return result;
        }else if(flag==ResultCode.BLOG_EXIT){
            result.setMsg("要移入的收藏夹博客已存在");
            return result;
        }else if(flag==ResultCode.COLLECT_ERROR){
            result.setMsg("两个收藏夹可能不属于该登录用户");
            return result;
        }
        else {
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
        if(userCollect.getName()==null||userCollect.getUserId()==null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("传参为空");
            return result;
        }
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        if(collectService.insertUserCollect(userCollect)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("新增收藏夹成功");
            result.setData(userCollect);
            return result;
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("新增收藏夹失败");
            return result;
        }

    }



    /**
     *将博客保存至默认收藏夹中
     * @param collect 收藏夹
     * @return 结果
     */
    @PostMapping("/addBlogToAuto")
    public  Result addBlogToAuto(@RequestBody Collect collect){
        Result result = new Result();
        if (collect == null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("用户对象为空");
        }else {
            if (collectService.insertCollectToAuto(collect)) {
                result.setCode(ResultCode.SUCCESS);
                result.setMsg("成功保存博客至默认收藏夹");
                result.setData(collect);
            }else{
                result.setCode(ResultCode.UNSPECIFIED);
                result.setMsg("保存至默认收藏夹失败");
            }
        }
        return  result;
    }

    /**
     * 登录用户查看自己的收藏夹
     * @param request 获取登录用户信息
     * @return  结果
     */
    @PostMapping("/findAllCollect")
    public Result<List<UserCollect>> findAllCollect(HttpServletRequest request){
        Result<List<UserCollect>> result=new Result<>();
        User user= (User) request.getSession().getAttribute("user");
        if(CheckUtils.userSessionTimeOut(request, result)){
            return result;
        }
        List<UserCollect> list=collectService.findUserCollect(user);
        if(list!=null){
            result.setData(list);
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("查询成功");
            return result;

        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 删除用户某个收藏夹，同时删除该收藏夹下收藏的博客记录
     * @param userCollect 收藏夹
     * @param request   获取用户登录信息
     * @return  结果
     */
    @PostMapping("/deleteUserCollect")
    public Result deleteUserCollect(@RequestBody UserCollect userCollect,HttpServletRequest request){
        Result result=new Result();
        if(userCollect.getUserId()==null||userCollect.getId()==null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("传参为空");
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        if(CheckUtils.userSessionTimeOut(request,result)|| CheckUtils.userRightIsTrue(user.getId(),userCollect.getUserId(),result)){
            return result;
        }
        if(collectService.deleteUserCollect(userCollect)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("删除收藏夹成功");
            return result;
        }else {
            result.setMsg("删除收藏夹失败");
            result.setCode(ResultCode.UNSPECIFIED);
            return result;
        }
    }

    /**
     * 用户将某一个博客存入非默认收藏夹
     * @param collect 博客和收藏夹中间表
     * @return  结果
     */
    @PostMapping("/addBlogCollect")
    public Result addBlogCollect(@RequestBody Collect collect,HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)||CheckUtils.checkCollect(collect,result)){
            return result;
        }
        User user= (User) request.getSession().getAttribute("user");
        int flag=collectService.addCollectBlog(collect,user.getId());
        result.setCode(flag);
        if(flag==ResultCode.SUCCESS){
            result.setMsg("博客添加收藏夹成功");
            return result;
        }
        else if(flag==ResultCode.BLOG_EXIT){
            result.setMsg("收藏夹已存在该博客，无需添加");
            return result;
        }
        else if(flag==ResultCode.RIGHT_ERROR){
            result.setMsg("该收藏夹不是此用户拥有");
            return result;
        }
        else {
            result.setMsg("博客添加收藏夹失败");
            return result;
        }
    }


    /**
     * 用户将一个博客从非默认收藏夹移入默认收藏夹
     * @param collect 非默认收藏夹
     * @param request 获取登录用户信息
     * @return     是否移入成功
     */
    @PostMapping("/changeToNormal")
    public Result changeToNormal(@RequestBody Collect collect,HttpServletRequest request){
        Result result=new Result();

        User user= (User) request.getSession().getAttribute("user");
        if(CheckUtils.userSessionTimeOut(request,result)||CheckUtils.checkCollect(collect,result)){
            return result;
        }
        int flag=collectService.changeToNormal(collect,user.getId());
        result.setCode(flag);
        if(flag==ResultCode.SUCCESS){
            result.setMsg("修改至默认收藏夹成功");
            return result;
        }else if(flag==ResultCode.BLOG_NOT_EXIT){
            result.setMsg("非默认收藏夹无该博客，无法修改");
            return result;
        }
        else if(flag==ResultCode.BLOG_EXIT){
            result.setMsg("默认收藏夹已有该博客");
            return result;
        }
        else {
            result.setMsg("修改至默认收藏夹失败");
            return result;
        }
    }

    /**
     * 用户删除非默认收藏夹的一篇博客
     * @param collect 收藏夹
     * @param request 用户登录信息
     * @return  结果
     */
    @PostMapping("/deleteCollectBlog")
    public Result deleteCollectBlog(@RequestBody Collect collect,HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)||CheckUtils.checkCollect(collect,result)){
            return result;
        }
        if(collectService.deleteCollectBlog(collect)){
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("删除成功");
            return result;
        }else {
            result.setCode(ResultCode.BLOG_NOT_EXIT);
            result.setMsg("删除失败，博客不存在");
            return result;
        }
    }

    /**
     * 删除默认收藏夹的一篇博客
     * @param collect 收藏夹
     * @param request 获取用户登录信息
     * @return 结果
     */
    @PostMapping("/deleteNormalColBlog")
    public Result deleteNormalColBlog(@RequestBody Collect collect,HttpServletRequest request){
        Result result=new Result();
        if(CheckUtils.userSessionTimeOut(request,result)||CheckUtils.checkCollect(collect,result)){
            return result;
        }
        if(collectService.deleteNormalCollectBlog(collect)){
            result.setMsg("删除成功");
            result.setCode(ResultCode.SUCCESS);
            return result;
        }else{
            result.setCode(ResultCode.BLOG_NOT_EXIT);
            result.setMsg("删除失败,博客不存在");
            return result;
        }
    }

    /**
     * 更新非默认收藏夹名称和简介
     * @param userCollect 用户收藏夹
     * @param request 用户登录信息
     * @return 结果
     */
    @PostMapping("/updateColNameIntro")
    public Result updateCollectNameAndIntro(@RequestBody UserCollect userCollect,HttpServletRequest request){
        Result result=new Result();
        User user= (User) request.getSession().getAttribute("user");
        //判断传参是否为空
        if(userCollect.getName()==null||userCollect.getId()==null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("传参为空");
            return result;
        }
        //判断session是否过期和用户权限是否正确
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        if(collectService.updateCollectNameAndIntro(userCollect)){
            result.setMsg("更新成功");
            result.setCode(ResultCode.SUCCESS);
            return result;
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("更新失败");
            return result;
        }
    }

    /**
     * 更新默认收藏夹名称
     * @param userCollect 默认收藏夹
     * @param request 登录用户信息
     * @return 结果
     */
    @PostMapping("/updateNormalCollectName")
    public Result updateNormalCollectName(@RequestBody UserCollect userCollect,HttpServletRequest request){
        Result result=new Result();
        //判断传参是否为空
        if(userCollect.getName()==null||userCollect.getId()==null){
            result.setCode(ResultCode.OBJECT_NULL);
            result.setMsg("传参为空");
            return result;
        }
        //判断session是否过期
        if(CheckUtils.userSessionTimeOut(request,result)){
            return result;
        }
        if(collectService.updateNormalCollectName(userCollect)){
            result.setMsg("更新成功");
            result.setCode(ResultCode.SUCCESS);
            return result;
        }else {
            result.setCode(ResultCode.UNSPECIFIED);
            result.setMsg("更新失败");
            return result;
        }
    }

    /**
     * 用户将一个博客从默认移入非默认收藏夹
     * @param collect  非默认收藏夹
     * @param request 获取登录用户信息
     * @return 是否移入成功
     */
    @PostMapping("/changeNorToUnNormal")
    public Result changeNorToUnNormal(@RequestBody Collect collect ,HttpServletRequest request){
        Result result = new Result();
        User user= (User) request.getSession().getAttribute("user");
        if(CheckUtils.userSessionTimeOut(request,result)||CheckUtils.checkCollect(collect,result)){
            return result;
        }
        int flag = collectService.changeNorToUnNormal(collect,user.getId());
        result.setCode(flag);
        if (flag == ResultCode.SUCCESS){
            result.setMsg("将博客移入收藏夹成功");
        }else if (flag == ResultCode.BLOG_NOT_EXIT){
            result.setMsg("博客在默认收藏夹中不存在");
        }else if(flag ==ResultCode.BLOG_EXIT){
            result.setMsg("博客在非默认收藏夹存在");
        }
        else{
            result.setMsg("将博客移动收藏夹失败");
        }
        return result;
    }


}
