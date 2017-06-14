package com.ebeijia.robot.modle.api;

import java.util.ArrayList;
import java.util.List;

public class DictDataListRes {

	private String count;
	private List<DictDataRes> list = new ArrayList<DictDataRes>();
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<DictDataRes> getList() {
		return list;
	}
	public void setList(List<DictDataRes> list) {
		this.list = list;
	}
	
}
