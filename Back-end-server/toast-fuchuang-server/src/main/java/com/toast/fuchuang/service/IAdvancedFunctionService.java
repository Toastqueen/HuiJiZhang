package com.toast.fuchuang.service;

public interface IAdvancedFunctionService {
    /**
     * gpt分析用户记录给出建议
     * @param userId
     * @return
     */
    String aiAnalysisBill(Long userId);

    /**
     * 给出当前月份的用户形象标号
     * @param userId
     * @return
     */
    int getUserFigureTab(Long userId);
}
