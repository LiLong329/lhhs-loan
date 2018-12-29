package com.lhhs.loan.common.utils;

public class NumberOfStringAdd {

	public static void main(String[] args) {
		String result = stringAdd("9999999999999999.99999", "0.00002");
		System.out.println(result);
	}

	public static String stringAdd(String data1, String data2) {
		String xiaoshuresult = "";
		String zhengshuresult = "";
		int sum = 0;
		int jinwei = 0;
		String xiaoshu1 = "";
		String zhengshu1 = "";
		String xiaoshu2 = "";
		String zhengshu2 = "";
		if (data1.indexOf(".") > 0) {
			xiaoshu1 = data1.substring(data1.indexOf(".") + 1, data1.length());
			zhengshu1 = data1.substring(0, data1.indexOf("."));
		} else {
			zhengshu1 = data1;
		}
		if (data2.indexOf(".") > 0) {
			xiaoshu2 = data2.substring(data2.indexOf(".") + 1, data2.length());
			zhengshu2 = data2.substring(0, data2.indexOf("."));
		} else {
			zhengshu2 = data2;
		}
		int xslength = xiaoshu1.length() > xiaoshu2.length() ? xiaoshu1
				.length() : xiaoshu2.length();
		for (int j = xslength - 1; j >= 0; j--) {
			int str1 = getIndexNum(xiaoshu1, j);
			int str2 = getIndexNum(xiaoshu2, j);
			sum = str1 + str2 + jinwei;
			jinwei = 0;
			if (sum >= 10) {
				sum -= 10;
				jinwei = 1;
			}
			xiaoshuresult = sum + xiaoshuresult;
		}
		xiaoshuresult = "." + xiaoshuresult;

		int zslength = zhengshu1.length() > zhengshu2.length() ? zhengshu1
				.length() : zhengshu2.length();
		for (int j = 0; j < zslength; j++) {
			int str1 = getIndexNum(zhengshu1, zhengshu1.length() - j - 1);
			int str2 = getIndexNum(zhengshu2, zhengshu2.length() - j - 1);
			sum = str1 + str2 + jinwei;
			jinwei = 0;
			if (sum >= 10) {
				sum -= 10;
				jinwei = 1;
			}
			zhengshuresult = sum + zhengshuresult;
		}
		if (jinwei == 1) {
			zhengshuresult = jinwei + zhengshuresult;
		}

		if (zhengshuresult.length() > 0) {
			zhengshuresult = zhengshuresult + xiaoshuresult;
		} else {
			zhengshuresult = "0" + xiaoshuresult;
		}
		return zhengshuresult;
	}

	public static int getIndexNum(String str, int index) {
		int indexNum = 0;
		if (str.length() > index && index >= 0) {
			indexNum = Integer.parseInt(str.substring(index, index + 1));
		}
		return indexNum;
	}
}
