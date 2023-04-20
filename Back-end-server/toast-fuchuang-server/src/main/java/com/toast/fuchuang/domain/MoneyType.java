package com.toast.fuchuang.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyType implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long typeId;
    /** 类型名称*/
    private String typeName;
    /** 父类型id*/
    private Long parentId;
    /** 类型是目录(父)还是下级(子)*/
    private String contentsType;
    /** 类型为支出还是收入*/
    private String recordType;
    /** 可视标签*/
    private Boolean visible;


}
