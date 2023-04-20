package com.toast.fuchuang.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户账户对象 user_account
 * 
 * @author toast
 * @date 2023-03-21
 */
public class UserAccount
{
    private static final long serialVersionUID = 1L;

    /** 用户id */

    private Long userid;

    /** 账户id */

    private Long accountid;

    /** 账户名字 */

    private String accountname;

    /** 账户信息 */

    private String info;

    public void setUserid(Long userid) 
    {
        this.userid = userid;
    }

    public Long getUserid() 
    {
        return userid;
    }
    public void setAccountid(Long accountid) 
    {
        this.accountid = accountid;
    }

    public Long getAccountid() 
    {
        return accountid;
    }
    public void setAccountname(String accountname) 
    {
        this.accountname = accountname;
    }

    public String getAccountname() 
    {
        return accountname;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userid", getUserid())
            .append("accountid", getAccountid())
            .append("accountname", getAccountname())
            .append("info", getInfo())
            .toString();
    }
}
