package com.toast.fuchuang.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 用于生成元宇宙形象
 *
 * {'income': 收入总金额,
 *      'expenditure': 支出总金额,
 *      'diet': 饮食金额, 1
 *      'relaxation': 休闲金额,5
 *      'shopping': 购物金额,2
 *      'education': 教育金额,6
 *      'health': 医疗金额 25
 *      }
 */
public class FigureDto implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 月收入*/
    private BigDecimal income;
    /** 月支出*/
    private BigDecimal expenditure;
    /** 饮食*/
    private BigDecimal diet;
    /** 休闲*/
    private BigDecimal relaxation;
    /** 购物*/
    private BigDecimal shopping;
    /** 教育*/
    private BigDecimal education;
    /** 医疗*/
    private BigDecimal health;

}
