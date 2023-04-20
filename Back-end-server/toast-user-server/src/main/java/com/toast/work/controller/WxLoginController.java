package com.toast.work.controller;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.toast.common.core.domain.HttpStatusCode;
import com.toast.common.core.domain.MessageConstant;
import com.toast.common.core.domain.Result;
import com.toast.common.redis.service.RedisService;
import com.toast.work.domain.ResponseKit;

import com.toast.work.domain.Vo.UserVo;
import com.toast.work.domain.WxUserInfo;
import com.toast.work.service.IUserService;
import com.toast.work.service.IWxLoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/wxUser")
public class WxLoginController {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${wxLogin.yiDengSecret}")
    private String yiDengSecret;
    @Autowired
    RedisService redisService;
    @Autowired
    private IWxLoginService wxLoginService;


    /**
     * 对接易登 网站接入微信登录
     * @return
     */
    @ApiOperation("获取微信登录二维码信息")
    @GetMapping("/wxQr")
    public Result getWxQr() {
        JSONObject data = null;
        try{
            // 请求易登获取二维码接口  "data": {"qrUrl": "","tempUserId": ""}
            String s = HttpUtil.get("https://yd.jylt.cc/api/wxLogin/tempUserId?secret=" + yiDengSecret);
            JSONObject jsonObject = JSONUtil.parseObj(s);
            if (jsonObject.getInt("code") != 0) {
                return new Result(HttpStatusCode.ERROR, MessageConstant.GET_QR_FAIL);
            }
             data = jsonObject.getJSONObject("data");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, MessageConstant.GET_QR_FAIL);
        }
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.GET_QR_SUCCESS,data);
    }

    /**
     * 对接易登 网站接入微信登录 请求响应要固定写法
     * @param body
     * @return
     */
    @ApiOperation("wx登录授权回调接口")
    @PostMapping("/wxCallBack")
    public ResponseKit<Object> wxCallBack(@RequestBody String body) {

        WxUserInfo wxUserInfo = JSONUtil.toBean(body, WxUserInfo.class);
        System.out.println(wxUserInfo);
        // 实现自己系统的登录和注册
        Boolean flag = wxLoginService.wxLogin(wxUserInfo);
        if (flag!=true){
            throw new RuntimeException("微信登录失败！");
        }
        return ResponseKit.success(0, "微信授权登录成功");
    }

    /**
     *  易登 网站接入微信登录
     * @param tempUserId
     * @return
     */
    @ApiOperation("判断用户是否微信登录成功 204说明暂未登录成功 200成功 500错误")
    @GetMapping("/isLogin")
    public Result wxIsLogin(String tempUserId) {
        if (tempUserId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, MessageConstant.NO_PARAMETER);
        }
        UserVo userVo = new UserVo();
        try{
            userVo = redisService.getCacheObject(tempUserId);
            if (userVo == null) {
                return new Result(HttpStatusCode.NO_CONTENT, "用户暂未登录");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, MessageConstant.USER_LOGIN_FAIL);
        }
        return new Result(HttpStatusCode.SUCCESS, MessageConstant.USER_LOGIN_SUCCESS,userVo);
    }
}
