package com.blog.www.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author: chenyu
 * @date: 2019/11/1 11:05
 */
public class FileUtils {

    /**
     * 获得文件后缀
     * @param fileName 文件全名
     * @return 文件后缀
     */
    private static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOldName 文件原名
     * @return  文件新名
     */
    public static String getFileNewName(String fileOldName){
        return UUIDUtils.getUUID()+getSuffix(fileOldName);
    }

//    /**
//     *  文件上传
//     * @param file 文件
//     * @param path 文件存放路径
//     * @param fileName 源文件名
//     * @return  下载是否成功
//     */
//    public static boolean upload(MultipartFile file, String path, String fileName){
//
//
//
////        //使用原文件名
////        String realPath = path + "/" + fileName;
//
//        File dest = new File(realPath);
//
//        //判断文件父目录是否存在
//        if(!dest.getParentFile().exists()){
//            dest.getParentFile().mkdir();
//        }
//
//        try {
//            //保存文件
//            file.transferTo(dest);
//            return true;
//        } catch (IllegalStateException | IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//            return false;
//        }
//
//    }


    /**
     * 文件上传
     * @param file 文件
     * @param filePath 文件存放路径
     * @param fileName 源文件名
     */
    public static void upload(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
