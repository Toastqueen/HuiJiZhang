package com.toast.work.domain.Vo;

import com.toast.work.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class UserVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long userId; //用户ID
    private String userName; //用户名
    private String nickName; //昵称
    private String gender; //性别
    private String phoneNumber; //手机号
    private String avatarUrl; //用户头像地址
    private String token; //jwt令牌
    private int age;//年龄
    private Long registerDays;//注册时间天数


    public UserVo(User user, String token) {
        this.userId = user.getUserId();
        this.avatarUrl = user.getAvatarUrl();
        this.phoneNumber = user.getPhoneNumber();
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.token = token;

    }

}
