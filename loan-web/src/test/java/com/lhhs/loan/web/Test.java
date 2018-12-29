package com.lhhs.loan.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.constant.SystemConst.RepaymentMethod;


public class Test {

	public static void main(String[] args) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse("2016-12-31 00:00:00");
		Test aa = new Test();
		List<Map<String, Date>> list = aa.getStartEndTimeList("2", (short)10, "天", date);
		for (int i = 0; i < list.size(); i++) {
			Map<String, Date> map = list.get(i);
			String cc = dateFormat.format(map.get("start"));
			String dd = dateFormat.format(map.get("end"));
			System.out.println(cc+"-------------"+dd);
		}
	}
	/**
	 * 获取每期开始时间和结束时间
	 * @param repaymentMethod 还款方式
	 * @param term 期数
	 * @param termUnit 期数单位
	 * @param lendingTime 放款时间
	 * @return
	 * @throws ParseException
	 */
	public List<Map<String, Date>> getStartEndTimeList(String repaymentMethod,Short term,String termUnit,Date lendingTime) throws ParseException {
		
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		String strLendingTime = sdf2.format(lendingTime);//放款时间去掉时分秒
		Date dateLendingTime = sdf1.parse(strLendingTime+" 00:00:00");//格式化放款时间
		
		Calendar startTime = Calendar.getInstance();//开始时间
		startTime.setTime(dateLendingTime);
		int startTimeDay = startTime.get(Calendar.DAY_OF_MONTH);//获取开始时间是那一天
		
		Calendar endTime = Calendar.getInstance();
		
		List<Map<String, Date>> list = new ArrayList<>();
		if (SystemConst.RepaymentMethod.ONCE_PAYMENT.equals(repaymentMethod)) {
			Map<String, Date> map = new HashMap<String, Date>();
			map.put("start", startTime.getTime());
			endTime.setTime(dateLendingTime);
			if (SystemConst.LoanTermUnit.MONTH.equals(termUnit)) {
				endTime.add(Calendar.MONTH,term);
				int endTimeDay = endTime.get(Calendar.DAY_OF_MONTH);
				endTime.add(Calendar.SECOND, -1);
				if(endTimeDay != startTimeDay){
					endTime.add(Calendar.DAY_OF_MONTH, 1);
				}
			}else if (SystemConst.LoanTermUnit.DAY.equals(termUnit)) {
				endTime.add(Calendar.DAY_OF_MONTH,term+1);
				endTime.add(Calendar.SECOND, -1);
			}
			map.put("end", endTime.getTime());
			list.add(map);
		}else if (RepaymentMethod.MONTHLY_INTERSETS.equals(repaymentMethod)) {
			if (SystemConst.LoanTermUnit.MONTH.equals(termUnit)) {
				Date start = startTime.getTime();
				for (int i = 0; i < term; i++) {
					startTime.setTime(dateLendingTime);
					startTime.add(Calendar.MONTH, i+1);
					int endTimeDay = startTime.get(Calendar.DAY_OF_MONTH);
					endTime.setTime(startTime.getTime());
					endTime.add(Calendar.SECOND, -1);
					if(endTimeDay != startTimeDay){
						endTime.add(Calendar.DAY_OF_MONTH, 1);
					}
					Map<String, Date> map = new HashMap<String, Date>();
					map.put("start", start);
					map.put("end", endTime.getTime());
					list.add(map);
					endTime.add(Calendar.SECOND, 1);
					start = endTime.getTime();
				}
			}else if (SystemConst.LoanTermUnit.DAY.equals(termUnit)) {
				Date startDate = startTime.getTime();
				
				Calendar overTime = Calendar.getInstance();//结束时间
				overTime.setTime(dateLendingTime);
				overTime.add(Calendar.DAY_OF_MONTH,term);
				Date overDate = overTime.getTime();
				
				int newTerm = 0;//重新计算新的期数
				while (overDate.getTime()>startDate.getTime()) {
					startTime.add(Calendar.MONTH, 1);//开始日期每次加月和结束时间比较
					startDate = startTime.getTime();
					newTerm++;
				}
				startTime.setTime(dateLendingTime);
				Date start = startTime.getTime();
				for (int i = 0; i < newTerm; i++) {
					startTime.setTime(dateLendingTime);
					startTime.add(Calendar.MONTH, i+1);
					int endTimeDay = startTime.get(Calendar.DAY_OF_MONTH);
					endTime.setTime(startTime.getTime());
					endTime.add(Calendar.SECOND, -1);
					if(endTimeDay != startTimeDay){
						endTime.add(Calendar.DAY_OF_MONTH, 1);
					}
					Map<String, Date> map = new HashMap<String, Date>();
					map.put("start", start);
					map.put("end", endTime.getTime());
					list.add(map);
					endTime.add(Calendar.SECOND, 1);
					start = endTime.getTime();
				}
			}
		}
		return list;
	}
}
