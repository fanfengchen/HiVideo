package com.ebeijia.robot.modle.api;

import java.util.ArrayList;
import java.util.List;

public class AllocationUserList {

	private String count;
	private List<AllocationUserRes> list = new ArrayList<AllocationUserRes>();
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public List<AllocationUserRes> getList() {
		return list;
	}
	public void setList(List<AllocationUserRes> list) {
		this.list = list;
	}
}
