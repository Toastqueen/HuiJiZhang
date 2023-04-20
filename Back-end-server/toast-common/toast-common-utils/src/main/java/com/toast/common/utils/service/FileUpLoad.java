package com.toast.common.utils.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileUpLoad {


    public List<String> upload(MultipartFile[] files, String fileStoragePath){
        File f = new File(fileStoragePath);
        List urlList = new ArrayList();
        if(!f.exists()){
            //多级目录
            f.mkdirs();
        }
        if(files.length > 0){
            for (MultipartFile file : files) {
                if(!file.isEmpty()){
                    try {
                        //获取上传的文件的文件名
                        String fileName = file.getOriginalFilename();
                        //处理文件重名问题 此时文件名是散列的字母数字
                        //获取文件后缀
                        String hzName = fileName.substring(fileName.lastIndexOf("."));
                        //使用UUID生成唯一标识文件名
                        fileName = UUID.randomUUID().toString() + hzName;

                        // File.separator代表文件分隔符 即finalPath= 路径/文件名
                        String finalPath = fileStoragePath + File.separator + fileName;
                        file.transferTo(new File(finalPath));
                        urlList.add(fileName);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return urlList;
    }
}
