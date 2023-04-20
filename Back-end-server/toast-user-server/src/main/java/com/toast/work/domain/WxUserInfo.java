package com.toast.work.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxUserInfo implements Serializable {
    private String tempUserId;
    private WxMaUserInfo wxMaUserInfo;

    @Data
    public static class WxMaUserInfo implements Serializable{
        private String openId;
        private String nickName;
        private String gender;
        private String avatarUrl;
    }
}