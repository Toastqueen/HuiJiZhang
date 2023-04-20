package com.toast.fuchuang.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.IdUtil;
import com.toast.common.core.domain.HttpStatusCode;
import com.toast.common.core.domain.Result;
import com.toast.fuchuang.domain.UserAccount;
import com.toast.fuchuang.domain.Vo.MoneyRecordVo;
import com.toast.fuchuang.service.IUserAccountService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账户Controller
 * 
 * @author ruoyi
 * @date 2023-03-21
 */
@RestController
@RequestMapping("/account")
public class UserAccountController
{
    @Autowired
    private IUserAccountService userAccountService;

    /**
     * 查询用户账户列表
     */
    @GetMapping("/list")
    public Result list(UserAccount userAccount)
    {
        if(userAccount.getUserid()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        List<UserAccount> list = new ArrayList<>();
        try{
            list = userAccountService.selectUserAccountList(userAccount);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "查询失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"查询成功",list);

    }


    /**
     * 获取用户账户详细信息
     */

    @GetMapping(value = "/{accountid}")
    public Result getInfo(@PathVariable("accountid") Long accountid)
    {
        if(accountid==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        UserAccount userAccount = new UserAccount();
        try{
            userAccount = userAccountService.selectUserAccountByUserid(accountid);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "查询失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"查询成功",userAccount);
    }

    /**
     * 新增用户账户
     */

    @PostMapping
    public Result add(@RequestBody UserAccount userAccount)
    {
        if(userAccount.getUserid()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }try{
            Long accountId = IdUtil.getSnowflakeNextId() / 1000000L;
            UserAccount userAccount1 = new UserAccount();
            userAccount1.setAccountid(accountId);
            userAccount1.setAccountname(userAccount.getAccountname());
            userAccount1.setInfo(userAccount.getInfo());
            userAccount1.setUserid(userAccount.getUserid());
            userAccountService.insertUserAccount(userAccount1);
        }catch (Exception e){
        e.printStackTrace();
        return new Result(HttpStatusCode.ERROR, "添加失败");
    }
        return new Result(HttpStatusCode.SUCCESS,"添加成功");
    }

    /**
     * 修改用户账户
     */
    @PutMapping
    public Result edit(@RequestBody UserAccount userAccount)
    {
        if(userAccount.getUserid()==null||userAccount.getAccountid()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }try{
        userAccountService.updateUserAccount(userAccount);

        }catch (Exception e) {
        e.printStackTrace();
        return new Result(HttpStatusCode.ERROR, "更新失败");
    }
        return new Result(HttpStatusCode.SUCCESS,"更新成功");
    }

    /**
     * 删除用户账户
     */
    @PostMapping(value = "/delete")
    public Result delete(@RequestBody UserAccount userAccount)
    {
        if(userAccount.getAccountid()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }try{
        System.out.println(userAccount.getAccountid());
        userAccountService.deleteUserAccountByAccountid(userAccount.getAccountid());

    }catch (Exception e) {
        e.printStackTrace();
        return new Result(HttpStatusCode.ERROR, "删除失败");
    }
        return new Result(HttpStatusCode.SUCCESS,"删除成功");
    }
}
