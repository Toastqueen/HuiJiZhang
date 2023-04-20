package com.toast.common.utils.service;


import com.toast.common.redis.service.RedisService;
import com.toast.common.utils.config.SendMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class PhoneVerificationService {
    @Autowired
    RedisService redisService;

    @Autowired
    SendMessageUtil sendMessageUtil;

    /** 向该电话发送验证码
     * @param phoneNumber 要发送短信的电话
     * @param redisKey 用户业务标识
     * @return 发送成功返回true 否则false
     */
    public boolean sendPhoneVerificationCode(String phoneNumber,String redisKey){
        System.out.println("向手机"+phoneNumber+"发送验证码");
        Boolean result = false;
        try {
            //自定义范围生成随机6位验证码
            String verifyCode = String.valueOf((int) (Math.random() * 900000 + 100000));
            System.out.println("redisKey:"+redisKey);
            System.out.println("验证码"+verifyCode);
            //接口需要一个map
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("code", verifyCode);
            //用业务唯一标识做key 用于之后验证验证码时取出
            redisService.setCacheObject(redisKey, verifyCode, 10L, TimeUnit.MINUTES);
            //调阿里云接口发短信
            Boolean messageStatus = sendMessageUtil.testSms("SMS_177243201", phoneNumber, params);
            if (messageStatus == false) {
                System.out.println("发送失败");
                return false;
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

    /**
     * 验证输入验证码和发送的是否一致
     * @param code 用户输入的验证码
     * @param redisKey 用户业务标识
     * @return 成功true 否则false
     */
    public boolean verifyPhoneVerificationCode(String code,String redisKey) {
        try {
            String sendCode = redisService.getCacheObject(redisKey);
            System.out.println(sendCode);
            System.out.println(redisKey);
            //简化if语句 判断相等
            return Objects.equals(code, sendCode);
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
