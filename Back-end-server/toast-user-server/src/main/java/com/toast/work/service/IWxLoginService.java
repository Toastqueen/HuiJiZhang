package com.toast.work.service;

import com.toast.work.domain.Vo.UserVo;
import com.toast.work.domain.WxUserInfo;

public interface IWxLoginService {
    Boolean wxLogin(WxUserInfo wxUserInfo);
}
