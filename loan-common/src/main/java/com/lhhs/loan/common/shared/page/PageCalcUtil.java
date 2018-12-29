package com.lhhs.loan.common.shared.page;

import javax.servlet.http.HttpServletRequest;

public class PageCalcUtil {

	/**
	 * 计算显示当前分页的起始页
	 * 
	 * @param pageNum
	 *            当前页码
	 * @param pageCount
	 *            总页数
	 * @param sideNum
	 *            分页系数 分页条中显示几个数字页码。 显示数字页码个数 = 2 * sideNum + 1
	 */
	public static void calcPage(int sideNum, HttpServletRequest request) {
		Integer pageNum = Integer.valueOf(request.getAttribute("pageNum") + "");
		Integer pageCount = Integer.valueOf(request.getAttribute("pageCount")
				+ "");

		int startNum = 0;
		int endNum = 0;

		if (pageCount <= sideNum) {
			endNum = pageCount;
		} else {
			if ((sideNum + pageNum) >= pageCount) {
				endNum = pageCount;
			} else {
				endNum = sideNum + pageNum;
				if ((sideNum + pageNum) <= (2 * sideNum + 1)) {
					if ((2 * sideNum + 1) >= pageCount) {
						endNum = pageCount;
					} else {
						endNum = 2 * sideNum + 1;
					}
				} else {
					endNum = sideNum + pageNum;
				}
			}
		}

		if (pageNum <= sideNum) {
			startNum = 1;
		} else {
			if ((pageNum + sideNum) >= pageCount) {
				if ((2 * sideNum + 1) >= pageCount) {
					if ((pageCount - 2 * sideNum) >= 1) {
						startNum = pageCount - 2 * sideNum;
					} else {
						startNum = 1;
					}
				} else {
					startNum = pageCount - 2 * sideNum;
				}
			} else {
				if ((pageNum - sideNum) >= 1) {
					startNum = pageNum - sideNum;
				} else {
					startNum = 1;
				}
			}
		}
		request.setAttribute("startNum", startNum);
		request.setAttribute("endNum", endNum);
	}

	/**
	 * 计算页码
	 * 
	 * @param pageNum
	 *            当前页
	 * @param pageCount
	 *            总页数
	 * @return
	 */
	public static Integer[] pageNum(int pageNum, int pageCount) {
		int startNum = 0;
		int endNum = 0;
		int sideNum = 4;// 分页系数 分页条中显示几个数字页码。 显示数字页码个数 = 2 * sideNum + 1
		if (pageCount <= sideNum) {
			endNum = pageCount;
		} else {
			if ((sideNum + pageNum) >= pageCount) {
				endNum = pageCount;
			} else {
				endNum = sideNum + pageNum;
				if ((sideNum + pageNum) <= (2 * sideNum + 1)) {
					if ((2 * sideNum + 1) >= pageCount) {
						endNum = pageCount;
					} else {
						endNum = 2 * sideNum + 1;
					}
				} else {
					endNum = sideNum + pageNum;
				}
			}
		}

		if (pageNum <= sideNum) {
			startNum = 1;
		} else {
			if ((pageNum + sideNum) >= pageCount) {
				if ((2 * sideNum + 1) >= pageCount) {
					if ((pageCount - 2 * sideNum) >= 1) {
						startNum = pageCount - 2 * sideNum;
					} else {
						startNum = 1;
					}
				} else {
					startNum = pageCount - 2 * sideNum;
				}
			} else {
				if ((pageNum - sideNum) >= 1) {
					startNum = pageNum - sideNum;
				} else {
					startNum = 1;
				}
			}
		}
		Integer[] page = new Integer[2];
		page[0] = startNum;
		page[1] = endNum;
		return page;
	}

}
