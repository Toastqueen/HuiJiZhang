package com.toast.fuchuang.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 预算对象 pay_budget
 * 
 * @author toast
 * @date 2023-03-21
 */

public class PayBudget
{
    private static final long serialVersionUID = 1L;

    private Long id;
    /** $column.columnComment */
    private Long userId;

    /** 该类型预算 */

    private BigDecimal typeBudget;

    /** 类型id */

    private Long typeId;

    /** 所有类型预算之和 */

    private BigDecimal allBudget;

    /** 月预算还是年 默认1为月 */

    private Integer monthYear;

    /** $column.columnComment */
    private Integer visible;

    public PayBudget() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getTypeBudget() {
        return typeBudget;
    }

    public void setTypeBudget(BigDecimal typeBudget) {
        this.typeBudget = typeBudget;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public BigDecimal getAllBudget() {
        return allBudget;
    }

    public void setAllBudget(BigDecimal allBudget) {
        this.allBudget = allBudget;
    }

    public Integer getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(Integer monthYear) {
        this.monthYear = monthYear;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public PayBudget(Long id, Long userId, BigDecimal typeBudget, Long typeId, BigDecimal allBudget, Integer monthYear, Integer visible) {
        this.id = id;
        this.userId = userId;
        this.typeBudget = typeBudget;
        this.typeId = typeId;
        this.allBudget = allBudget;
        this.monthYear = monthYear;
        this.visible = visible;
    }
}
