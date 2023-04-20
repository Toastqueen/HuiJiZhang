package com.toast.fuchuang.service.impl;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import cn.hutool.core.util.IdUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.toast.fuchuang.domain.*;
import com.toast.fuchuang.domain.Vo.MoneyRecordVo;
import com.toast.fuchuang.domain.Vo.MoneyStatisticVo;
import com.toast.fuchuang.mapper.MoneyTypeMapper;
import com.toast.fuchuang.mapper.PayBudgetMapper;
import com.toast.fuchuang.service.IMoneyTypeService;
import com.toast.fuchuang.util.OpenAIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.toast.fuchuang.mapper.MoneyRecordMapper;
import com.toast.fuchuang.service.IMoneyRecordService;

/**
 * 用户记账Service业务层处理
 *
 * @author toast
 * @date 2023-03-02
 */
@Service
public class MoneyRecordServiceImpl implements IMoneyRecordService
{
    @Autowired
    private MoneyRecordMapper moneyRecordMapper;
    @Autowired
    private IMoneyTypeService moneyTypeService;
    @Autowired
    private MoneyTypeMapper moneyTypeMapper;
    @Autowired
    private OpenAIUtil openAIUtil;
    @Autowired
    private PayBudgetMapper payBudgetMapper;


    /**
     * 查询用户某个记账记录
     *
     * @param id 用户记账主键id
     * @return 用户单条记账VO
     */
    @Override
    public MoneyRecordVo selectMoneyRecordById(Long id)
    {
        //查询基本信息
        MoneyRecord moneyRecord = moneyRecordMapper.selectMoneyRecordById(id);
        //查询附件
        String[] fileUrl = getMoneyRecordFileUrl(id);
        MoneyRecordPicture moneyRecordPicture = new MoneyRecordPicture();
        moneyRecordPicture.setRecordId(id);
        moneyRecordPicture.setFileUrls(fileUrl);
        moneyRecord.setMoneyRecordPicture(moneyRecordPicture);
        //构建vo
        String typeName = (String) moneyTypeService.getTypeInfoByTypeId(moneyRecord.getTypeId()).get("type_name");
        Long parentId = 0L;
        Long tempId = (Long) moneyTypeService.getTypeInfoByTypeId(moneyRecord.getTypeId()).get("parent_id");
        if (tempId==0){
            parentId = moneyRecord.getTypeId();
        }else {
            parentId = tempId;
        }
        String parentName = (String) moneyTypeService.getTypeInfoByTypeId(parentId).get("type_name");
        Boolean isPayment=false;
        if (moneyTypeService.getTypeInfoByTypeId(moneyRecord.getTypeId()).get("record_type").equals("1")){
            isPayment=true;
        }
        MoneyRecordVo moneyRecordVo = new MoneyRecordVo(moneyRecord,typeName,isPayment);
        moneyRecordVo.setParentId(parentId);
        moneyRecordVo.setParentName(parentName);
        return moneyRecordVo;
    }

    public ExcelMoneyRecord getExcelVo(Long id)
    {
        //查询基本信息
        MoneyRecord moneyRecord = moneyRecordMapper.selectMoneyRecordById(id);

        //构建vo
        String typeName = (String) moneyTypeService.getTypeInfoByTypeId(moneyRecord.getTypeId()).get("type_name");
        ExcelMoneyRecord excelMoneyRecord = new ExcelMoneyRecord(moneyRecord,typeName);

        if (moneyTypeService.getTypeInfoByTypeId(moneyRecord.getTypeId()).get("record_type").equals("1")){
            excelMoneyRecord.setIsPay("支出");
        }else {
            excelMoneyRecord.setIsPay("收入");
        }
        return excelMoneyRecord;
    }

    /**
     * 查询用户记账列表
     *
     * @param moneyRecord 用户记账
     * @return 用户记账
     */
    @Override
    public PageInfo<MoneyRecordVo> selectMoneyRecordList(MoneyRecord moneyRecord,Integer pageNum,Integer pageSize)
    {
        if (pageNum==null||pageSize==null){
            pageSize=100000;
            pageNum=1;
        }
        List<MoneyRecordVo> list = new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        List<MoneyRecord> moneyRecords = moneyRecordMapper.selectMoneyRecordList(moneyRecord);
        System.out.println("第一次查询结果"+moneyRecords);
        for (MoneyRecord record : moneyRecords) {
            MoneyRecordVo moneyRecordVo = selectMoneyRecordById(record.getId());
            list.add(moneyRecordVo);
        }
        Collections.sort(list, new Comparator<MoneyRecordVo>() {
            @Override
            public int compare(MoneyRecordVo o1, MoneyRecordVo o2) {
                return o2.getCreateTime().compareTo(o1.getCreateTime());
            }
        });
        System.out.println("VoList: "+list);
        PageInfo<MoneyRecordVo> pageInfo = new PageInfo<MoneyRecordVo>(list);

        return pageInfo;
    }

    @Override
    public List<ExcelMoneyRecord> selectExcelMoneyRecordListByMonth(Long userId,int month)
    {

        List<ExcelMoneyRecord> list = new ArrayList<>();
        List<MoneyRecord> moneyRecords = moneyRecordMapper.excelMoneyRecordByMonth(userId,month);
        System.out.println("第一次查询结果"+moneyRecords);
        int i=1;
        for (MoneyRecord record : moneyRecords) {
            ExcelMoneyRecord excelVo = getExcelVo(record.getId());
            excelVo.setNum(i);
            list.add(excelVo);
            i++;
        }
        System.out.println("VoList: "+list);
        return list;
    }

    @Override
    public List<ExcelMoneyRecord> selectExcelMoneyRecordListByYear(Long userId,int year)
    {

        List<ExcelMoneyRecord> list = new ArrayList<>();
        List<MoneyRecord> moneyRecords = moneyRecordMapper.excelMoneyRecordByYear(userId,year);
        System.out.println("第一次查询结果"+moneyRecords);
        int i=1;
        for (MoneyRecord record : moneyRecords) {
            ExcelMoneyRecord excelVo = getExcelVo(record.getId());
            excelVo.setNum(i);
            list.add(excelVo);
            i++;
        }
        System.out.println("VoList: "+list);
        return list;
    }



    /**
     * 查询记账附件
     * @param recordId
     * @return
     */
    @Override
    public String[] getMoneyRecordFileUrl(Long recordId) {
        return moneyRecordMapper.getMoneyRecordFileUrl(recordId);
    }

    /**
     * 新增用户记账
     *
     * @param moneyRecord 用户记账
     * @return 结果
     */
    @Override
    public int insertMoneyRecord(MoneyRecord moneyRecord)
    {
        if (moneyRecord.getCreateTime()==null)
        moneyRecord.setCreateTime(new Date());
        Map<String, Object> typeInfoById = moneyTypeService.getTypeInfoByTypeId(moneyRecord.getTypeId());
        moneyRecord.setParentId((Long) typeInfoById.get("parent_id"));

        Long id = IdUtil.getSnowflakeNextId() / 1000000L;
        moneyRecord.setId(id);

        //添加该用户上传的附件
        MoneyRecordPicture recordPicture = moneyRecord.getMoneyRecordPicture();
        if(recordPicture != null && recordPicture.getFileUrls() != null){
            recordPicture.setRecordId(id);
            for (String url : recordPicture.getFileUrls()) {
                recordPicture.setId(IdUtil.getSnowflakeNextId());
                moneyRecordMapper.insertMoneyRecordPicture(recordPicture, url);
            }
        }

        return moneyRecordMapper.insertMoneyRecord(moneyRecord);
    }

    /**
     * 修改用户记账
     *
     * @param moneyRecord 用户记账
     * @return 结果
     */
    @Override
    public int updateMoneyRecord(MoneyRecord moneyRecord)
    {
        moneyRecord.setUpdateTime(new Date());
        return moneyRecordMapper.updateMoneyRecord(moneyRecord);
    }

    /**
     * 批量删除用户记账
     *
     * @param ids 需要删除的用户记账主键
     * @return 结果
     */
    @Override
    public int deleteMoneyRecordByIds(Long[] ids)
    {
        return moneyRecordMapper.deleteMoneyRecordByIds(ids);
    }

    /**
     * 删除用户记账信息
     *
     * @param id 用户记账主键
     * @return 结果
     */
    @Override
    public int deleteMoneyRecord(Long id)
    {
        return moneyRecordMapper.deleteMoneyRecord(id);
    }

    /**
     * 获取指定时间范围的小类型收入统计详情
     * @return
     */
    @Override
    public List<MoneyStatisticVo> getIncomeStatisticDetailOfType(MoneyStatisticDto moneyStatisticDto){
        List<MoneyStatisticVo> listVo = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 将 Date 类型的时间转换为 MySQL 中的日期格式
        String start = dateFormat.format(moneyStatisticDto.getStartTime());
        String end = dateFormat.format(moneyStatisticDto.getEndTime());
        for (Map<String, Object> map : moneyRecordMapper.getIncomeTotalDetailsGroupByTId(moneyStatisticDto.getUserId(), start, end)) {
            MoneyStatisticVo moneyStatisticVo = new MoneyStatisticVo();
            moneyStatisticVo.setMoney((BigDecimal) map.get("money_counts"));
            Map<String, Object> infoById = moneyTypeService.getTypeInfoByTypeId((Long) map.get("type_id"));
            moneyStatisticVo.setTypeName(String.valueOf(infoById.get("type_name")));
            moneyStatisticVo.setTypeId((Long) map.get("type_id"));
            listVo.add(moneyStatisticVo);
        }

        return listVo;

    }
    /**
     * 获取指定时间范围的大类型收入统计详情
     * @return
     */
    @Override
    public List<MoneyStatisticVo> getIncomeStatisticDetailOfParentType(MoneyStatisticDto moneyStatisticDto){
        List<MoneyStatisticVo> listVo = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 将 Date 类型的时间转换为 MySQL 中的日期格式
        String start = dateFormat.format(moneyStatisticDto.getStartTime());
        String end = dateFormat.format(moneyStatisticDto.getEndTime());
        for (Map<String, Object> map : moneyRecordMapper.getIncomeTotalDetailsGroupByPId(moneyStatisticDto.getUserId(), start, end)) {
            MoneyStatisticVo moneyStatisticVo = new MoneyStatisticVo();
            moneyStatisticVo.setMoney((BigDecimal) map.get("money_counts"));
            Map<String, Object> infoById = moneyTypeService.getTypeInfoByTypeId((Long) map.get("parent_id"));
            System.out.println(infoById);
            moneyStatisticVo.setTypeName(String.valueOf(infoById.get("type_name")));
            moneyStatisticVo.setTypeId((Long) map.get("type_id"));
            listVo.add(moneyStatisticVo);
        }

        return listVo;

    }
    /**
     * 获取指定时间范围的小类型支出统计详情
     * @return
     */
    @Override
    public List<MoneyStatisticVo> getPayStatisticDetailOfType(MoneyStatisticDto moneyStatisticDto){
        List<MoneyStatisticVo> listVo = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 将 Date 类型的时间转换为 MySQL 中的日期格式
        String start = dateFormat.format(moneyStatisticDto.getStartTime());
        String end = dateFormat.format(moneyStatisticDto.getEndTime());
        System.out.println(start);
        System.out.println(end);
        for (Map<String, Object> map :  moneyRecordMapper.getPaymentTotalDetailsGroupByTId(moneyStatisticDto.getUserId(), start, end)) {
            MoneyStatisticVo moneyStatisticVo = new MoneyStatisticVo();
            moneyStatisticVo.setMoney((BigDecimal) map.get("money_counts"));
            moneyStatisticVo.setTypeId((Long) map.get("type_id"));
            Map<String, Object> infoById = moneyTypeService.getTypeInfoByTypeId((Long) map.get("type_id"));
            moneyStatisticVo.setTypeName(String.valueOf(infoById.get("type_name")));
            listVo.add(moneyStatisticVo);
        }

        return listVo;

    }
    /**
     * 获取指定时间范围的大类型支出统计详情
     * @return
     */
    @Override
    public List<MoneyStatisticVo> getPayStatisticDetailOfParentType(MoneyStatisticDto moneyStatisticDto){
        List<MoneyStatisticVo> listVo = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 将 Date 类型的时间转换为 MySQL 中的日期格式
        String start = dateFormat.format(moneyStatisticDto.getStartTime());
        String end = dateFormat.format(moneyStatisticDto.getEndTime());
        for (Map<String, Object> map : moneyRecordMapper.getPaymentTotalDetailsGroupByPId(moneyStatisticDto.getUserId(), start, end)) {
            System.out.println(map);
            MoneyStatisticVo moneyStatisticVo = new MoneyStatisticVo();
            moneyStatisticVo.setMoney((BigDecimal) map.get("money_counts"));
            Map<String, Object> infoById = moneyTypeService.getTypeInfoByTypeId((Long) map.get("parent_id"));
            System.out.println(infoById);
            moneyStatisticVo.setTypeName(String.valueOf(infoById.get("type_name")));
            moneyStatisticVo.setTypeId((Long) map.get("type_id"));
            listVo.add(moneyStatisticVo);
        }
        return listVo;
    }

    @Override
    public void importMoneyRecordsFromCSV(int index) throws IOException, CsvValidationException {
        String[] list = new String[]{"E:\\out.csv","E:\\out1.csv","E:\\out2.csv","E:\\out3.csv"};
        Reader reader = new FileReader(list[index]);
        CSVParser parser = new CSVParserBuilder().withSeparator(',').withIgnoreQuotations(true).build();
        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).build();
        String[] headers = csvReader.readNext(); // 获取csv文件的表头
        String[] line;
        Long userId = 1646160385438L;
        LocalDateTime currentDateTime = LocalDateTime.of(2020, 1, 1, 0, 0);
        int count = 0; // 当前时间段内记录数量
        while ((line = csvReader.readNext()) != null) {
            if (count % 4 == 0) {
                currentDateTime = currentDateTime.plusDays(2);
            }
            String name = line[0];
            String superclass = line[1];
            String subclass = line[2];
            String moneyStr = line[3];
            if(name == null | subclass == null | superclass == null | moneyStr == null || moneyStr.isEmpty()){
                System.out.println(name+"数据不完整 跳过");
                continue;
            }
            Double m = Double.parseDouble(moneyStr);
            BigDecimal money = BigDecimal.valueOf(m);
            Long typeId = moneyTypeMapper.selectMoneyTypeIdByName(subclass);
            Long parentId = moneyTypeMapper.selectMoneyTypeIdByName(superclass);
            if (typeId == null || parentId == null){
                System.out.println(name+"类型无对应 跳过");
                continue;
            }

            MoneyRecord moneyRecord = new MoneyRecord();
            moneyRecord.setId(IdUtil.getSnowflakeNextId() / 1000000L);
            moneyRecord.setUserId(userId);
            moneyRecord.setTypeId(typeId);
            moneyRecord.setParentId(parentId);
            moneyRecord.setMoney(money);
            moneyRecord.setRemark(name);
            Date createTime = new Date(currentDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
            moneyRecord.setCreateTime(createTime);

            moneyRecordMapper.insertMoneyRecord(moneyRecord);
            System.out.println(name+superclass+subclass+money+"已录入");
            System.out.println(moneyRecord+"\n");
            count ++;
        }
    }
}

