package com.toast.work.controller;

import cn.hutool.core.util.IdUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toast.common.core.domain.HttpStatusCode;
import com.toast.common.core.domain.MessageConstant;
import com.toast.common.core.domain.Result;
import com.toast.common.core.domain.ServiceException;
import com.toast.common.utils.service.FileUpLoad;
import com.toast.common.utils.service.PhoneVerificationService;
import com.toast.work.domain.User;
import com.toast.work.domain.VerifyCodeDTO;
import com.toast.work.domain.Vo.UserVo;
import com.toast.work.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    //设置过的虚拟路径映射
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Autowired
    private IUserService userService;
    @Resource
    private FileUpLoad fileUpLoad;
    @Autowired
    private PhoneVerificationService phoneVerificationService;

    /**
     * 用户注册
     * @param user 电话和密码必填 用户名默认电话
     * @return 用户信息和token
     */
    @ApiOperation(value = "用户注册（电话和密码必填 用户名默认电话） ")
    @PostMapping("/register")
    public Result registerUser(@RequestBody User user){
        UserVo userVo = new UserVo();
        if (user.getPassword()==null||user.getPhoneNumber()==null)
            return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_PARAMETER);
        if (!userService.checkPhoneUnique(user))
            return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.USER_PHONE_EXIST);
        try {
            userVo = userService.registerUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, MessageConstant.USER_REGISTER_FAIL);
        }
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.USER_REGISTER_SUCCESS,userVo);
    }

    /**
     * 用户登录
     * @param user 用户名和密码登录
     * @return 用户信息和token
     */
    @ApiOperation(value = "用户名和密码登录 ")
    @PostMapping("/login")
    public Result loginUser(@RequestBody User user){
        UserVo userVo = new UserVo();
        if (user.getPassword()==null||user.getUserName()==null)
            return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_PARAMETER);
        try {
            if (!userService.isExistUser(user)){
                return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_USER);
            }
            userVo = userService.loginUser(user);
        } catch (ServiceException e){
           return new Result(HttpStatusCode.FORBIDDEN, e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
           return new Result(HttpStatusCode.ERROR, MessageConstant.USER_LOGIN_FAIL);
        }
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.USER_LOGIN_SUCCESS,userVo);
    }

    @ApiOperation(value = "获得用户信息 ")
    @GetMapping("/userInfo")
    public Result userInfoByUserId(Long userId){
        User user = new User();
        try {
          user = userService.selectUserByUserId(userId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, MessageConstant.GET_USER_FAIL);
        }
        System.out.println(user);
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.GET_USER_SUCCESS,user);
    }

    /**
     * userId和phoneNumber同时为空才报错 因为支持在注册时没有id只有电话的情况
     * @param user
     * @return
     */
    @ApiOperation(value = "发送验证码")
    @PostMapping("/sendCode")
    public Result sendCode(@RequestBody User user) {
        if (user.getUserId()==null & user.getPhoneNumber()==null)
            return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_PARAMETER);
        try {
            String tempKey = userService.sendPhoneVerificationCode(user);
            return new Result(HttpStatusCode.SUCCESS, MessageConstant.SEND_VERIFY_MESSAGE_SUCCESS,tempKey);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, MessageConstant.SEND_VERIFY_MESSAGE_FAIL);
        }
    }

    @ApiOperation(value = "校验验证码是否正确")
    @PostMapping("/verifyCode")
    public Result verifyCode(@RequestBody VerifyCodeDTO verifyCodeDTO) {
        System.out.println(verifyCodeDTO);
        if (verifyCodeDTO.getCode()==null || verifyCodeDTO.getTempKey()==null)
            return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_PARAMETER);
        try {
            if (!userService.verifyPhoneVerificationCode(verifyCodeDTO.getCode(),verifyCodeDTO.getTempKey()))
                return new Result(HttpStatusCode.ERROR, MessageConstant.VERIFY_MESSAGE_FAIL);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "验证出错");
        }
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.VERIFY_MESSAGE_SUCCESS);
    }

    @ApiOperation(value = "设置密码")
    @PostMapping("/setPassword")
    public Result setPassword(@RequestBody User user) {
        if (user.getUserId()==null)
            return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_PARAMETER);
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, MessageConstant.RESET_PASSWORD_FAIL);
        }
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.RESET_PASSWORD_SUCCESS);
    }

    @ApiOperation(value = "更新用户信息")
    @PostMapping("/updateUser")
       public Result updateUser(@RequestBody User user) {
        if (user.getUserId()==null)
            return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_PARAMETER);
        try {
            if (user.getPhoneNumber()!=null){
                if (!userService.checkPhoneUnique(user)){
                    return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.USER_PHONE_EXIST);
                }
            }
            //没传用户名-数据库里没有过用户名-传了电话-则默认设置用户名为电话
            if(user.getUserName()==null){
                if (userService.selectUserByUserId(user.getUserId()).getUserName()==null&&user.getPhoneNumber()!=null){
                    user.setUserName(user.getPhoneNumber());
                }
            }
//            //存在该号码为userName的记录
//            if (userService.isExistUser(user)){
//                return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_USER);
//            }
            userService.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, MessageConstant.RESET_USER_FAIL);
        }
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.RESET_USER_SUCCESS);
    }


    @ApiOperation(value = "获取用户已注册时间")
    @GetMapping("/registerDays/{userId}")
    public Result getRegisterDays(@PathVariable Long userId) {
        long userRegisterDays = 0;
        try {
          userRegisterDays = userService.getUserRegisterDays(userId);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR,"获取用户已注册时间失败");
        }
        return new Result(HttpStatusCode.SUCCESS, "获取用户已注册时间成功",userRegisterDays);
    }



    @ApiOperation(value = "上传头像 <input type=\"file\" name=\"upFiles\" multiple=\"multiple\">")
    @PostMapping("/upLoadPicture")
    /**
     * 上传文件 @RequestPart("upFiles")要与<input>的name一致
     */
    public Result upLoadPicture(@RequestPart("upFiles") MultipartFile[] file, HttpServletRequest request,User user) {
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

        if (!list.isEmpty()){
            for (String newFileName:list){
                //返回保存的url，根据url可以进行文件查看或者下载
                String fileDownloadUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort() + "/api/file/" + newFileName;
                fileDownloadUrlList.add(fileDownloadUrl);
            }
        }
        try {
            user.setAvatarUrl(fileDownloadUrlList.get(0));
            userService.updateUser(user);
        }catch (Exception e){
            e.printStackTrace();
            return  new Result(HttpStatusCode.ERROR, MessageConstant.UPLOAD_FAIL);
        }
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.UPLOAD_SUCCESS,fileDownloadUrlList);
    }
    /**
     * 上传文件 @RequestPart("upFiles")要与<input>的name一致
     */
    @ApiOperation(value = "上传文件-图床服务 <input type=\"file\" name=\"upFiles\" multiple=\"multiple\">")
    @PostMapping("/ImgServer")
    public Result ImgServer(@RequestPart("upFiles") MultipartFile[] file, HttpServletRequest request) {
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
