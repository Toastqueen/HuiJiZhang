package com.toast.fuchuang.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.toast.fuchuang.domain.PayBudget;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 预算Mapper接口
 * 
 * @author toast
 * @date 2023-03-21
 */
@Mapper
public interface PayBudgetMapper 
{
    @Select("select user_id,month_budget,year_budget from user_budget where user_id=#{userId}")
    Map<String, Object> selectallPayBudget(@Param("userId") Long userId);
    /**
     * 查询预算
     * 
     * @param Id 预算主键
     * @return 预算
     */
    public PayBudget selectPayBudgetById(Long Id);

    /**
     * 查询预算列表 按月
     *
     * @return 预算集合
     */
    public List<PayBudget>  selectPayBudgetList(@Param("userId")Long userId);

    /**
     * 查询预算列表 按年
     *
     * @return 预算集合
     */
    List<PayBudget> selectPayBudgetListOFYear(@Param("userId")Long userId);

    /**
     * 新增预算
     * 
     * @param payBudget 预算
     * @return 结果
     */
    public int insertPayBudget(PayBudget payBudget);

    /**
     * 新增总预算
     *
     * @return 结果
     */
    public int insertallPayBudget(@Param("userId")Long userId, @Param("monthBudget")BigDecimal monthBudget,@Param("yearBudget")BigDecimal yearBudget);

    /**
     * 修改预算
     * 
     * @param payBudget 预算
     * @return 结果
     */
    public int updatePayBudget(PayBudget payBudget);

    /**
     * 修改总预算
     * @param userId
     * @param monthBudget
     * @param yearBudget
     * @return
     */
    public int updateAllPayBudget(@Param("userId")Long userId, @Param("monthBudget")BigDecimal monthBudget,@Param("yearBudget")BigDecimal yearBudget);

    /**
     * 删除预算
     * 
     * @param id 预算主键
     * @return 结果
     */
    public int deletePayBudgetByUserId(Long id);

    /**
     * 批量删除预算
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePayBudgetByUserIds(Long[] userIds);
}
