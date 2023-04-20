package com.toast.fuchuang.domain.Vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "") // 设置空字符串表示去掉对象名称
public class MoneyStatisticVo {
    private Long typeId;
    private String typeName;
    private BigDecimal money;
}
