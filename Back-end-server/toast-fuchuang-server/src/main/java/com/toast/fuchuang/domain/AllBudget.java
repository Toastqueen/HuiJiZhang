package com.toast.fuchuang.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllBudget implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long userId;
    private BigDecimal monthBudget;
    private BigDecimal yearBudget;
}
