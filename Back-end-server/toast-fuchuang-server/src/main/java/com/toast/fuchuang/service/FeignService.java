package com.toast.fuchuang.service;


import com.toast.common.core.domain.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "toast-user-service", url = "http://localhost:8001")
public interface FeignService  //这里面对应的是服务提供者对应接口地址
{
    @GetMapping("/user/userInfo")
    Result userInfoByUserId(@RequestParam("userId") Long userId);
}