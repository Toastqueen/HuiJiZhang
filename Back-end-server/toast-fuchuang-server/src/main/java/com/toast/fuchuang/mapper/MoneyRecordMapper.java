package com.toast.fuchuang.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.toast.fuchuang.domain.MoneyRecord;
import com.toast.fuchuang.domain.MoneyRecordPicture;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * 用户记账Mapper接口
 *
 * @author toast
 * @date 2023-03-02
 */
@Mapper
public interface MoneyRecordMapper
{
    /**
     * 查询用户记账
     *
     * @param id 用户记账主键
     * @return 用户记账
     */
    public MoneyRecord selectMoneyRecordById(Long id);

    /**
     * 查询用户记账列表
     *
     * @param moneyRecord 用户记账
     * @return 用户记账集合
     */
    public List<MoneyRecord> selectMoneyRecordList(MoneyRecord moneyRecord);

  /**
   * 查询用户月记账列表
   * @param userId
   * @param month
   * @return
   */
    List<MoneyRecord> selectMonthMoneyRecordList(@Param("userId")Long userId,@Param("month")int month,@Param("year")int year);

   /**
    * 查询用户年记账列表
    * @param userId
    * @param year
    * @return
    */
   List<MoneyRecord> selectYearMoneyRecordList(@Param("userId")Long userId,@Param("year")int year);

    /**
     * 新增用户记账
     *
     * @param moneyRecord 用户记账
     * @return 结果
     */
    public int insertMoneyRecord(MoneyRecord moneyRecord);

   //添加记录附件
    @Insert("INSERT INTO money_record_file values(#{moneyRecordPicture.id},#{moneyRecordPicture.recordId},#{url},b'1')")
    void insertMoneyRecordPicture(@Param("moneyRecordPicture") MoneyRecordPicture moneyRecordPicture, @Param("url") String url);
    //查询记录附件
    @Select("SELECT url from money_record_file where visible=b'1' and record_id=#{recordId}")
    String[] getMoneyRecordFileUrl(@Param("recordId") Long recordId);
    /**
     * 修改用户记账
     *
     * @param moneyRecord 用户记账
     * @return 结果
     */
    public int updateMoneyRecord(MoneyRecord moneyRecord);

    /**
     * 软删除用户记账
     *
     * @param id 用户记账主键
     * @return 结果
     */
    int deleteMoneyRecord(Long id);

    /**
     * 批量删除用户记账
     *
     * @param id 用户记账主键
     * @return 结果
     */
    int deleteMoneyRecordByIds(Long[] id);

    /**
     * 查询某月支出金额总和
     * @param userId
     * @param month
     * @return
     */
    BigDecimal GetPaymentMoneyByMonth(@Param("userId")Long userId,@Param("month")int month);

    /**
     * 查询某月收入金额总和
     * @param userId
     * @param month
     * @return
     */
    BigDecimal GetIncomeMoneyByMonth(@Param("userId")Long userId,@Param("month")int month);
    /**
     * 查询时间范围收入金额总和
     * @return
     */
    BigDecimal GetIncomeMoneyByTime(@Param("userId")Long userId,@Param("startTime")String startTime,@Param("endTime")String endTime);
    /**
     * 查询时间范围支出金额总和
     * @return
     */
    BigDecimal GetPaymentMoneyByTime(@Param("userId")Long userId,@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 查询不同类型统计支出金额
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> getIncomeTotalDetailsGroupByTId(@Param("userId")Long userId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 查询不同大类型统计支出金额
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> getIncomeTotalDetailsGroupByPId(@Param("userId")Long userId, @Param("startTime")String startTime, @Param("endTime")String endTime);
    /**
     * 查询不同类型统计收入金额
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> getPaymentTotalDetailsGroupByTId(@Param("userId")Long userId, @Param("startTime")String startTime, @Param("endTime")String endTime);
    /**
     * 查询不同大类型统计收入金额
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> getPaymentTotalDetailsGroupByPId(@Param("userId")Long userId, @Param("startTime")String startTime, @Param("endTime")String endTime);


    List<MoneyRecord> excelMoneyRecordByMonth(@Param("userId")Long userId,@Param("month")int month);

    List<MoneyRecord> excelMoneyRecordByYear(@Param("userId")Long userId,@Param("year")int year);

 /**
  * 根据类型id查询某月份的总金额
  * @param userId
  * @param typeId
  * @param month
  * @return
  */
 BigDecimal getMoneyByTypeIdCurrentMonth(@Param("userId")Long userId, @Param("typeId") int typeId, @Param("month")int month,@Param("year") int year);

 /**
  * 根据父类型id查询某月份的总金额
  * @param userId
  * @param parentId
  * @param month
  * @return
  */
    BigDecimal getMoneyByParentIdCurrentMonth(@Param("userId")Long userId, @Param("parentId") int parentId,@Param("month")int month,@Param("year") int year);

}
