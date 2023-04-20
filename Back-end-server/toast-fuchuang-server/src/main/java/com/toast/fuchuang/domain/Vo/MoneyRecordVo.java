package com.toast.fuchuang.domain.Vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.toast.fuchuang.domain.MoneyRecord;
import com.toast.fuchuang.domain.MoneyRecordPicture;
import io.swagger.annotations.ApiModelProperty;
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
public class MoneyRecordVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 主键id */
    @ApiModelProperty("记录id")
    private Long id;
    /** 用户id */
    @ApiModelProperty("该记录的用户id")
    private Long userId;
    /** 记录类型id */
    @ApiModelProperty("该记录金额类型id")
    private Long typeId;
    /** 记录类型名字 */
    @ApiModelProperty("金额类型名称")
    private String typeName;
    /** 父类型id */
    @ApiModelProperty("父类型id")
    private Long parentId;
    /** 父类型名字 */
    @ApiModelProperty("父类型名称")
    private String parentName;
    @ApiModelProperty("记账类型标识 true为支出 false为收入")
    private Boolean isPayment;
    /** 记录金额 */
    @ApiModelProperty("金额")
    private BigDecimal money;
    /** 备注*/
    @ApiModelProperty("备注")
    private String remark;
    /** 商家*/
    @ApiModelProperty("商家")
    private String seller;
    /** 创建时间*/
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 拓展信息*/
    @ApiModelProperty("拓展信息")
    private String info;
    @ApiModelProperty("附件地址")
    private String[] fileUrls;

    public MoneyRecordVo(MoneyRecord moneyRecord,String typeName,Boolean isPayment) {
        this.id=moneyRecord.getId();
        this.money=moneyRecord.getMoney();
        this.typeId=moneyRecord.getTypeId();
        this.userId=moneyRecord.getUserId();
        this.createTime=moneyRecord.getCreateTime();
        this.seller=moneyRecord.getSeller();
        this.remark=moneyRecord.getRemark();
        this.info=moneyRecord.getInfo();
        this.fileUrls=moneyRecord.getMoneyRecordPicture().getFileUrls();
        this.typeName=typeName;
        this.isPayment=isPayment;
    }
}



