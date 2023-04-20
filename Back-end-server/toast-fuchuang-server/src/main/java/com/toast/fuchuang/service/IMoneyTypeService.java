package com.toast.fuchuang.service;

import com.toast.fuchuang.domain.MoneyType;
import com.toast.fuchuang.domain.Vo.MoneyTypeVo;

import java.util.List;
import java.util.Map;

public interface IMoneyTypeService {

    Map<String, Object> getTypeInfoByTypeId(Long typeId);

    List<MoneyTypeVo> getAllMoneyType(Long userId);

    int insertType(Long userId, MoneyType moneyType);

    int deleteType(Long userId, MoneyType moneyType);
}
