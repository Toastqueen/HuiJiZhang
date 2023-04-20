package com.toast.fuchuang.util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.toast.common.utils.tools.HttpUtil;
import com.toast.fuchuang.domain.AiDTO;
import com.toast.fuchuang.domain.Vo.MoneyRecordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
@Component
public class OpenAIUtil {
    @Value("${toast.api.url}")
    private String apiUrl;
    @Value("${toast.api.key}")
    private String apiKey;
    @Value("${toast.api.model}")
    private String model;
    @Value("${toast.pythonAiServerUrl}")
    private String pythonAiServerUrl;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * 向openAI发起对话
     * @param messageContent 提问内容
     * @param id 上下文token序列 没有就不填
     * @return {
     *     "content":"回答内容"
     *      "context":"本次回答的上下文token序列"
     * }
     */
    public Map curlOpenAI(String messageContent, String id) {
        System.out.println(apiKey);
        System.out.println(apiUrl);
        // 设置请求头部
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        // 设置请求体
        Map<String, Object> requestBody = new HashMap<>();
        //模型
        requestBody.put("model", model);
        //上下文
//        requestBody.put("prompt_tokens", id);
        //设置温度
        requestBody.put("temperature", 0.5);
        //生成文本的最大长度
        requestBody.put("max_tokens", 500);
        // 一次请求生成的文本个数。
        requestBody.put("n", 1);
        requestBody.put("stream", false);

        requestBody.put("user", "toast");
        //这个参数是一个数组，其中包含要发送的消息内容。每个元素都是一个包含 role 和 content 属性的对象，表示发送方和消息内容。
        requestBody.put("messages", Arrays.asList(
                new HashMap<String, Object>(){
                    {put("role", "user"); put("content", messageContent);}
                }
        ));
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        // 发送 POST 请求
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, request, String.class);
        String jsonResponse = response.getBody();
        System.out.println(jsonResponse);
        JSONObject jsonObject = JSONObject.parseObject(jsonResponse);
        String thisId = (String) jsonObject.get("id");
        // 获取choices数组中的第一个元素，再获取message字段的值
        String content = JSONPath.eval(jsonObject, "$.choices[0].message.content").toString();
        System.out.println(content);
        Map map = new HashMap<>();
        map.put("content",content);
        map.put("id",thisId);
        return map;
    }

    /**
     * 调用python端的消费行为分析服务
     * @param aiDTO
     * @return null则说明调用失败
     */
    public String curlPyServer(AiDTO aiDTO){
        String url = pythonAiServerUrl;
        String res;
        String content = null;
        //构建请求体
        Map<String, String> param = new HashMap<>();
        param.put("AiDto", String.valueOf(aiDTO));
        try {
            res = HttpUtil.HttpPost(url, param);
            JSONObject jsonObject = JSONObject.parseObject(res);
            if (!jsonObject.get("code").equals(200)) {
                throw new RuntimeException("调用接口失败！");
            }
            JSONObject message = jsonObject.getJSONObject("info").getJSONArray("choices").getJSONObject(0).getJSONObject("message");
            content = message.getString("content");
            System.out.println(content);
            return content;
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

}


