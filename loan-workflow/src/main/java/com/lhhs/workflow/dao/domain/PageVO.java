package com.lhhs.workflow.dao.domain;

public class PageVO  {
	private static final long serialVersionUID = 1L;
	private long total = 0; // 总记录数
	private int pageNum = 1; // 第几页
	private int pageSize = 10; // 每页记录数
	private int pages; // 总页数
	private int size; // 当前页的数量 <= pageSize，该属性来自ArrayList的size属性
	private String orderBy = ""; // 标准查询有效， 实例： updatedate desc, name asc
	private String sort;
	private String order;

	private String sqlPageStart = "";
	private String sqlPageEnd = "";

	public String getSqlPageStart() {
		return "";
		
	}

	public String getSqlPageEnd() {
		
		return "limit " + getStartRow() + "," + this.getPageSize();
		
	}

	public PageVO() {

	}

	public PageVO(int nowpage, int rows) {
		this.pageNum = nowpage;
		this.pageSize = rows;
	}

	public PageVO(int total, int nowpage, int rows) {
		this.total = total;
		this.pageNum = nowpage;
		this.pageSize = rows;
	}

	public PageVO(int nowpage, int rows, String sort, String order) {
		this.pageNum = nowpage;
		this.pageSize = rows;
		this.sort = sort;
		this.order = order;
	}

	public int getStartRow() {
		return (pageNum - 1) * pageSize;
	}

	public int getEndRow() {
		return pageNum * pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getOrderBy() {
		if (sort == null || sort.equals("")) {
			sort = "id";
		}
		if (order == null || order.equals("")) {
			order = "desc";
		}
		return " order by " + sort + " " + order;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		if (sort != null) {
			return sort.replace("_view_name", "");
		} else {
			return sort;
		}
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

}
