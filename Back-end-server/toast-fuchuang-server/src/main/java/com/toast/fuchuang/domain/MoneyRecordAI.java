package com.toast.fuchuang.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "") // 设置空字符串表示去掉对象名称
public class MoneyRecordAI implements Serializable {
    private static final long serialVersionUID = 1L;
    private String typeName;
    private BigDecimal money;
}
