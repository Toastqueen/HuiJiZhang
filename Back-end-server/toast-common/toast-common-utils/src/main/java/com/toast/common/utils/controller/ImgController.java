package com.toast.common.utils.controller;

import com.toast.common.core.domain.HttpStatusCode;
import com.toast.common.core.domain.Result;
import com.toast.common.utils.service.FileUpLoad;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
@Controller
/**
 * 只作为参考 不要在此模块运行conrtoller
 */
public class ImgController {
    @Resource
    private FileUpLoad fileUpLoad;
    //设置过的虚拟路径映射
    @Value("${file.uploadFolder}")
    private String uploadFolder;

    /**
     * 上传文件 @RequestPart("upFiles")要与<input>的name一致
     */
    @PostMapping("/ImgsServer")
    public Result ImgsServer(@RequestPart("upFiles") MultipartFile[] file, HttpServletRequest request) {
        System.out.println(request.getPathInfo());
//        MultipartFile[] list = {photo}; 若单个文件
        List<String> fileDownloadUrlList = new ArrayList<>();
        InetAddress inetAddress = null;
        try {
            //获得本机Ip（获取的是服务器的Ip）
            inetAddress = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = inetAddress.getHostAddress();
        List<String> list = fileUpLoad.upload(file, uploadFolder);
        try {
            if (!list.isEmpty()){
                for (String newFileName:list){
                    //返回保存的url，根据url可以进行文件查看或者下载
                    String fileDownloadUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort() + "/api/file/" + newFileName;
                    fileDownloadUrlList.add(fileDownloadUrl);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(HttpStatusCode.SUCCESS,"上传失败");
        }
        return new Result(HttpStatusCode.SUCCESS, "上传成功",fileDownloadUrlList);
    }

}
