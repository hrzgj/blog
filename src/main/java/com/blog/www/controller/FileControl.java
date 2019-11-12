package com.blog.www.controller;

import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.User;
import com.blog.www.service.FileService;
import com.blog.www.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author: chenyu
 * @date: 2019/11/1 12:31
 */
@RestController
@CrossOrigin
//@Slf4j
public class FileControl {


    @Autowired
    private FileService fileService;

    @Value("${web.upload-path}")
    private String path;

    /**
     * 将用户头像文件下载到服务器，并删除用户之前的头像文件
     * @param file 文件
     * @param request 获取用户登录信息
     * @return  result
     */
    @PostMapping("/fileLoad")
    public Result upload(@RequestParam("photo") MultipartFile file, HttpServletRequest request){
        Result result=new Result();
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            result.setMsg("session失效，请重新登录");
            result.setCode(ResultCode.USER_SESSION_ERROR);
            return result;
        }
        if(user.getPhoto()!=null){
            String fileOldPath=path+user.getPhoto();
            result.setMsg(FileUtils.deleteFile(fileOldPath));
        }
        String fileName=file.getOriginalFilename();
        String fileNewName=FileUtils.getFileNewName(fileName);


        try {
            FileUtils.upload(file.getBytes(), path, fileNewName);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("下载失败");
            result.setCode(404);
            return result;
        }
        fileService.upload(fileNewName,user.getId());
        result.setMsg("下载成功，数据库更新成功");
        result.setCode(200);
        return result;

    }


//    @PostMapping("/test")
//    public  Result upload(HttpServletRequest request){
//        User user= (User) request.getSession().getAttribute("user");
//        String mes=FileUtils.deleteFile(path+user.getPhoto());
//        Result result=new Result();
//        result.setMsg(mes);
//        return result;
//
//    }




}
