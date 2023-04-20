package com.toast.fuchuang.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.toast.fuchuang.domain.PayBudget;
import org.springframework.stereotype.Service;

/**
 * 预算Service接口
 * 
 * @author toast
 * @date 2023-03-21
 */

public interface IPayBudgetService 
{
    /**
     * 查询预算
     * 
     * @param
     * @return 预算
     */
    public PayBudget selectPayBudgetById(Long Id);

    /**
     * 查询预算列表
     * 
     * @param payBudget 预算
     * @return 预算集合
     */
    public List<PayBudget> selectPayBudgetList(PayBudget payBudget);

    Map selectPayBudget(Long userId);

    List<PayBudget> selectPayBudgetListOfYear(PayBudget payBudget);

    /**
     * 新增预算
     * 
     * @param payBudget 预算
     * @return 结果
     */
    public int insertPayBudget(PayBudget payBudget);

    int insertallPayBudget(Long userId, BigDecimal monthBudget, BigDecimal yearBudget);

    /**
     * 修改预算
     * 
     * @param payBudget 预算
     * @return 结果
     */
    public int updatePayBudget(PayBudget payBudget);

    int updateAllPayBudget(Long userId, BigDecimal monthBudget, BigDecimal yearBudget);

    /**
     * 批量删除预算
     * 
     * @param userIds 需要删除的预算主键集合
     * @return 结果
     */
    public int deletePayBudgetByUserIds(Long[] userIds);

    /**
     * 删除预算信息
     * 
     * @param userId 预算主键
     * @return 结果
     */
    public int deletePayBudgetByUserId(Long userId);
}
