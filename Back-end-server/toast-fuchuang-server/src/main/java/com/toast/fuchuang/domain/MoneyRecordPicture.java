package com.toast.fuchuang.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 记录图片URl上传实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyRecordPicture implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id; //记录ID
    private Long recordId; //结算 ID
    private String[] fileUrls; //图片URL数组
    private boolean visible; //逻辑删除标志


}
