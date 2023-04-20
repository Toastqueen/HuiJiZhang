package com.toast.fuchuang.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.toast.fuchuang.mapper.PayBudgetMapper;
import com.toast.fuchuang.domain.PayBudget;
import com.toast.fuchuang.service.IPayBudgetService;

/**
 * 预算Service业务层处理
 * 
 * @author toast
 * @date 2023-03-21
 */
@Service
public class PayBudgetServiceImpl implements IPayBudgetService 
{
    @Autowired
    private PayBudgetMapper payBudgetMapper;

    /**
     * 查询预算
     * 
     * @param id 预算主键
     * @return 预算
     */
    @Override
    public PayBudget selectPayBudgetById(Long id)
    {
        return payBudgetMapper.selectPayBudgetById(id);
    }

    /**
     * 查询预算列表按月
     * 
     * @param payBudget 预算
     * @return 预算
     */
    @Override
    public List<PayBudget> selectPayBudgetList(PayBudget payBudget)
    {
        return payBudgetMapper.selectPayBudgetList(payBudget.getUserId());
    }

    /**
     * 查询用户年和月的总预算
     * @param userId
     * @return
     */
    @Override
    public Map selectPayBudget(Long userId){
        Map<String, Object> map = payBudgetMapper.selectallPayBudget(userId);
        System.out.println(map);
        return map;
    }
    /**
     * 查询预算列表按年
     *
     * @param payBudget 预算
     * @return 预算
     */
    @Override
    public List<PayBudget> selectPayBudgetListOfYear(PayBudget payBudget)
    {
        System.out.println(payBudget.getUserId());
        return payBudgetMapper.selectPayBudgetListOFYear(payBudget.getUserId());
    }
    /**
     * 新增预算
     * 
     * @param payBudget 预算
     * @return 结果
     */
    @Override
    public int insertPayBudget(PayBudget payBudget)
    {
        return payBudgetMapper.insertPayBudget(payBudget);
    }

    @Override
    public int insertallPayBudget(Long userId, BigDecimal monthBudget, BigDecimal yearBudget)
    {
        return payBudgetMapper.insertallPayBudget(userId,monthBudget,yearBudget);
    }

    /**
     * 修改预算
     * 
     * @param payBudget 预算
     * @return 结果
     */
    @Override
    public int updatePayBudget(PayBudget payBudget)
    {
        return payBudgetMapper.updatePayBudget(payBudget);
    }

    @Override
    public int updateAllPayBudget(Long userId, BigDecimal monthBudget, BigDecimal yearBudget)
    {
        return payBudgetMapper.updateAllPayBudget(userId,monthBudget,yearBudget);
    }
    /**
     * 批量删除预算
     * 
     * @param userIds 需要删除的预算主键
     * @return 结果
     */
    @Override
    public int deletePayBudgetByUserIds(Long[] userIds)
    {
        return payBudgetMapper.deletePayBudgetByUserIds(userIds);
    }

    /**
     * 删除预算信息
     * 
     * @param userId 预算主键
     * @return 结果
     */
    @Override
    public int deletePayBudgetByUserId(Long userId)
    {
        return payBudgetMapper.deletePayBudgetByUserId(userId);
    }
}
