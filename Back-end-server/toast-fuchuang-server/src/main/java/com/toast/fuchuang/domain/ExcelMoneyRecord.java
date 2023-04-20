package com.toast.fuchuang.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExcelMoneyRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @ExcelProperty("序号")
    private int num;
    @ExcelProperty("类型")
    private String typeName;
    @ExcelProperty("支出/收入")
    private String isPay;
    @ExcelProperty("金额")
    private BigDecimal money;
    @ExcelProperty("商家")
    private String seller;
    @ExcelProperty("备注")
    private String remark;
    @ExcelProperty("创建时间")
    private Date createTime;


    public ExcelMoneyRecord(MoneyRecord moneyRecord,String typeName) {

        this.money=moneyRecord.getMoney();
        this.createTime=moneyRecord.getCreateTime();
        this.seller=moneyRecord.getSeller();
        this.remark=moneyRecord.getRemark();
        this.typeName=typeName;
    }
}
