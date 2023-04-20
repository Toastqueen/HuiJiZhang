package com.toast.common.utils.config;

import com.alibaba.fastjson2.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Component;

import java.util.Map;
//短信接口提供的api
@Component
public class SendMessageUtil {
    public Boolean testSms(String code, String phoneNumber, Map<String,Object> params){
        // 指定地域名称 短信API的就是 cn-hangzhou 不能改变  后边填写您的  accessKey 和 accessKey Secret
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou",
                "***", "***");
        IAcsClient client = new DefaultAcsClient(profile);

        // 创建通用的请求对象
        CommonRequest request = new CommonRequest();
        // 指定请求方式
        request.setMethod(MethodType.POST);
        // 短信api的请求地址  固定
        request.setDomain("dysmsapi.aliyuncs.com");
        //签名算法版本  固定
        request.setVersion("2017-05-25");
        //请求 API 的名称
        request.setAction("SendSms");
        //指定地域名称
        request.putQueryParameter("RegionId", "cn-hangzhou");
        // 要给哪个手机号发送短信  指定手机
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        // 您的申请签名 需要申请 目前把惠工云改了就不会发送验证码了
        request.putQueryParameter("SignName", "***");
        // 您申请的模板 code
        request.putQueryParameter("TemplateCode", code);

        // 放入参数  需要把 map转换为json格式  使用fastJson进行转换
        request.putQueryParameter("TemplateParam", JSON.toJSONString(params));

        try {
            CommonResponse response = client.getCommonResponse(request);
            //log.info(JSON.parseObject(response.getData(), Map.class).get("Message").toString());
            return response.getHttpResponse().isSuccess();

        } catch (ClientException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

