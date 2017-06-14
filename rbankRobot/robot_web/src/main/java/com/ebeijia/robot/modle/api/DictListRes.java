package com.ebeijia.robot.modle.api;

import java.util.ArrayList;
import java.util.List;

public class DictListRes {
	
	private String count;
	private List<DictRes> list = new ArrayList<DictRes>();
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<DictRes> getList() {
		return list;
	}
	public void setList(List<DictRes> list) {
		this.list = list;
	}
	
}
