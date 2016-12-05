package com.ebeijia.mapper;

import com.ebeijia.entity.WxUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WxUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);

    //根据openid查询，判断是否有关注过
    WxUser queryWxUserByOpenId(@Param("openid") String openid);

    //用于分页，查询数据条数
    int selectCountByParams(@Param("params") Map<String, Object> params);

    //用于分页，查询数据
    List<WxUser> selectListByParams(@Param("params") Map<String, Object> params,
                                    @Param("orderParam") String orderParam,
                                    @Param("pageOffset") Integer pageOffset,
                                    @Param("pageSize") Integer pageSize);

}