package com.toast.work.service.Impl;

import java.util.*;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.toast.common.utils.service.PhoneVerificationService;
import com.toast.common.utils.tools.JwtUtil;
import com.toast.common.utils.tools.SecurityUtil;

import com.toast.work.domain.Vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.toast.work.mapper.UserMapper;
import com.toast.work.domain.User;
import com.toast.work.service.IUserService;
import com.toast.common.core.domain.ServiceException;
/**
 * 服创用户信息Service业务层处理
 * 
 * @author toast
 * @date 2023-01-01
 */
@Slf4j
@Service
public class UserServiceImpl implements IUserService
{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PhoneVerificationService phoneVerificationService;

    /**
     * 查询服创用户信息
     * 
     * @param userId 服创用户信息主键
     * @return 服创用户信息
     */
    @Override
    public User selectUserByUserId(Long userId)
    {
        return userMapper.selectUserByUserId(userId);
    }

    /**
     * 查询服创用户信息列表
     * 
     * @param user 服创用户信息
     * @return 服创用户信息
     */
    @Override
    public List<User> selectUserList(User user)
    {
        return userMapper.selectUserList(user);
    }

    /**
     * 用户注册
     * 
     * @param user 服创用户信息
     * @return jwt和用户信息
     */
    @Override
    public UserVo registerUser(User user) {

        user.setUserName(user.getPhoneNumber());
        //雪花id
        Long userId = IdUtil.getSnowflakeNextId()/ 1000000L;
        user.setUserId(userId);
        //加密存储
        user.setPassword(SecurityUtil.encryptPassword(user.getPassword()));
        //生成令牌
        Map<String,Object> info = new HashMap<String,Object>();
        info.put("userId",userId);
        info.put("userName",user.getUserName());
        String token = JwtUtil.createJWT(String.valueOf(userId), JSON.toJSONString(info),null);
        userMapper.insertUser(user);
        User userDto = userMapper.selectUserByUserId(userId);
        return new UserVo(userDto,token);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    @Override
    public UserVo loginUser(User user){

        User userDto = userMapper.selectUserByUserName(user.getUserName());
        System.out.println(userDto);
       if (!SecurityUtil.matchesPassword(user.getPassword(),userDto.getPassword()))
            throw new ServiceException("密码错误");
        //生成令牌
        Map<String,Object> info = new HashMap<String,Object>();
        Long userId = userDto.getUserId();
        info.put("userId",userId);
        info.put("userName",user.getUserName());
        Long timeout = 10800000L; //3*60 * 60 *1000 三小时
        String token = JwtUtil.createJWT(String.valueOf(userId), JSON.toJSONString(info),timeout);
        UserVo userVo = new UserVo(userDto,token);
        userVo.setRegisterDays((long) userMapper.getRegisterDays(userId));
        System.out.println(String.valueOf(userVo));
        return userVo;
    }

    @Override
    public String sendPhoneVerificationCode(User user){
        //若传入的对象没有电话 说明是登录后状态 已有userId作为key
        if(user.getPhoneNumber()==null){
            System.out.println(user);
            User userFromDB = userMapper.selectUserByUserId(user.getUserId());
            if (userFromDB == null)
                throw new RuntimeException("该用户不存在对应手机号，无法发送验证码");
            if(!phoneVerificationService.sendPhoneVerificationCode(user.getPhoneNumber(), String.valueOf(user.getPhoneNumber()))){
                throw new RuntimeException("发送失败");
            }
            return String.valueOf(user.getUserId());
        }
        //若传入的对象有电话 说明是注册状态 新建key
        long redisKey = IdUtil.getSnowflakeNextId() / 1000000L;
        if(!phoneVerificationService.sendPhoneVerificationCode(user.getPhoneNumber(), String.valueOf(redisKey))){
            throw new RuntimeException("发送失败");
        }
        return String.valueOf(redisKey);
    }

    @Override
    public boolean verifyPhoneVerificationCode(String code, String redisKey){
        return phoneVerificationService.verifyPhoneVerificationCode(code,redisKey);
    }

    /**
     * 修改服创用户信息
     * 
     * @param user 服创用户信息
     * @return 结果
     */
    @Override
    public int updateUser(User user)
    {

        if (user.getPassword() != null){
            user.setPassword(SecurityUtil.encryptPassword(user.getPassword()));
        }
        return userMapper.updateUser(user);
    }

    /**
     * 批量删除服创用户信息
     * 
     * @param userIds 需要删除的服创用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserIds(Long[] userIds)
    {
        return userMapper.deleteUserByUserIds(userIds);
    }

    /**
     * 删除服创用户信息信息
     * 
     * @param userId 服创用户信息主键
     * @return 结果
     */
    @Override
    public int deleteUserByUserId(Long userId)
    {
        return userMapper.deleteUserByUserId(userId);
    }


    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 唯一true 否则false
     */
    @Override
    public Boolean checkPhoneUnique(User user)
    {
        Long userId = user.getUserId()==null ? -1L : user.getUserId();
        User info = userMapper.checkPhoneUnique(user.getPhoneNumber());
        //根据手机查出的用户id和传入的不一样 说明手机号已存在用户
        if (info != null && info.getUserId().longValue() != userId.longValue())
        {
            return false;
        }
        return true;
    }

    /**
     * 校验用户是否存在
     *
     * @param user 用户信息
     * @return 存在true 否则false
     */
    @Override
    public Boolean isExistUser(User user)
    {
        Long userId = user.getUserId()==null ? -1L : user.getUserId();
        User info = userMapper.checkExistUser(user.getUserName());
        //存在
        if (info != null && info.getUserId().longValue() != userId.longValue())
        {
            return true;
        }
        //不存在
        return false;
    }

    /**
     * 用户注册时间天数统计
     */
    @Override
    public long getUserRegisterDays(long userId) {
        long registerDays= userMapper.getRegisterDays(userId);
        System.out.println(registerDays);
        return registerDays;
    }
}
