package com.toast.common.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Result<T> {
    private int code;   //状态码
    private String message; //返回消息
    private Object data;  //数据对象


    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }

    public Result(int code, String message, Object data, int total) {
        this.code = code;
        this.message = message;
        this.data = data;

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}