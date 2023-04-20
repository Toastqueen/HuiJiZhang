package com.toast.fuchuang.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.toast.common.core.domain.Result;
import com.toast.common.utils.tools.HttpUtil;
import com.toast.fuchuang.domain.*;
import com.toast.fuchuang.domain.Vo.MoneyStatisticVo;
import com.toast.fuchuang.mapper.MoneyRecordMapper;
import com.toast.fuchuang.mapper.PayBudgetMapper;
import com.toast.fuchuang.service.FeignService;
import com.toast.fuchuang.service.IAdvancedFunctionService;
import com.toast.fuchuang.service.IMoneyRecordService;
import com.toast.fuchuang.service.IMoneyTypeService;
import com.toast.fuchuang.util.OpenAIUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * 高级功能：gpt分析、用户画像标号
 *
 * @author toast
 */
@Service
public class AdvancedFunctionServiceImpl implements IAdvancedFunctionService {

    @Autowired
    private MoneyRecordMapper moneyRecordMapper;
    @Autowired
    private IMoneyRecordService moneyRecordService;
    @Autowired
    private IMoneyTypeService moneyTypeService;
    @Autowired
    private OpenAIUtil openAIUtil;
    @Autowired
    private PayBudgetMapper payBudgetMapper;
    @Value("${toast.pythonUserTabServerUrl}")
    private String pythonUserTabServerUrl;
    @Resource
    private FeignService feignService;

    @Override
    public String aiAnalysisBill(Long userId){

        //获取当前月支出统计详情 按类型统计
        LocalDate currentDate = LocalDate.now();

        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        Date startMonthTime = Date.from(firstDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate lastDayOfMonth = currentDate.withDayOfMonth(currentDate.lengthOfMonth());
        Date endMonthTime = Date.from(lastDayOfMonth.atStartOfDay(ZoneId.systemDefault()).toInstant());
        MoneyStatisticDto monthDto = new MoneyStatisticDto();
        monthDto.setUserId(userId);
        monthDto.setStartTime(startMonthTime);
        monthDto.setEndTime(endMonthTime);
        List<MoneyStatisticVo> monthRecordlist = moneyRecordService.getPayStatisticDetailOfType(monthDto);

        //获取当前年支出统计详情 按类型统计
        LocalDate firstDayOfYear = currentDate.withDayOfYear(1);
        Date startYearTime = Date.from(firstDayOfYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDate lastDayOfYear = currentDate.withDayOfYear(currentDate.lengthOfYear());
        Date endYearTime = Date.from(lastDayOfYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
        MoneyStatisticDto yearDto = new MoneyStatisticDto();
        yearDto.setUserId(userId);
        yearDto.setStartTime(startYearTime);
        yearDto.setEndTime(endYearTime);
        List<MoneyStatisticVo> yearRecordlist = moneyRecordService.getPayStatisticDetailOfType(yearDto);
        AiDTO aiDTO = new AiDTO();

        //获取年龄
        Integer age;
        Result result = feignService.userInfoByUserId(userId);
        if (result.getData()==null)
            throw new RuntimeException("查询用户信息为空！");
        if (result.getCode() == 200) {
            LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
            age = (int) linkedHashMap.get("age");
        } else {
            age = null;
        }


//        List<MoneyRecordAI> monthRecordlist = new ArrayList<>();
//        List<MoneyRecordAI> yearRecordlist = new ArrayList<>();
//        //获取消费记录
//        LocalDate currentDate = LocalDate.now();
//        int currentMonth = currentDate.getMonthValue();
//        int currentYear = currentDate.getYear();
//        List<MoneyRecord> monthMoneyRecords = moneyRecordMapper.selectMonthMoneyRecordList(userId,currentMonth,currentYear);
//        System.out.println(currentMonth+"月消费记录:"+monthMoneyRecords);
//        for (MoneyRecord m:monthMoneyRecords) {
//            MoneyRecordAI moneyRecordAI = new MoneyRecordAI();
//            moneyRecordAI.setMoney(m.getMoney());
//            moneyRecordAI.setTypeName((String) moneyTypeService.getTypeInfoByTypeId(m.getTypeId()).get("type_name"));
//            monthRecordlist.add(moneyRecordAI);
//        }
//        List<MoneyRecord> yearMoneyRecords = moneyRecordMapper.selectYearMoneyRecordList(userId, currentYear);
//        System.out.println(currentYear+"年消费记录:"+yearMoneyRecords);
//        for (MoneyRecord m:yearMoneyRecords) {
//            MoneyRecordAI moneyRecordAI = new MoneyRecordAI();
//            moneyRecordAI.setMoney(m.getMoney());
//            moneyRecordAI.setTypeName((String) moneyTypeService.getTypeInfoByTypeId(m.getTypeId()).get("type_name"));
//            yearRecordlist.add(moneyRecordAI);
//        }
        //获取预算
        Map<String, Object> map = payBudgetMapper.selectallPayBudget(userId);
        if (map!=null){
            if (map.get("month_budget")!=null)
                aiDTO.setMonthBudget((BigDecimal) map.get("month_budget"));
            if (map.get("year_budget")!=null)
                aiDTO.setYearBudget((BigDecimal) map.get("year_budget"));
        }
        aiDTO.setMonthMoneyRecord(monthRecordlist);
        aiDTO.setYearMoneyRecord(yearRecordlist);
        aiDTO.setAge(age);
        String content = openAIUtil.curlPyServer(aiDTO);
        System.out.println(aiDTO);
        System.out.println(content);
        return content;

    }

    @Override
    public int getUserFigureTab(Long userId){
        FigureDto figureDto = new FigureDto();
        //构建Dto
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        BigDecimal pay = moneyRecordMapper.GetPaymentMoneyByMonth(userId, currentMonth);
        BigDecimal income = moneyRecordMapper.GetIncomeMoneyByMonth(userId, currentMonth);
        BigDecimal education = moneyRecordMapper.getMoneyByParentIdCurrentMonth(userId, 6, currentMonth,currentYear);
        BigDecimal diet = moneyRecordMapper.getMoneyByParentIdCurrentMonth(userId, 1, currentMonth,currentYear);
        BigDecimal shopping = moneyRecordMapper.getMoneyByParentIdCurrentMonth(userId, 2, currentMonth,currentYear);
        BigDecimal relaxation = moneyRecordMapper.getMoneyByParentIdCurrentMonth(userId, 5, currentMonth,currentYear);
        BigDecimal health = moneyRecordMapper.getMoneyByTypeIdCurrentMonth(userId, 25, currentMonth,currentYear);
        //准备发起请求
        String url = pythonUserTabServerUrl;
        String res;
        int content = -1;
        //构建请求体
        Map<String, String> param = new HashMap<>();
        param.put("income", String.valueOf(income));
        param.put("expenditure", String.valueOf(pay));
        param.put("diet", String.valueOf(diet));
        param.put("relaxation", String.valueOf(relaxation));
        param.put("education", String.valueOf(education));
        param.put("shopping", String.valueOf(shopping));
        if(health!=null){
            param.put("health", String.valueOf(health));
        }else{
            param.put("health", String.valueOf(0));
        }

        try {
            res = HttpUtil.HttpPost(url, param);
            JSONObject jsonObject = JSONObject.parseObject(res);
            content = (int) jsonObject.get("info");
            System.out.println("code=" + jsonObject.get("code"));
            if (!jsonObject.get("code").equals(200)) {
                throw new RuntimeException("调用接口失败！");
            }
            System.out.println(content);
            return content;
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }
}
