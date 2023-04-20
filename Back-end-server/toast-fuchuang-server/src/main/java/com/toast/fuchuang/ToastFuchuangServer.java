package com.toast.fuchuang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.toast"})
public class ToastFuchuangServer {
    public static void main(String[] args)
    {
        SpringApplication.run(ToastFuchuangServer.class,args);
    }
}
