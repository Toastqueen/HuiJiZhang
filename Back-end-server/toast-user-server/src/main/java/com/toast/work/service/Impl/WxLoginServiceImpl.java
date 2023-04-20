package com.toast.work.service.Impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.toast.common.redis.service.RedisService;
import com.toast.common.utils.tools.JwtUtil;
import com.toast.work.domain.User;
import com.toast.work.domain.Vo.UserVo;
import com.toast.work.domain.WxUserInfo;
import com.toast.work.mapper.UserMapper;
import com.toast.work.service.IWxLoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WxLoginServiceImpl implements IWxLoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    RedisService redisService;

    @Override
    public Boolean wxLogin(WxUserInfo wxUserInfo){
        WxUserInfo.WxMaUserInfo wxMaUserInfo = wxUserInfo.getWxMaUserInfo();
        try {
            Long userId = userMapper.getUserIdByOpenId(wxMaUserInfo.getOpenId());
            //首次登录 需要注册
                if (userId == null) {
                    User user = new User();
                    user.setAvatarUrl(wxMaUserInfo.getAvatarUrl());
                    if(wxMaUserInfo.getGender()!=null){
                        if(wxMaUserInfo.getGender().equals("1"))
                            user.setGender("男");
                        if(wxMaUserInfo.getGender().equals("2"))
                            user.setGender("女");
                        if(wxMaUserInfo.getGender().equals("0"))
                            user.setGender("未知");
                    }else {
                        user.setGender("未知");
                    }
                    user.setNickName(wxMaUserInfo.getNickName());
                    //雪花id
                    long newUserId = IdUtil.getSnowflakeNextId() / 1000000L;
                    user.setUserId(newUserId);
                    //还有电话 密码 年龄未录入
                    userMapper.insertUserOpenId(newUserId, wxMaUserInfo.getOpenId());
                    userMapper.insertUser(user);
                    //token
                    Map<String, Object> info = new HashMap<String, Object>();
                    info.put("userId", newUserId);
                    String token = JwtUtil.createJWT(String.valueOf(newUserId), JSON.toJSONString(info), null);
                    User userDto = userMapper.selectUserByUserId(newUserId);
                    UserVo userVo = new UserVo(userDto, token);
                    userVo.setRegisterDays((long) userMapper.getRegisterDays(newUserId));
                    redisService.setCacheObject(wxUserInfo.getTempUserId(), userVo, 10L, TimeUnit.MINUTES);
                    return true;
                }
            //已经有关联的userId
            User userDto = userMapper.selectUserByUserId(userId);
            //token
            Map<String, Object> info = new HashMap<String, Object>();
            info.put("userId", userId);
            String token = JwtUtil.createJWT(String.valueOf(userId), JSON.toJSONString(info), null);
            UserVo userVo = new UserVo(userDto, token);
            userVo.setRegisterDays((long) userMapper.getRegisterDays(userId));
            redisService.setCacheObject(wxUserInfo.getTempUserId(), userVo, 10L, TimeUnit.MINUTES);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }



}
