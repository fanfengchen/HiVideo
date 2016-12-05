package com.ebeijia.mapper;

import com.ebeijia.entity.UserBilAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserBilAccountMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserBilAccount record);

    int insertSelective(UserBilAccount record);

    UserBilAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserBilAccount record);

    int updateByPrimaryKey(UserBilAccount record);

    UserBilAccount selectByParams(@Param(value = "params")Map<String,Object> params);

    int selectCountByParams(@Param(value = "params") Map<String, Object> params);

    List<UserBilAccount> selectListByParams(@Param("params") Map<String, Object> params,
                                            @Param("orderParam") String orderParam,
                                            @Param("pageOffset") Integer pageOffset,
                                            @Param("pageSize") Integer pageSize);

    int updateAccountStatus(@Param("accountStatus") String accountStatus,
                            @Param("userId") String userId,
                            @Param("userNo") String userNo);

    int updateAccountTipsArrears(@Param("tipsArrears") String tipsArrears,
                                 @Param("userId") String userId,
                                 @Param("userNo") String userNo,
                                 @Param("id") Long id,
                                 @Param("accountStatus") String accountStatus);


    List<UserBilAccount> selectByTips(String tips);

    List<UserBilAccount> selectByUserNo(String userNo);

    int updateRemarkById(@Param("remark")String remark, @Param("id")Long id);
}