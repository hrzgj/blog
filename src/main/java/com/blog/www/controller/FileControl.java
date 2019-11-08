package com.blog.www.controller;

import com.blog.www.model.Result;
import com.blog.www.model.User;
import com.blog.www.service.FileService;
import com.blog.www.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
public class FileControl {


    @Autowired
    private FileService fileService;

    @Value("${img.location}")
    private String path;



    @PostMapping("/fileLoad")
    public Result upload(@RequestParam("photo") MultipartFile file, HttpServletRequest request){
        Result result=new Result();
//        String contentType=file.getContentType();
        String fileName=file.getOriginalFilename();
        String fileNewName=FileUtils.getFileNewName(fileName);
        String filePath = request.getSession().getServletContext().getRealPath(path);
        try {
            FileUtils.upload(file.getBytes(), filePath, fileNewName);
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg("下载失败");
            result.setCode(404);
            return result;
        }
//        // 拼接图片url
//        String imgHost = "http://localost:8080";
//        String imgUploadPath = path;
//        String imgName = fileName;
//        String imgPath = imgHost + imgUploadPath + imgName;
        User user= (User) request.getSession().getAttribute("user");
        fileService.upload(fileNewName,user.getId());
        result.setMsg("下载成功，数据库更新成功");
        result.setCode(200);
        return result;

    }
}
