package com.toast.fuchuang.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MoneyStatisticDto {
    /** 用户id*/
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
    /** 起始时间*/
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd ", timezone = "GMT+8")
    /** 终止时间*/
    private Date endTime;
}
