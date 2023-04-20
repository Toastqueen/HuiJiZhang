package com.toast.fuchuang.service.impl;

import cn.hutool.core.util.IdUtil;
import com.toast.fuchuang.domain.MoneyType;
import com.toast.fuchuang.domain.RTypeUser;
import com.toast.fuchuang.domain.Vo.MoneyTypeVo;
import com.toast.fuchuang.mapper.MoneyTypeMapper;
import com.toast.fuchuang.service.IMoneyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 金额类型有关Service业务层处理
 *
 * @author toast
 * @date 2023-02-28
 */
@Service
public class MoneyTypeServiceImpl implements IMoneyTypeService {

    @Autowired
    private MoneyTypeMapper moneyTypeMapper;

    /**
     * 根据类型id查询类型信息
     * @param typeId
     * @return
     */
    @Override
    public Map<String, Object> getTypeInfoByTypeId(Long typeId){

        Map<String, Object> map = moneyTypeMapper.selectMoneyTypeInfoById(typeId);
        if (map==null) {
            System.out.println("查找用户新建类型");
            return moneyTypeMapper.selectMoneyNewTypeInfoById(typeId);
        }else
          return map;
    }

    /**
     * 查询用户当前可操作金额类型
     * @param userId
     * @return
     */
    @Override
    public  List<MoneyTypeVo> getAllMoneyType(Long userId){


        List<MoneyTypeVo> list = new ArrayList<>();
        List<MoneyType> typeList = moneyTypeMapper.selectMoneyTypeList(userId);
        System.out.println("all:"+typeList);
        List<MoneyType> newAddTypeList = moneyTypeMapper.selectMoneyTypeNewAddList(userId);
        System.out.println("xin"+newAddTypeList);

        for (MoneyType moneyType: typeList) {
            MoneyTypeVo moneyTypeVo = new MoneyTypeVo(moneyType);
            list.add(moneyTypeVo);
            
        }
        for (MoneyType moneyType: newAddTypeList) {
            MoneyTypeVo moneyTypeVo = new MoneyTypeVo(moneyType);
            list.add(moneyTypeVo);
        }
        return list;
    }

    /**
     * 新增类型
     * @param userId
     * @param moneyType
     * @return
     */
    @Override
    public int insertType(Long userId, MoneyType moneyType){
        //只保留前12位的雪花id
        Long typeId = IdUtil.getSnowflakeNextId() / 1000000L;

        RTypeUser rTypeUser = new RTypeUser(moneyType,typeId,true,userId);
        return moneyTypeMapper.insertType(rTypeUser);

    }

    /**
     * 删除类型
     * @param userId
     * @param moneyType
     * @return
     */
    @Override
    public int deleteType(Long userId, MoneyType moneyType){


        boolean flag = true;
        for (Long typeId : moneyTypeMapper.getAllTypeId()) {
            if (typeId==moneyType.getTypeId()){

                Map<String, Object> info = moneyTypeMapper.selectMoneyTypeInfoById(moneyType.getTypeId());
                System.out.println(info);
                moneyType.setContentsType(String.valueOf(info.get("contents_type")));
                moneyType.setTypeName(String.valueOf(info.get("type_name")));
                moneyType.setRecordType(String.valueOf(info.get("record_type")));
                moneyType.setParentId((Long) info.get("parent_id"));


                RTypeUser rTypeUser = new RTypeUser(moneyType,typeId,false,userId);
                return moneyTypeMapper.insertType(rTypeUser);
            }
        }
        return moneyTypeMapper.updateTypeFlagFalse(new RTypeUser(moneyType,moneyType.getTypeId(),false,userId));
    }

}
