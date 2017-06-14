package com.ebeijia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebeijia.common.RollPage;
import com.ebeijia.dto.VerSofttypeDto;
import com.ebeijia.mapper.VerSofttypeMapper;
import com.ebeijia.models.VerSofttype;

@Service
public class VerSofttypeServiceImpl implements VerSofttypeService {
	@Autowired
	private VerSofttypeMapper verSofttypeMapper;

	@Override
	public RollPage<VerSofttypeDto> findInfoList() {
		RollPage<VerSofttypeDto> rollPage = new RollPage<VerSofttypeDto>();
		List<VerSofttype> list = verSofttypeMapper.selectAllResult();
		if (null == list || list.isEmpty()) {
			rollPage.setResMsg("-2");
			return rollPage;
		}
		List<VerSofttypeDto> newList = new ArrayList<VerSofttypeDto>();
		for (VerSofttype verSofttype : list) {
			VerSofttypeDto typeDto = new VerSofttypeDto();
			typeDto.setId(verSofttype.getTypeId().toString());
			typeDto.setName(verSofttype.getName());
			typeDto.setAbout(verSofttype.getAbout());
			newList.add(typeDto);
		}
		rollPage.setRecordList(newList);
		rollPage.setResMsg("0");
		return rollPage;
	}

}
