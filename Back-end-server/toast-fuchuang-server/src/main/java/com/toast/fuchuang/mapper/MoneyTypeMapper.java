package com.toast.fuchuang.mapper;

import com.toast.fuchuang.domain.MoneyType;
import com.toast.fuchuang.domain.RTypeUser;
import com.toast.fuchuang.domain.Vo.MoneyTypeVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MoneyTypeMapper {
    /**
     * 根据typeId查询该类型信息 主表
     * @param typeId
     * @return
     */
    Map<String, Object> selectMoneyTypeInfoById(@Param("typeId")Long typeId);

    /**
     * 根据类型名查询类型id
     * @param typeName
     * @return typeId
     */
    Long selectMoneyTypeIdByName(@Param("typeName")String typeName);
    /**
     * 根据typeId查询用户新创建的类型信息 关联表
     * @param typeId
     * @return
     */
    Map<String, Object> selectMoneyNewTypeInfoById(@Param("typeId")Long typeId);

    /**
     * 查出用户目前可操作的金额类型 去除用户删除的
     * @param userId
     * @return
     */
     List<MoneyType> selectMoneyTypeList(@Param("userId")Long userId);

    /**
     * 获取用户增加的金额类型
     * @param userId
     * @return
     */
     List<MoneyType> selectMoneyTypeNewAddList(@Param("userId")Long userId);
    /**
     * 新增可见类型
     */
     int insertType(RTypeUser rTypeUser);
    /**
     *  更改设置类型 (flag=增加/删除)
     * @param rTypeUser
     * @return
     */
    int updateTypeFlagFalse(RTypeUser rTypeUser);

    List<Long> getAllTypeId();
}
