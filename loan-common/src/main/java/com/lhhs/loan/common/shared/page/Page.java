package com.lhhs.loan.common.shared.page;

import java.util.List;

public class Page {

	/* 当前页标 */
	private int pageIndex = 1;

	/* 当前页数据开始索引 */
	private int pageStartIndex;
	/* 当前页数据结束索引 */
	private int pageEndIndex;

	/* 分页大小 */
	private int pageSize = 10;

	/* 总分页数 */
	private int pageCount;

	/* 总数据量 */
	private int totalCount;
	
	/*开始页数*/
	private int startNum;
	
	/*结束页数*/
	private int endNum;

	/* 当前页数据查询url */
	private String url;
	
	/**
	 * 当前页上的结果列表
	 */
	private List<?> resultList;

	public Page() {

	};
	public Page(int pageIndex,int pageSize) {
		this.pageSize =  pageSize;
		setPageIndex(pageIndex);
	}

	public Page(int pageSize) {

		this.pageSize = pageSize;
	}

	public int getPageIndex() {

		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {

		if (pageIndex > 0) {
			this.pageIndex = pageIndex;
		}
		if (this.pageIndex > 1) {
			setPageStartIndex((pageIndex - 1) * pageSize);
		} else {
			setPageStartIndex(0);
		}
	}

	public void setPageIndex(String pageIndex) {

		if (pageIndex != null) {
			try {
				setPageIndex(Integer.parseInt(pageIndex));
			} catch (Exception e) {
			}
		}
	}

	public int getPageStartIndex() {

		return pageStartIndex;
	}

	public void setPageStartIndex(int pageStartIndex) {

		this.pageStartIndex = pageStartIndex;
	}

	public int getPageSize() {

		return pageSize;
	}

	public void setPageSize(int pageSize) {

		this.pageSize = pageSize;
		setPageIndex(this.pageIndex);
	}

	public int getPageCount() {

		return pageCount;
	}

	public void setPageCount(int pageCount) {

		this.pageCount = pageCount;
	}

	public int getTotalCount() {

		return totalCount;
	}

	public void setTotalCount(int totalCount) {

		this.totalCount = totalCount;
		this.pageCount = totalCount % pageSize == 0 ? totalCount / pageSize
				: totalCount / pageSize + 1;
		if (this.totalCount > 0 && this.pageCount > 0 && pageIndex > pageCount) {
			setPageIndex(pageCount);
		}
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public List<?> getResultList() {

		return resultList;
	}

	public void setResultList(List<?> resultList) {

		this.resultList = resultList;
	}

	public int getStartNum() {
		Integer[] num = PageCalcUtil.pageNum(pageIndex, pageCount);
		startNum = num[0];
		return startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public int getEndNum() {
		Integer[] num = PageCalcUtil.pageNum(pageIndex, pageCount);
		endNum = num[1];
		return endNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}
	public int getPageEndIndex() {
		this.pageEndIndex=this.pageStartIndex+this.pageSize;
		return pageEndIndex;
	}
	public void setPageEndIndex(int pageEndIndex) {
		this.pageEndIndex = pageEndIndex;
	}

}
