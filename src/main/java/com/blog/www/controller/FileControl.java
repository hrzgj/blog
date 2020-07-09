package com.blog.www.controller;

import com.blog.www.model.Result;
import com.blog.www.model.ResultCode;
import com.blog.www.model.User;
import com.blog.www.service.FileService;
import com.blog.www.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;


/**
 * @author: chenyu
 * @date: 2019/11/1 12:31
 */
@RestController
//@CrossOrigin
//@RequestMapping("/api")
public class FileControl {


    @Autowired
    private FileService fileService;

    @Value("${web.upload-path}")
    private String path;

    @Value("${web.url}")
    private String url;

    /**
     * 将用户头像文件下载到服务器，并删除用户之前的头像文件
     * @param file 文件
     * @param request 获取用户登录信息
     * @return  result
     */
    @PostMapping("/fileLoad")
    public Result<String> upload(@RequestParam("photo") MultipartFile file, HttpServletRequest request){
        Result<String> result=new Result<>();
        User user= (User) request.getSession().getAttribute("user");
        StringBuffer msg =new StringBuffer();
        if(user==null){
            result.setMsg("session失效，请重新登录");
            result.setCode(ResultCode.USER_SESSION_ERROR);
            return result;
        }
        if(file==null){
            result.setMsg("文件为空");
            result.setCode(ResultCode.FILE_NULL);
            return result;
        }
        if(user.getPhoto()!=null){
            String fileOldPath=path+"user/"+user.getPhoto();
            if(FileUtils.deleteFile(fileOldPath)){
                msg.append("删除原头像成功,");
            }else {
                msg.append("删除原头像失败,");
            }

        }
        String fileName=file.getOriginalFilename();
        String fileNewName=FileUtils.getFileNewName(fileName);
        try {
            FileUtils.upload(file.getBytes(), path+"user/", fileNewName);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("下载失败");
            result.setCode(ResultCode.UNSPECIFIED);
            return result;
        }
        fileService.upload(fileNewName,user.getId());
        msg.append("下载新头像成功，并存入数据库");
        result.setMsg(String.valueOf(msg));
        result.setCode(ResultCode.SUCCESS);
        result.setData(fileNewName);
        user.setPhoto(fileNewName);
        request.getSession().setAttribute("user",user);
        return result;

    }

    /**
     * 将博客图片文件下载到服务器
     * @param file 文件
     * @param request 获取用户登录信息
     * @return  result
     */
    @PostMapping("/uploadPicture")
    public Result<String> uploadPicture(@RequestParam("photo") MultipartFile file, HttpServletRequest request){
        Result<String> result=new Result<>();
        if(file==null){
            result.setMsg("文件为空");
            result.setCode(ResultCode.FILE_NULL);
            return result;
        }
        String filePath=path+"blog/";
        String fileNewName=FileUtils.getFileNewName(file.getOriginalFilename());
        try {
            FileUtils.upload(file.getBytes(),filePath,fileNewName);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("下载失败");
            result.setCode(ResultCode.UNSPECIFIED);
            return result;
        }
        result.setCode(ResultCode.SUCCESS);
        result.setMsg("上传成功");
        result.setData(url+"/img/blog/"+fileNewName);
        return result;
    }

    /**
     * 将博客的图片文件从服务器删除
     * @param photoName 图片访问地址
     * @return  result
     */
    @GetMapping("/deletePicture")
    public  Result deletePicture(@RequestParam("photoName")  String photoName){
        Result result =new Result();
        String photo=photoName.substring(photoName.lastIndexOf("/")+1);
        String filePath=path+"blog/"+photo;
        if(FileUtils.deleteFile(filePath)) {
            result.setCode(ResultCode.SUCCESS);
            result.setMsg("删除成功");
            return result;
        }else {
            result.setCode(ResultCode.DELETE_ERROR);
            result.setMsg("删除失败");
            return result;
        }
    }
}
