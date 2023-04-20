package com.toast.fuchuang.mapper;

import com.toast.fuchuang.domain.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户账户Mapper接口
 * 
 * @author toast
 * @date 2023-03-21
 */
@Mapper
public interface UserAccountMapper 
{
    /**
     * 查询用户账户
     * 
     * @param userid 用户账户主键
     * @return 用户账户
     */
    public UserAccount selectUserAccountByUserid(Long userid);

    /**
     * 查询用户账户列表
     * 
     * @param userAccount 用户账户
     * @return 用户账户集合
     */
    public List<UserAccount> selectUserAccountList(UserAccount userAccount);

    /**
     * 新增用户账户
     * 
     * @param userAccount 用户账户
     * @return 结果
     */
    public int insertUserAccount(UserAccount userAccount);

    /**
     * 修改用户账户
     * 
     * @param userAccount 用户账户
     * @return 结果
     */
    public int updateUserAccount(UserAccount userAccount);

    /**
     * 删除用户账户
     * 
     * @param accountid 用户账户主键
     * @return 结果
     */
    public int deleteUserAccountByAccountid(@Param("accountid") Long accountid);

    /**
     * 批量删除用户账户
     * 
     * @param userids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserAccountByUserids(Long[] userids);
}
