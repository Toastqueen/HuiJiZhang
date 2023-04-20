package com.toast.fuchuang.service.impl;

import java.util.List;

import com.toast.fuchuang.domain.UserAccount;
import com.toast.fuchuang.mapper.UserAccountMapper;
import com.toast.fuchuang.service.IUserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 用户账户Service业务层处理
 * 
 * @author toast
 * @date 2023-03-21
 */
@Service
public class UserAccountServiceImpl implements IUserAccountService
{
    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 查询用户账户
     * 
     * @param userid 用户账户主键
     * @return 用户账户
     */
    @Override
    public UserAccount selectUserAccountByUserid(Long userid)
    {
        return userAccountMapper.selectUserAccountByUserid(userid);
    }

    /**
     * 查询用户账户列表
     * 
     * @param userAccount 用户账户
     * @return 用户账户
     */
    @Override
    public List<UserAccount> selectUserAccountList(UserAccount userAccount)
    {
        return userAccountMapper.selectUserAccountList(userAccount);
    }

    /**
     * 新增用户账户
     * 
     * @param userAccount 用户账户
     * @return 结果
     */
    @Override
    public int insertUserAccount(UserAccount userAccount)
    {
        return userAccountMapper.insertUserAccount(userAccount);
    }

    /**
     * 修改用户账户
     * 
     * @param userAccount 用户账户
     * @return 结果
     */
    @Override
    public int updateUserAccount(UserAccount userAccount)
    {
        return userAccountMapper.updateUserAccount(userAccount);
    }

    /**
     * 批量删除用户账户
     * 
     * @param userids 需要删除的用户账户主键
     * @return 结果
     */
    @Override
    public int deleteUserAccountByUserids(Long[] userids)
    {
        return userAccountMapper.deleteUserAccountByUserids(userids);
    }

    /**
     * 删除用户账户信息
     * 
     * @param accountid
     * @return 结果
     */
    @Override
    public int deleteUserAccountByAccountid(Long accountid)
    {
        return userAccountMapper.deleteUserAccountByAccountid(accountid);
    }
}
