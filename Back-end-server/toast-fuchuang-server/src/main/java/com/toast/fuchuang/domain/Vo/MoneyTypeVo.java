package com.toast.fuchuang.domain.Vo;

import com.toast.fuchuang.domain.MoneyType;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTypeVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("类型id")
    private Long typeId;
    @ApiModelProperty("类型名称")
    private String typeName;
    @ApiModelProperty("父类型id")
    private Long parent_id;
    @ApiModelProperty("是否为子类型 true为子类型")
    private boolean isSub;
    @ApiModelProperty("金额类型 支出或收入")
    private String type;

    public MoneyTypeVo(MoneyType moneyType) {
        this.typeId=moneyType.getTypeId();
        this.typeName=moneyType.getTypeName();
        this.parent_id=moneyType.getParentId();
        this.isSub=((moneyType.getContentsType().equals("1")));
        this.type=(moneyType.getRecordType().equals("0")?"收入":"支出");

    }
}
