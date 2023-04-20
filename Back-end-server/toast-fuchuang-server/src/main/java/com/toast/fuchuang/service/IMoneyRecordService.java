package com.toast.fuchuang.service;

import java.io.IOException;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.opencsv.exceptions.CsvValidationException;
import com.toast.fuchuang.domain.ExcelMoneyRecord;
import com.toast.fuchuang.domain.MoneyRecord;
import com.toast.fuchuang.domain.MoneyStatisticDto;
import com.toast.fuchuang.domain.Vo.MoneyRecordVo;
import com.toast.fuchuang.domain.Vo.MoneyStatisticVo;

/**
 * 用户记账Service接口
 *
 * @author toast
 * @date 2023-03-02
 */
public interface IMoneyRecordService
{
    /**
     * 查询用户某个记账记录
     *
     * @param id 用户记账主键id
     * @return 用户记账VO
     */
    public MoneyRecordVo selectMoneyRecordById(Long id);

    /**
     * 查询用户记账列表
     *
     * @param moneyRecord 用户记账
     * @return 用户记账集合
     */
    public PageInfo<MoneyRecordVo> selectMoneyRecordList(MoneyRecord moneyRecord,Integer pageNum,Integer pageSize);


    List<ExcelMoneyRecord> selectExcelMoneyRecordListByMonth(Long userId, int month);

    List<ExcelMoneyRecord> selectExcelMoneyRecordListByYear(Long userId, int year);

    /**
     * 查询某记录的附件
     * @param recordId
     * @return
     */
    String[] getMoneyRecordFileUrl(Long recordId);

    /**
     * 新增用户记账
     *
     * @param moneyRecord 用户记账
     * @return 结果
     */
    public int insertMoneyRecord(MoneyRecord moneyRecord);

    /**
     * 修改用户记账
     *
     * @param moneyRecord 用户记账
     * @return 结果
     */
    public int updateMoneyRecord(MoneyRecord moneyRecord);

    /**
     * 批量删除用户记账
     *
     * @param ids 需要删除的用户记账主键集合
     * @return 结果
     */
    public int deleteMoneyRecordByIds(Long[] ids);

    /**
     * 删除用户记账信息
     *
     * @param id 用户记账主键
     * @return 结果
     */
    public int deleteMoneyRecord(Long id);
    /**
     * 获取指定时间范围的小类型收入统计详情
     * @return
     */
    List<MoneyStatisticVo> getIncomeStatisticDetailOfType(MoneyStatisticDto moneyStatisticDto);
    //收入没有父类型 弃用
    @Deprecated
    List<MoneyStatisticVo> getIncomeStatisticDetailOfParentType(MoneyStatisticDto moneyStatisticDto);
    /**
     * 获取指定时间范围的小类型支出统计详情
     * @return
     */
    List<MoneyStatisticVo> getPayStatisticDetailOfType(MoneyStatisticDto moneyStatisticDto);
    /**
     * 获取指定时间范围的大类型支出统计详情
     * @return
     */
    List<MoneyStatisticVo> getPayStatisticDetailOfParentType(MoneyStatisticDto moneyStatisticDto);

    /**
     * 批量导入测试数据
     * @throws IOException
     * @throws CsvValidationException
     */
    void importMoneyRecordsFromCSV(int index) throws IOException, CsvValidationException;
}
