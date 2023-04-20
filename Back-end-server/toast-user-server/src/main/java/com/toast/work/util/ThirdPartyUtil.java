//package com.toast.work.util;
//
//import cn.hutool.core.date.DateUtil;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//import sun.security.util.SignatureUtil;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 第三方登录工具类，支持微信、支付宝等第三方登录
// */
//@Component
//public class ThirdPartyUtil {
//
//    private static final String WECHAT_APPID = "your_wechat_appid";
//    private static final String WECHAT_SECRET = "your_wechat_secret";
//    private static final String WECHAT_AUTH_URL = "https://api.weixin.qq.com/sns/jscode2session";
//    private static final String WECHAT_GRANT_TYPE = "authorization_code";
//
//    private static final String ALIPAY_APPID = "your_alipay_appid";
//    private static final String ALIPAY_SECRET = "your_alipay_secret";
//    private static final String ALIPAY_AUTH_URL = "https://openapi.alipay.com/gateway.do";
//    private static final String ALIPAY_GRANT_TYPE = "authorization_code";
//    private static final String ALIPAY_METHOD = "alipay.system.oauth.token";
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    /**
//     * 微信登录授权
//     * @param code 微信登录时获取的code
//     * @return 返回包含微信用户信息的JSON对象
//     */
//    public JSONObject wechatLogin(String code) {
//        // 封装请求参数
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("appid", WECHAT_APPID);
//        paramMap.put("secret", WECHAT_SECRET);
//        paramMap.put("js_code", code);
//        paramMap.put("grant_type", WECHAT_GRANT_TYPE);
//        // 发送请求并解析响应
//        String response = restTemplate.getForObject(WECHAT_AUTH_URL + "?appid={appid}&secret={secret}&js_code={js_code}&grant_type={grant_type}", String.class, paramMap);
//        JSONObject json = JSONObject.parseObject(response);
//        if (json.containsKey("errcode")) {
//            throw new RuntimeException("WeChat login error: " + json.getString("errmsg"));
//        }
//        return json;
//    }
//
//    /**
//     * 支付宝登录授权
//     * @param authCode 支付宝登录授权码
//     * @return 返回包含支付宝用户信息的JSON对象
//     */
//    public JSONObject alipayLogin(String authCode) {
//        // 封装请求参数
//        Map<String, Object> paramMap = new HashMap<>();
//        paramMap.put("app_id", ALIPAY_APPID);
//        paramMap.put("method", ALIPAY_METHOD);
//        paramMap.put("charset", "utf-8");
//        paramMap.put("sign_type", "RSA2");
//        paramMap.put("timestamp", DateUtil.now());
//        paramMap.put("version", "1.0");
//        paramMap.put("grant_type", ALIPAY_GRANT_TYPE);
//        paramMap.put("code", authCode);
//        // 构建待签名字符串
//        String signContent = SignatureUtil.buildSignContent(paramMap);
//        // 生成签名
//        String sign = SignatureUtil.rsaSign(signContent, ALIPAY_SECRET, "utf-8", SignatureUtil.SIGN_TYPE_RSA2);
//        paramMap.put("sign", sign);
//        // 发送请求并解析响应
//        JSONObject response = restTemplate.postForObject(ALIPAY_AUTH_URL, paramMap, JSONObject.class);
//        JSONObject json = response.getJSONObject("alipay_system_oauth_token_response
//    }