package com.blog.www.utils;

import java.io.File;
import java.io.FileOutputStream;

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

    public static String deleteFile(String path){
        File file=new File(path);
        if(file.exists()){
            file.delete();
            return "删除成功";
        }
        else {
            return "删除失败";
        }
    }
}

