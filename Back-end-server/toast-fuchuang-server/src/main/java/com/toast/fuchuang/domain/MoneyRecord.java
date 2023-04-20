package com.toast.fuchuang.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户记账有关对象 money_record
 * 
 * @author toast
 * @date 2023-02-28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 主键id */
    private Long id;
    /** 用户id */
    private Long userId;
    /** 记录类型id */
    private Long typeId;
    /** 记录金额 */
    private BigDecimal money;
    /** 备注*/
    private String remark;
    /** 商家*/
    private String seller;
    /** 创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /** 更新时间*/
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    /** 1存在 0删除 */
    private Integer visible;
    /** 拓展信息*/
    private String info;
    /** 文件集合*/
    private MoneyRecordPicture moneyRecordPicture;
    /** 类型的父类id*/
    private Long parentId;


}
