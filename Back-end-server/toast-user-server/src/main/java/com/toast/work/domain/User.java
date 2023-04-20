package com.toast.work.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId; //用户ID
    private String userName; //昵称
    private String nickName; //昵称
    private String gender; //性别
    private String phoneNumber; //手机号
    private String avatarUrl; //用户头像地址
    private Boolean visible; //逻辑删除标志
    private String password; //密码
    private int age;//年龄
    private Date createTime;//创建时间

}