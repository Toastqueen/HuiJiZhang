package com.toast.fuchuang.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.IdUtil;
import com.toast.common.core.domain.HttpStatusCode;
import com.toast.common.core.domain.Result;
import com.toast.fuchuang.domain.AllBudget;
import com.toast.fuchuang.service.IPayBudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.toast.fuchuang.domain.PayBudget;


/**
 * 预算Controller
 * 
 * @author toast
 * @date 2023-03-21
 */
@RestController
@RequestMapping("/budget")
public class PayBudgetController
{
    @Autowired
    private IPayBudgetService payBudgetService;

    /**
     * 查询预算列表按月
     */
    @GetMapping("/listOfMonth/{userId}")
    public Result listOfMonth(@PathVariable("userId") Long userId)
    {
        if(userId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        PayBudget payBudget = new PayBudget();
        payBudget.setUserId(userId);
        List<PayBudget> list = new ArrayList<>();
        try{
            list = payBudgetService.selectPayBudgetList(payBudget);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功",list);
    }

    /**
     * 查询预算列表按年
     */
    @GetMapping("/listOfYear/{userId}")
    public Result listOfYear(@PathVariable("userId") Long userId)
    {
        if(userId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        List<PayBudget> list = new ArrayList<>();
        PayBudget payBudget = new PayBudget();
        payBudget.setUserId(userId);
        try{
            list = payBudgetService.selectPayBudgetListOfYear(payBudget);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功",list);
    }

    /**
     * 查询用户总预算 月和年
     */
    @GetMapping("/allBudget/{userId}")
    public Result allBudget(@PathVariable("userId") Long userId)
    {
        if(userId==null){
            return new Result(HttpStatusCode.BAD_REQUEST, "参数错误");
        }
        Map map = new HashMap<>();
        try{
            map = payBudgetService.selectPayBudget(userId);
            System.out.println(map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功",map);
    }


    /**
     * 获取预算详细信息
     */

    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        PayBudget payBudget = new PayBudget();
        try{
            payBudget = payBudgetService.selectPayBudgetById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功",payBudget);

    }

    /**
     * 新增类型预算
     */
    @PostMapping(value = "/addTypeBudget")
    public Result add(@RequestBody PayBudget payBudget)
    {
        Long id = IdUtil.getSnowflakeNextId() / 1000000L;
        try{
            payBudget.setId(id);
            payBudgetService.insertPayBudget(payBudget);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功");
    }

    /**
     * 新增总预算
     */
    @PostMapping(value = "/addAllBudget")
    public Result addAll(@RequestBody AllBudget allBudget)
    {
        try{
            payBudgetService.insertallPayBudget(allBudget.getUserId(),allBudget.getMonthBudget(),allBudget.getYearBudget());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功");
    }

    /**
     * 修改预算
     */
    @PutMapping
    public Result edit(@RequestBody PayBudget payBudget)
    {
        try{
            payBudgetService.updatePayBudget(payBudget);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功");
    }

    /**
     * 修改总预算
     */
    @PutMapping(value = "updateAllbudget")
    public Result editAllbudget(@RequestBody AllBudget allBudget)
    {
        try{
            payBudgetService.updateAllPayBudget(allBudget.getUserId(),allBudget.getMonthBudget(),allBudget.getYearBudget());
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功");
    }

    /**
     * 删除预算
     */
	@PostMapping("/remove/{id}")
    public Result remove(@PathVariable Long id)
    {
        try{
            payBudgetService.deletePayBudgetByUserId(id);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(HttpStatusCode.ERROR, "失败");
        }
        return new Result(HttpStatusCode.SUCCESS,"成功");
    }
}
