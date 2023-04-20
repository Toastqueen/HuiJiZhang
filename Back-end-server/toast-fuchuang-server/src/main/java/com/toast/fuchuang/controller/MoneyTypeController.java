package com.toast.fuchuang.controller;

import java.util.List;
import com.toast.common.core.domain.HttpStatusCode;

import com.toast.common.core.domain.Result;
import com.toast.fuchuang.domain.MoneyType;
import com.toast.fuchuang.domain.Vo.MoneyTypeVo;
import com.toast.fuchuang.service.IMoneyTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.toast.fuchuang.service.IMoneyRecordService;


/**
 * 金额类型有关Controller
 * 
 * @author toast
 * @date 2023-02-28
 */
@RestController
@RequestMapping("/type")
public class MoneyTypeController
{

    @Autowired
    private IMoneyTypeService moneyTypeService;


    @ApiOperation(value = "获取用户可见的金额类型 没有分页")
    @GetMapping("/getAllMoneyType")
    public Result getAllMoneyType(@RequestParam("userId") Long userId){
        if(userId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        List<MoneyTypeVo> allMoneyType = null;
        try{
            allMoneyType = moneyTypeService.getAllMoneyType(userId);
        }catch (Exception e){
            System.out.println(e);
            return new Result(HttpStatusCode.ERROR, "获取用户可见金额类型失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"获取用户可见金额类型成功",allMoneyType);
    }

    @ApiOperation(value = "用户新增类型")
    @PostMapping("/addMoneyType")
    public Result addMoneyType(@RequestParam("userId") Long userId, @RequestBody MoneyType moneyType){
        if(userId==null||moneyType.getTypeName()==null||moneyType.getRecordType()==null||moneyType.getContentsType()==null||moneyType.getParentId()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        if (moneyType.getContentsType().equals("0")&&moneyType.getParentId()!=0){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误 新增为目录类型无父类型");
        }

        try{
            moneyTypeService.insertType(userId,moneyType);
        }catch (Exception e){
            System.out.println(e);
            return new Result(HttpStatusCode.ERROR, "用户新增金额类型失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"用户新增金额类型成功");
    }

    @ApiOperation(value = "用户删除类型")
    @PostMapping("/deleteMoneyType")
    public Result deleteMoneyType(@RequestParam("userId") Long userId, @RequestBody MoneyType moneyType){
        if(userId==null||moneyType.getTypeId()==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        try{
            moneyTypeService.deleteType(userId,moneyType);
        }
        catch (Exception e){
            System.out.println(e);
            return new Result(HttpStatusCode.ERROR, "用户删除金额类型失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"用户删除金额类型成功");
    }

}
