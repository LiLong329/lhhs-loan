package com.lhhs.loan.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期计算工具类
 * 2014年12月14日
 * @author rocky
 *
 */
public class CalendarUtil {

    /**
     * 获取指定日期的当周第一天日期
     *
     * @param date 指定日期
     * @return 当月第一天日期
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); //获取本周一的日期
        return calendar.getTime();
    }
    
    /**
     * 获取指定日期的上周第一天日期
     *
     * @param date 指定日期
     * @return 当月第一天日期
     */
    public static Date getFirstDayOfPreviousWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        calendar.add(Calendar.DATE, -1*7);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        return calendar.getTime();
    }
    
    /**
     * 获取指定日期的当月第一天日期
     *
     * @param date 指定日期
     * @return 当月第一天日期
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1); // 设置当前月的1号
        return calendar.getTime();
    }
    
    /**
     * 获取指定日期的上周最后一天日期
     *
     * @param date 指定日期
     * @return 当月第一天日期
     */
    public static Date getLastDayOfPreviousWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        calendar.add(Calendar.DATE, -1*7);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        return calendar.getTime();
    }
    
    
    
    /**
     * 获取指定日期当月最后一天日期
     *
     * @param date 指定日期
     * @return 当月最后一天日期
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);     // 设置当前月的1号
        calendar.add(Calendar.MONTH, 1);   // 加一个月，变为下月的1号
        calendar.add(Calendar.DATE, -1);    // 减去一天，变为当前月的最后一天
        return calendar.getTime();
    }

    /**
     * 获取指定日期上月第一天日期
     *
     * @param date 指定日期
     * @return 上月第一天日期
     */
    public static Date getFirstDayOfPreviousMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);     // 设置当前月的1号
        calendar.add(Calendar.MONTH, -1);  // 减一个月，变为上月的1号
        return calendar.getTime();
    }

    /**
     * 获取指定日期的上月最后一天
     *
     * @param date 指定日期
     * @return 上月最后一天日期
     */
    public static Date getLastDayOfPreviousMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);     // 设置当前月的1号
        calendar.add(Calendar.DATE, -1);    // 减一天，变为上月的1号
        return calendar.getTime();
    }

    /**
     * 获取指定日期的下月第一天日期
     *
     * @param date 指定日期
     * @return 下月第一天日期
     */
    public static Date getFirstDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);    // 加一个月
        calendar.set(Calendar.DATE, 1);     // 设置当前月第一天
        return calendar.getTime();
    }

    /**
     * 获取指定日期下月最后一天的日期
     *
     * @param date 指定日期
     * @return 下月最后一天的日期
     */
    public static Date getLastDayOfNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);     // 设置当前月的1号
        calendar.add(Calendar.MONTH, 2);   // 加两个月，变为下下月的1号
        calendar.add(Calendar.DATE, -1);    // 减一天，变为下月的最后一天
        return calendar.getTime();
    }

    /**
     * 获取两个日期相差天数
     * @param fromDate
     * @param endDate
     * @return 相差天数
     */
    public static int getIntervalDays(Date fromDate, Date endDate) {
        if (null == fromDate || null == endDate) {
            return -1;
        }
        long intervalMilli = endDate.getTime() - fromDate.getTime();
        return (int) (intervalMilli / (24 * 60 * 60 * 1000));
    }
    
    /**
     * 获取指定周数第一天早上日期
     *
     * @param date 指定日期
     * @return 当月第一天日期
     */
    public static Date getFirstOfSpecifiedWeek(Date date,Integer flag) {
    	date.setHours(0);
    	date.setMinutes(1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        calendar.add(Calendar.DATE, flag*7);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
        return calendar.getTime();
    }
    
    /**
     * 获取指定周数最后一天晚上日期
     *
     * @param date 指定日期
     * @return 当月第一天日期
     */
    public static Date getLastOfSpecifiedWeek(Date date,Integer flag) {
    	date.setHours(23);
    	date.setMinutes(59);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);//将每周第一天设为星期一，默认是星期天
        calendar.add(Calendar.DATE, flag*7);
        calendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        return calendar.getTime();
    }
}
