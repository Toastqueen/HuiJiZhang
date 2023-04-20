package com.toast.work.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一系统响应结果格式
 *
 * @author wuchenxi
 * @date 2020-08-08 10:07:03
 */
@Data
@ApiModel
public class ResponseKit<T> implements Serializable {

    @ApiModelProperty(name = "code", value = "响应码")
    private int code;

    @ApiModelProperty(name = "msg", value = "响应字符串信息")
    private String msg;

    @ApiModelProperty(name = "data", value = "响应数据")
    private T data;

    private ResponseKit(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResponseKit(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public static <T> ResponseKit<T> success(int code, String msg) {
        return new ResponseKit<T>(code, msg);
    }

    public static <T> ResponseKit<T> success() {
        return new ResponseKit<T>(200, "操作成功！");
    }

    public static <T> ResponseKit<T> error(int code, String msg) {
        return new ResponseKit<T>(code, msg);
    }

    public static <T> ResponseKit<T> success(T data) {
        return new ResponseKit<T>(200, data);
    }

    private ResponseKit(){}
}
