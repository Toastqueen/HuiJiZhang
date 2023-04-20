package com.toast.work;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.toast"})
public class ToastUserServer {
    public static void main(String[] args)
    {
        SpringApplication.run(ToastUserServer.class,args);
    }
}
