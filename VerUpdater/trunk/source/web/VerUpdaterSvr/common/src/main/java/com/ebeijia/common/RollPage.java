/**
 * 
 */
package com.ebeijia.common;

import java.util.List;

import com.ebeijia.dto.VerSoftDto;

/**
 * 分页对象类
 * 
 * @author lijm
 * 
 */
public class RollPage<T> implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Integer DEFAULTPAGESIZE = 20;

	private Integer pageSize = 0;

	private Integer pageNum = 1;

	private Integer pageSum = 0;

	private Integer recordSum = 0;

	private String resMsg;
	
	private boolean success;//失败，成功
	
	private VerSoftDto verSoftDto;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	private List<T> recordList;

	private List<T> footer;

	public List<T> getFooter() {
		return footer;
	}

	public void setFooter(List<T> footer) {
		this.footer = footer;
	}

	public RollPage() {
		pageSize = DEFAULTPAGESIZE;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		if (pageNum > 0) {
			this.pageNum = pageNum;
		}
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageSum() {
		if (this.pageSum == 0) {
			this.pageSum = (int) Math.ceil(this.recordSum / this.pageSize)
					+ ((this.recordSum % this.pageSize == 0) ? 0 : 1);
		}
		return pageSum;
	}

	public void setPageSum(Integer pageSum) {
		this.pageSum = pageSum;
	}

	public Integer getRecordSum() {
		return recordSum;
	}

	public void setRecordSum(Integer recordSum) {
		this.recordSum = recordSum;
	}

	public List<T> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<T> recordList) {
		this.recordList = recordList;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	
	public VerSoftDto getVerSoftDto() {
		return verSoftDto;
	}

	public void setVerSoftDto(VerSoftDto verSoftDto) {
		this.verSoftDto = verSoftDto;
	}

}
