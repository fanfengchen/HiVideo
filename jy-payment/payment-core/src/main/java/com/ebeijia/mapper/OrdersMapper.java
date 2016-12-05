package com.ebeijia.mapper;

import com.ebeijia.entity.Orders;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import java.util.Map;

public interface OrdersMapper {

    List<Orders> selectAllResult(@Param("openId") String openId, @Param("orderType") String orderType);

    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    Orders selectByOrderNo(String orderNo);

    int updateOrderStatus(Map<String,Object> map);


    List<Orders> selectByNotPay();

    List<Orders> selectByToDayPaySuccesss();

    Orders selectByOrdersDeatil(String orderNo);


}