package com.toast.fuchuang.util;
import com.alibaba.fastjson2.JSONObject;


import com.toast.common.utils.tools.HttpUtil;
import com.toast.fuchuang.domain.Vo.MoneyRecordVo;
import com.toast.fuchuang.mapper.MoneyTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Async;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

@Component
public class PhotoProcessorServer {
    @Autowired
    MoneyTypeMapper moneyTypeMapper;
    @Value("${toast.pythonServerUrl}")
    private String pythonServerUrl;

    private final Executor executor;

    public PhotoProcessorServer(Executor executor) {
        this.executor = executor;
    }

    /**
     * 对单张图片进行识别并转化为MoneyRecordVo对象
     * @param photoUrl
     * @param userId
     * @return
     */
    @Async
    public CompletableFuture<MoneyRecordVo> processPhotoUnit(String photoUrl, Long userId) {
        // 执行异步任务，并返回一个CompletableFuture对象
        return CompletableFuture.supplyAsync(() -> {
            // 这里写具体的异步任务代码
            System.out.println(Thread.currentThread().getName()+"-----start-------asyncTaskFuture---");
            //图片服务地址
//            String url = "http://127.0.0.1:8003/getPhotoJson";
            String url = pythonServerUrl;
            String res;
            //构建请求体
            Map<String, String> param = new HashMap<>();
            param.put("url", photoUrl);
            MoneyRecordVo moneyRecordVo = new MoneyRecordVo();
            try {
                res = HttpUtil.HttpPost(url,param);
                JSONObject jsonObject = JSONObject.parseObject(res);
                System.out.println("code="+jsonObject.get("code"));
                if (!jsonObject.get("code").equals(200)){
                    throw new RuntimeException("图形识别失败！");
                }
                System.out.println(jsonObject.getJSONObject("info"));
                //构建vo对象返回前端
                moneyRecordVo.setUserId(userId);
                moneyRecordVo.setMoney(new BigDecimal((String) jsonObject.getJSONObject("info").get("money")));
                moneyRecordVo.setSeller((String) jsonObject.getJSONObject("info").get("shop"));
                moneyRecordVo.setRemark((String) jsonObject.getJSONObject("info").get("detailed"));
                moneyRecordVo.setInfo((String) jsonObject.getJSONObject("info").get("all"));
                String[] fileUrl = new String[]{photoUrl};
                moneyRecordVo.setFileUrls(fileUrl);
                if (jsonObject.getJSONObject("info").get("subclass")!=null){
                    System.out.println(jsonObject.getJSONObject("info").get("subclass"));
                    moneyRecordVo.setTypeName(String.valueOf(jsonObject.getJSONObject("info").get("subclass")));
                    Long typeId = moneyTypeMapper.selectMoneyTypeIdByName((String) jsonObject.getJSONObject("info").get("subclass"));
                    moneyRecordVo.setTypeId(typeId);
                }
                System.out.println(moneyRecordVo);

            } catch (Exception e) {
                System.out.println(e);
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName()+"-----end-------asyncTaskFuture---");
            return moneyRecordVo;
        }, executor);
    }

    /**
     * 并行处理多张图片服务
     * @param urls
     * @param userId
     * @return CompletableFuture包装的moneyRecordVo列表
     */
    public CompletableFuture<Void> asyncPhotoProcessor(String[] urls,Long userId) {
        // 定义一个CompletionStage数组，用于保存每个异步任务的结果
        CompletableFuture<MoneyRecordVo>[] futures = new CompletableFuture[urls.length];

        // 提交所有异步任务，将每个异步任务的返回结果保存到futures数组中
        for (int i = 0; i < urls.length; i++) {
            futures[i] = processPhotoUnit(urls[i],userId);
        }

        // 合并所有异步任务的结果，并在所有异步任务完成后执行下一步操作
        return CompletableFuture.allOf(futures)
                .thenAcceptAsync((Void) -> {
                    // 这里写所有异步任务完成后要执行的操作
                    System.out.println("最终结果：=======");
                    System.out.println(futures.toString());
                }, executor);
    }

    /**
     * 将并行任务获取的结果转为VO列表
     * @param urls
     * @param userId
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<MoneyRecordVo> getVolist(List<String> urls,Long userId) throws ExecutionException, InterruptedException {
        // 创建一个空的 CompletableFuture 列表
        List<CompletableFuture<MoneyRecordVo>> futures = new ArrayList<>();

        // 为每个 url 创建一个 CompletableFuture
        for (String url : urls) {
            CompletableFuture<MoneyRecordVo> future = processPhotoUnit(url,userId);
            futures.add(future);
        }

        // 等待所有 CompletableFuture 完成
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .join();

        // 将 CompletableFuture 转换为 MoneyRecordVo 列表
        return futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

}

