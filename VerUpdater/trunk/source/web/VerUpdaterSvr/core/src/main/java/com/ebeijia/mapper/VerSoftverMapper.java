package com.ebeijia.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ebeijia.models.MdiFileinfo;
import com.ebeijia.models.VerSoftver;

public interface VerSoftverMapper {
	//查询满足查询条件的数据数
	int selectCountByParams(@Param("chnlId")String chnlId,@Param("typeId")String typeId);
	// 查询所有数据
	List<VerSoftver> selectByPrimarys(@Param("chnlId") String chnlId,
			@Param("typeId") String typeId,
			@Param("pageOffset") int pageOffset,
			@Param("pageSize") int pageSize);
	int deleteByPrimaryKey(Integer softId);

	int insert(VerSoftver record);

	int insertSelective(VerSoftver record);
	
	 VerSoftver selectByPrimaryKey(@Param("sofId")Integer sofId);
	 List<MdiFileinfo> selectpreviewImgUrl(@Param("sofId")Integer sofId);
	 List<VerSoftver> selectBysoftId(@Param("name")String name,
			 								@Param("pageOffset")int pageOffset,
			 								@Param("pageSize")int pageSize);
	int updateByPrimaryKeySelective(VerSoftver record);

	int updateByPrimaryKey(VerSoftver record);
}