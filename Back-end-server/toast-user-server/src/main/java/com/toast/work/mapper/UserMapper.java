package com.toast.work.mapper;

import java.util.Date;
import java.util.List;
import com.toast.work.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 服创用户信息Mapper接口
 * 
 * @author toast
 * @date 2023-01-01
 */
@Mapper
public interface UserMapper 
{

    /**
     * 查询服创用户信息
     * 
     * @param userId 服创用户信息主键
     * @return 服创用户信息
     */
    public User selectUserByUserId(Long userId);

    public User selectUserByUserName(String userName);

    /**
     * 查询服创用户信息列表
     * 
     * @param user 服创用户信息
     * @return 服创用户信息集合
     */
    public List<User> selectUserList(User user);

    /**
     * 新增服创用户信息
     * 
     * @param user 服创用户信息
     * @return 结果
     */
    public int insertUser(User user);

    /**
     * 修改服创用户信息
     * 
     * @param user 服创用户信息
     * @return 结果
     */
    public int updateUser(User user);

    /**
     * 删除服创用户信息
     * 
     * @param userId 服创用户信息主键
     * @return 结果
     */
    public int deleteUserByUserId(Long userId);

    /**
     * 批量删除服创用户信息
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserByUserIds(Long[] userIds);

    /**
     * 校验手机号码是否唯一
     *
     * @param phonenumber 手机号码
     * @return user_id, phone_number
     */
    public User checkPhoneUnique(String phonenumber);

    /**
     * 校验用户是否存在
     * @param userName
     * @return user_id, user_name
     */
    public User checkExistUser(String userName);

    /**
     * 获得注册天数
     * @param userId
     * @return
     */
    @Select("SELECT DATEDIFF(NOW(), create_time) AS register_days FROM fuchuang_user WHERE user_id = #{userId} and visible = 1")
    int getRegisterDays(@Param("userId") Long userId);

    /**
     * 根据userId查openId
     * @param userId
     * @return
     */
    public String getOpenIdByUserId(@Param("userId") Long userId);

    /**
     * 根据openId查userId
     * @param openId
     * @return
     */
    public Long getUserIdByOpenId(@Param("openId") String openId);

    public int insertUserOpenId(@Param("userId") Long userId,@Param("openId") String openId);

}
