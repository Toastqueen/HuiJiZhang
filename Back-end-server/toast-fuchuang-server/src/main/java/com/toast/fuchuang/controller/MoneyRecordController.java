package com.toast.fuchuang.controller;


import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageInfo;
import com.opencsv.exceptions.CsvValidationException;
import com.toast.common.core.domain.HttpStatusCode;
import com.toast.common.core.domain.Result;
import com.toast.common.utils.service.FileUpLoad;
import com.toast.fuchuang.domain.ExcelMoneyRecord;
import com.toast.fuchuang.domain.MoneyRecord;
import com.toast.fuchuang.domain.MoneyStatisticDto;
import com.toast.fuchuang.domain.Vo.MoneyRecordVo;
import com.toast.fuchuang.domain.Vo.MoneyStatisticVo;
import com.toast.fuchuang.service.FeignService;
import com.toast.fuchuang.service.IAdvancedFunctionService;
import com.toast.fuchuang.service.IMoneyRecordService;
import com.toast.fuchuang.util.PhotoProcessorServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 用户记账有关Controller
 *
 * @author toast
 * @date 2023-02-28
 */
@RestController
@RequestMapping("/record")
public class MoneyRecordController {
    //设置过的虚拟路径映射
    @Value("${file.uploadFolder}")
    private String uploadFolder;
    @Resource
    private FileUpLoad fileUpLoad;
    @Autowired
    private IMoneyRecordService moneyRecordService;
   @Autowired
   private PhotoProcessorServer photoProcessorServer;
   @Autowired
   private IAdvancedFunctionService advancedFunctionService;
    @Resource
    private FeignService feignService;

    @ApiOperation(value = "用户查询指定记录")
    @GetMapping("/getMoneyRecordById")
    public Result getMoneyRecordById(@RequestParam("recordId")Long recordId){
        if(recordId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        MoneyRecordVo moneyRecordVo = new MoneyRecordVo();
        try{
            moneyRecordVo= moneyRecordService.selectMoneyRecordById(recordId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "查询记录失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"查询记录成功",moneyRecordVo);
    }

    @ApiOperation(value = "分页获取用户所有记账记录")
    @GetMapping("/getMoneyRecordList")
    public Result getMoneyRecordList(@RequestParam("userId")Long userId,Integer pageNum,Integer pageSize){
        if(userId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        PageInfo<MoneyRecordVo> moneyRecordVoList = null;
        try{
            MoneyRecord moneyRecord = new MoneyRecord();
            moneyRecord.setUserId(userId);
            moneyRecordVoList = moneyRecordService.selectMoneyRecordList(moneyRecord, pageNum, pageSize);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "查询记录失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"查询记录成功",moneyRecordVoList);
    }



    @ApiOperation(value = "新增记录")
    @PostMapping("/addMoneyRecord")
    public Result addMoneyRecord( @RequestBody MoneyRecord moneyRecord){
        if(moneyRecord.getUserId() ==null||moneyRecord.getMoney()==null||moneyRecord.getTypeId()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        try{
            moneyRecordService.insertMoneyRecord(moneyRecord);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "用户新增记录失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"用户新增记录成功");
    }

    @ApiOperation(value = "修改记录")
    @PostMapping("/updateMoneyRecord")
    public Result updateMoneyRecord( @RequestBody MoneyRecord moneyRecord){
        if(moneyRecord.getId()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        try{
            moneyRecordService.updateMoneyRecord(moneyRecord);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "用户修改记录失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"用户修改记录成功");
    }

    @ApiOperation(value = "删除记录")
    @PutMapping("/deleteMoneyRecord")
    public Result deleteMoneyRecord(@RequestParam("recordId") Long recordId){
        if(recordId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        try{
            moneyRecordService.deleteMoneyRecord(recordId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "用户删除记录失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"用户删除记录成功");
    }

    @ApiOperation(value = "批量删除记录")
    @PutMapping("/deleteMoneyRecords")
    public Result deleteMoneyRecords(@RequestBody Long[] recordIds){
        if(recordIds==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        try{
            moneyRecordService.deleteMoneyRecordByIds(recordIds);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "用户批量删除记录失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"用户批量删除记录成功");
    }


    @ApiOperation(value = "获取指定时间范围的类型收入统计详情")
    @GetMapping("/incomeStatisticDetailOfType")
    public Result IncomeStatisticDetailOfType(MoneyStatisticDto moneyStatisticDto){
        if(moneyStatisticDto.getUserId()==null||moneyStatisticDto.getStartTime()==null||moneyStatisticDto.getEndTime()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        List<MoneyStatisticVo> listVo= new ArrayList<>();
        try{
            listVo = moneyRecordService.getIncomeStatisticDetailOfType(moneyStatisticDto);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR,"查询指定时间范围的小类型收入统计详情失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"查询指定时间范围的小类型收入统计详情成功",listVo);
    }

    @ApiOperation(value = "获取指定时间范围的大类型支出统计详情")
    @GetMapping("/paymentStatisticDetailOfParentType")
    public Result paymentStatisticDetailOfParentType(MoneyStatisticDto moneyStatisticDto){
        if(moneyStatisticDto.getUserId()==null||moneyStatisticDto.getStartTime()==null||moneyStatisticDto.getEndTime()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        List<MoneyStatisticVo> listVo= new ArrayList<>();
        try{
            listVo = moneyRecordService.getPayStatisticDetailOfParentType(moneyStatisticDto);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR,"查询指定时间范围的大类型支出统计详情失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"查询指定时间范围的大类型支出统计详情成功",listVo);
    }

    @ApiOperation(value = "获取指定时间范围的小类型支出统计详情")
    @GetMapping("/paymentStatisticDetailOfType")
    public Result paymentStatisticDetailOfType(MoneyStatisticDto moneyStatisticDto){
        System.out.println(moneyStatisticDto.getStartTime());
        System.out.println(moneyStatisticDto.getEndTime());
        if(moneyStatisticDto.getUserId()==null||moneyStatisticDto.getStartTime()==null||moneyStatisticDto.getEndTime()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        List<MoneyStatisticVo> listVo= new ArrayList<>();
        try{
            listVo = moneyRecordService.getPayStatisticDetailOfType(moneyStatisticDto);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR,"查询指定时间范围的小类型支出统计详情失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"查询指定时间范围的小类型支出统计详情成功",listVo);
    }


    @ApiOperation(value = "多张张图片并行识别")
    @PutMapping("/photoProcessor")
    public Result photoProcessor(@RequestBody Map<String, Object> requestBody) throws Exception {
        List<MoneyRecordVo> volist = new ArrayList<>();
        if (requestBody.get("userId").toString()==null||requestBody.get("urls")==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        try {
            Long userId = Long.parseLong(requestBody.get("userId").toString());
            String[] urls = ((List<String>)requestBody.get("urls")).toArray(new String[0]);
            System.out.println(urls);
            volist = photoProcessorServer.getVolist(Arrays.asList(urls), userId);
        }catch (Exception e){
            return new Result(HttpStatusCode.ERROR,"图像识别失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"图像识别成功",volist);
    }

    @ApiOperation(value = "导出月记账记录")
    @GetMapping("/excelDownloadByMonth")
    public void excelDownloadByMonth(@RequestParam("userId")Long userId,@RequestParam("month")int month, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("您的账单", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<ExcelMoneyRecord> list = moneyRecordService.selectExcelMoneyRecordListByMonth(userId,month);
        EasyExcel.write(response.getOutputStream(), ExcelMoneyRecord.class).sheet("用户信息").doWrite(list);
    }

    @ApiOperation(value = "导出年记账记录")
    @GetMapping("/excelDownloadByYear")
    public void excelDownloadByYear(@RequestParam("userId")Long userId,@RequestParam("year")int year, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("您的账单", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<ExcelMoneyRecord> list = moneyRecordService.selectExcelMoneyRecordListByYear(userId,year);
        EasyExcel.write(response.getOutputStream(), ExcelMoneyRecord.class).sheet("用户信息").doWrite(list);
    }

    @ApiOperation(value = "导出月记账记录 (存储版)")
    @GetMapping("/excelDownloadByMonthFileUrl")
    public Result excelDownloadByMonthFileUrl(@RequestParam("userId")Long userId, @RequestParam("month")int month, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileDownloadUrl = null;
        try {
            File file = new File(uploadFolder);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = "您的月账单" + ".xlsx";
            String fullPath = uploadFolder + fileName;
            List<ExcelMoneyRecord> list = moneyRecordService.selectExcelMoneyRecordListByMonth(userId,month);
            EasyExcel.write(fullPath, ExcelMoneyRecord.class).sheet("用户信息").doWrite(list);

            InetAddress inetAddress = null;
            try {
                //获得本机Ip（获取的是服务器的Ip）
                inetAddress = InetAddress.getLocalHost();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String ip = inetAddress.getHostAddress();
            fileDownloadUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort() + "/api/file/" + fileName;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR,"失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功",fileDownloadUrl);
    }

    @ApiOperation(value = "导出年记账记录 (存储版)")
    @GetMapping("/excelDownloadByYearFileUrl")
    public Result excelDownloadByYearFileUrl(@RequestParam("userId")Long userId, @RequestParam("year")int year, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileDownloadUrl = null;
        try {
            File file = new File(uploadFolder);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = "您的年账单" + ".xlsx";
            String fullPath = uploadFolder + fileName;
            List<ExcelMoneyRecord> list = moneyRecordService.selectExcelMoneyRecordListByYear(userId,year);
            EasyExcel.write(fullPath, ExcelMoneyRecord.class).sheet("用户信息").doWrite(list);

            InetAddress inetAddress = null;
            try {
                //获得本机Ip（获取的是服务器的Ip）
                inetAddress = InetAddress.getLocalHost();

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            String ip = inetAddress.getHostAddress();
            fileDownloadUrl = request.getScheme() + "://" + ip + ":" + request.getServerPort() + "/api/file/" + fileName;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR,"失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功",fileDownloadUrl);
    }



    @ApiOperation(value = "AI分析用户给出建议")
    @GetMapping("/aiAnalysis")
    public Result aiAnalysisBill(@RequestParam("userId")Long userId){
        String content = null;
        if(userId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        try {
            content = advancedFunctionService.aiAnalysisBill(userId);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR,"AI分析失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"AI分析成功",content);
    }

    @ApiOperation(value = "用户形象标号")
    @GetMapping("/userFigureTab")
    public Result UserFigureTab(@RequestParam("userId")Long userId){
        int content = 0;
        if(userId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        try {
            content = advancedFunctionService.getUserFigureTab(userId);
            if (content==-1)
                return new Result(HttpStatusCode.ERROR,"获取用户形象标号失败");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR,"获取用户形象标号失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"获取用户形象标号成功",content);
    }
    @GetMapping("/test")
    public void r(){
        Result feignUserResult = feignService.userInfoByUserId(1611767133559480320L);
        System.out.println(feignUserResult.getCode());
        System.out.println(feignUserResult.getData());
        System.out.println(feignUserResult.getData().getClass());
        LinkedHashMap linkedHashMap = (LinkedHashMap) feignUserResult.getData();
        System.out.println(linkedHashMap.get("userId"));
        System.out.println(linkedHashMap.get("age"));
    }

    @GetMapping(value = "/import/{index}")
    public void importData(@PathVariable int index)
    {
        try {
            moneyRecordService.importMoneyRecordsFromCSV(index);
            System.out.println("开始处理");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }
    }
}
