package com.toast.fuchuang.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.toast.fuchuang.domain.MoneyRecord;
import com.toast.fuchuang.domain.MoneyRecordAI;
import com.toast.fuchuang.domain.Vo.MoneyStatisticVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "") // 设置空字符串表示去掉对象名称
public class AiDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private int age;
    //月预算
    private BigDecimal monthBudget;
    //年预算
    private BigDecimal yearBudget;
    //当月消费统计
    private List<MoneyStatisticVo> monthMoneyRecord;
    //当年消费统计
    private List<MoneyStatisticVo> yearMoneyRecord;
}
