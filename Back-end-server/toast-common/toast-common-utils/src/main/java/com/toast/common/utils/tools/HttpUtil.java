package com.toast.common.utils.tools;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.springframework.http.HttpHeaders.USER_AGENT;

public class HttpUtil {
    /**
     * 发送 HTTP POST请求的方法（可更改）
     *
     * @param Url    请求的 URL
     * @param params 携带的请求体 每个键值对都会封装成 JSON
     * @return 返回的 JSON结果
     */
    public static String HttpPost(String Url, Map<String, String> params) {
        String resp = null;
        JSONObject obj = new JSONObject();
        obj.putAll(params);
        String query = obj.toString();
        try {
            URL url = new URL(Url); // URL地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            try (OutputStream os = connection.getOutputStream()) {
                os.write(query.getBytes(StandardCharsets.UTF_8));
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String lines;
                StringBuffer sbf = new StringBuffer();
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes(), StandardCharsets.UTF_8);
                    sbf.append(lines);
                }
                resp = sbf.toString(); //返回的数据
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JSONObject json = (JSONObject) JSON.parse(resp);
        }
        return resp;
    }
    /**
     * 发送HTTP GET请求的方法
     *
     * @param Url 请求的 URL
     * @return 返回的 JSON结果
     * @throws Exception 异常
     */
    public static String HttpGet(String Url) throws Exception {
        URL url = new URL(Url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        //添加请求头
        conn.setRequestProperty("User-Agent", USER_AGENT);
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
