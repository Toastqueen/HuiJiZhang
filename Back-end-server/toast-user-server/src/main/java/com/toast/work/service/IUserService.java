package com.toast.work.service;

import java.util.List;
import com.toast.work.domain.User;
import com.toast.work.domain.Vo.UserVo;

/**
 * 服创用户信息Service接口
 * 
 * @author toast
 * @date 2023-01-01
 */
public interface IUserService 
{

    /**
     * 用户注册
     *
     * @param user 服创用户信息
     * @return 结果
     */
    public UserVo registerUser(User user);

    /**
     * 用户登录
     * @param user
     * @return
     */
    public UserVo loginUser(User user);

    /**
     * 发送手机验证码
     * @param user user有电话则用当前的 没有则以userId查询绑定电话发送
     * @return
     */
    String sendPhoneVerificationCode(User user);

    /**
     * 验证验证码
     * @param code
     * @return
     */
    public boolean verifyPhoneVerificationCode(String code,String redisKey);

    /**
     * 查询服创用户信息
     * 
     * @param userId 服创用户信息主键
     * @return 服创用户信息
     */
    public User selectUserByUserId(Long userId);

    /**
     * 查询服创用户信息列表
     * 
     * @param user 服创用户信息
     * @return 服创用户信息集合
     */
    public List<User> selectUserList(User user);



    /**
     * 修改服创用户信息
     * 
     * @param user 服创用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 批量删除服创用户信息
     * 
     * @param userIds 需要删除的服创用户信息主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(Long[] userIds);

    /**
     * 删除服创用户信息信息
     * 
     * @param userId 服创用户信息主键
     * @return 结果
     */
    public int deleteUserByUserId(Long userId);

    Boolean checkPhoneUnique(User user);

    /**
     * 校验用户是否存在
     *
     * @param user 用户信息
     * @return 存在true 否则false
     */
    Boolean isExistUser(User user);

    long getUserRegisterDays(long userId);
}
