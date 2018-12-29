package com.lhhs.loan.common.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.esotericsoftware.minlog.Log;
import com.lhhs.loan.common.shared.constant.SystemConst;
import com.lhhs.loan.common.shared.constant.SystemConst.RepaymentMethod;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * 
 * @author ThinkGem
 * @version 2013-3-15
 */
public class DateUtils {
	/** 缺省日期格式 */
	public static final String DEFAULT_DATE_FMT = "yyyy-MM-dd";

	/** 缺省时间格式 */
	public static final String DEFAULT_TIME_FMT = "yyyy-MM-dd HH:mm:ss";

	
	/**北京时区*/
	public static final TimeZone timeZoneBeijing = TimeZone.getTimeZone("Asia/Shanghai");
	
	/** 全部时区名字 */
	private static final List TimeZoneIds = Arrays.asList(TimeZone.getAvailableIDs());

	/** 自定义时区缓存 */
	private static final Map TimeZoneCache = new HashMap();
	
	private static String[] parsePatterns = { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if(date == null){
			return null;
		}
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到某日具体天字符串 格式（dd）
	 */
	public static String getDay(Date date) {
		return Date2String(new Date(), "dd");
	}
	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}

	/**
	 * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
	 * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return org.apache.commons.lang3.time.DateUtils.parseDate(
					str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}


	/**
	 * 转换日期毫秒数为缺省日期格式字符串
	 *
	 * @param date
	 * @return
	 */
	public static String Date2String(long date) {
		return Date2String(new Date(date), DEFAULT_DATE_FMT, null);
	}

	/**
	 * 转换日期毫秒数为缺省日期格式字符串
	 *
	 * @param date
	 * @param timeZone
	 * @return
	 */
	public static String Date2String(long date, TimeZone timeZone) {
		return Date2String(new Date(date), DEFAULT_DATE_FMT, timeZone);
	}

	/**
	 * 转换日期为缺省日期格式字符串
	 *
	 * @param date
	 * @return
	 */
	public static String Date2String(Date date) {
		return Date2String(date, DEFAULT_DATE_FMT, null);
	}

	/**
	 * 转换日期为缺省日期格式字符串
	 *
	 * @param date
	 * @param timeZone
	 * @return
	 */
	public static String Date2String(Date date, TimeZone timeZone) {
		return Date2String(date, DEFAULT_DATE_FMT, timeZone);
	}

	/**
	 * 转换日期毫秒数为缺省日期格式字符串
	 *
	 * @param date
	 * @return
	 */
	public static String Time2String(long date) {
		return Date2String(new Date(date), DEFAULT_TIME_FMT, null);
	}

	/**
	 * 转换日期毫秒数为缺省日期格式字符串
	 *
	 * @param date
	 * @param timeZone
	 * @return
	 */
	public static String Time2String(long date, TimeZone timeZone) {
		return Date2String(new Date(date), DEFAULT_TIME_FMT, timeZone);
	}

	/**
	 * 转换日期为缺省日期格式字符串
	 *
	 * @param date
	 * @return
	 */
	public static String Time2String(Date date) {
		return Date2String(date, DEFAULT_TIME_FMT, null);
	}

	/**
	 * 转换日期为缺省日期格式字符串
	 *
	 * @param date
	 * @param timeZone
	 * @return
	 */
	public static String Time2String(Date date, TimeZone timeZone) {
		return Date2String(date, DEFAULT_TIME_FMT, timeZone);
	}

	/**
	 * 转换日期为指定格式字符串
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static String Date2String(Date date, String format) {
		return Date2String(date, format, null);

	}

	/**
	 * 转换日期为指定格式字符串
	 *
	 * @param date
	 * @param format
	 * @param timeZone
	 * @return
	 */
	public static String Date2String(Date date, String format, TimeZone timeZone) {
		if (date == null || format == null)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		if (timeZone != null)
			sdf.setTimeZone(timeZone);
		return sdf.format(date);

	}

	/**
	 * 解析日期时间字符串,支持 yyMMdd,yyyyMMdd, yyyy-MM-dd, yyyy/MM/dd, yyyyMMddHHmm,
	 * yyyyMMddHHmmss, yyyyMMddHHmmssSSS, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd HH:mm:ss.SSS 格式,
	 * 其它方式结果不保证正确
	 *
	 * @param str
	 * @return date
	 */
	public static Date String2Date(String str) {
		return String2Date(str, (TimeZone) null);
	}

	/**
	 * 解析日期时间字符串,支持 yyMMdd,yyyyMMdd, yyyy-MM-dd, yyyy/MM/dd, yyyyMMddHHmm,
	 * yyyyMMddHHmmss, yyyyMMddHHmmssSSS, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd HH:mm:ss.SSS, yyyyMMdd HH:mm:ss(add by zhongcf 20120220)格式,
	 * 其它方式结果不保证正确
	 *
	 * @param str
	 * @param timeZone
	 * @return date
	 */
	public static Date String2Date(String str, TimeZone timeZone) {
		if (str == null)
			return null;
		str = str.trim();
		if (str.length() == 6)
			return String2Date(str, "yyMMdd", timeZone);
		if (str.length() == 8)
			return String2Date(str, "yyyyMMdd", timeZone);
		if (str.length() == 10) {
			if (str.indexOf("-") != -1)
				return String2Date(str, "yyyy-MM-dd", timeZone);

			if (str.indexOf("/") != -1)
				return String2Date(str, "yyyy/MM/dd", timeZone);
		}
		if (str.length() == 12)
			return String2Date(str, "yyyyMMddHHmm", timeZone);
		if (str.length() == 14)
			return String2Date(str, "yyyyMMddHHmmss", timeZone);
		if (str.length() == 17)
			if(str.indexOf(":") != -1)
				return String2Date(str, "yyyyMMdd HH:mm:ss", timeZone);
			else
				return String2Date(str, "yyyyMMddHHmmssSSS", timeZone);
		if (str.length() == 19) {
			if (str.indexOf("-") != -1)
				return String2Date(str, "yyyy-MM-dd HH:mm:ss", timeZone);
			if (str.indexOf("/") != -1)
				return String2Date(str, "yyyy/MM/dd HH:mm:ss", timeZone);
		}
		if (str.length() == 23) {
			if (str.indexOf("-") != -1)
				return String2Date(str, "yyyy-MM-dd HH:mm:ss.SSS", timeZone);
			if (str.indexOf("/") != -1)
				return String2Date(str, "yyyy/MM/dd HH:mm:ss.SSS", timeZone);
		}
		try {
			return new SimpleDateFormat().parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 按指定方式解析日期时间
	 *
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date String2Date(String str, String format) {
		return String2Date(str, format, null);
	}

	/**
	 * 按指定方式解析日期时间
	 *
	 * @param str
	 * @param format
	 * @param timeZone
	 * @return
	 */
	public static Date String2Date(String str, String format, TimeZone timeZone) {
		if (str == null)
			return null;
		if (format == null)
			format = DEFAULT_DATE_FMT;
		SimpleDateFormat fmt = new SimpleDateFormat(format);
		if (timeZone != null)
			fmt.setTimeZone(timeZone);
		try {
			return fmt.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 比较两个日期是否是一天(不考虑时间)
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateEqual(Date date1, Date date2) {
		return truncDate(date1).equals(truncDate(date2));
	}

	/**
	 * 比较两个日期是否是一天(不考虑时间)
	 *
	 * @param date1
	 * @param date2
	 * @param timeZone
	 * @return
	 */
	public static boolean isDateEqual(Date date1, Date date2, TimeZone timeZone) {
		return truncDate(date1, timeZone).equals(truncDate(date2, timeZone));
	}

	/**
	 * 返回某日零时整
	 *
	 * @param date
	 * @return
	 */
	public static Date truncDate(Date date) {
		return truncDate(date, Calendar.DATE);
	}

	/**
	 * 返回某日零时整
	 *
	 * @param date
	 * @param timeZone
	 * @return
	 */
	public static Date truncDate(Date date, TimeZone timeZone) {
		return truncDate(date, Calendar.DATE, timeZone);
	}

	/**
	 * 日期时间取整，支持年、月、周、日、时、分、秒
	 *
	 * @param date
	 * @param mode
	 * @return date
	 */
	public static Date truncDate(Date date, int mode) {
		return truncDate(date, mode, null);
	}

	/**
	 * 日期时间取整，支持年、月、周、日、时、分、秒
	 *
	 * @param date
	 * @param mode
	 * @param timeZone
	 * @return date
	 */
	public static Date truncDate(Date date, int mode, TimeZone timeZone) {
		if (date == null)
			return null;
		Calendar cal = (timeZone == null ? Calendar.getInstance() : Calendar.getInstance(timeZone));
		cal.setTime(date);
		switch (mode) {
		case Calendar.YEAR:
			cal.clear(Calendar.MONTH);
		case Calendar.MONTH:
		case Calendar.WEEK_OF_MONTH:
			if (mode == Calendar.MONTH)
				cal.set(Calendar.DAY_OF_MONTH, 1);
			else
				cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		case Calendar.DATE:
			cal.set(Calendar.HOUR_OF_DAY, 0);
		case Calendar.HOUR:
			cal.clear(Calendar.MINUTE);
		case Calendar.MINUTE:
			cal.clear(Calendar.SECOND);
		case Calendar.SECOND:
			cal.clear(Calendar.MILLISECOND);
			break;
		default:
			throw new IllegalArgumentException();
		}
		return cal.getTime();
	}

	/**
	 * 循环调整时间
	 *
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date rollDate(Date date, int field, int amount) {
		return rollDate(date, field, amount, null);
	}

	/**
	 * 循环调整时间
	 *
	 * @param date
	 * @param field:1年份,2月份,3周,5天
	 * @param amount 加减值
	 * @param timeZone
	 * @return
	 */
	public static Date rollDate(Date date, int field, int amount, TimeZone timeZone) {
		Calendar cal = (timeZone == null ? Calendar.getInstance() : Calendar.getInstance(timeZone));
		cal.setTime(date);
		cal.roll(field, amount);
		return cal.getTime();
	}

	/**
	 * 调整时间
	 *
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static Date addDate(Date date, int field, int amount) {
		return addDate(date, field, amount, null);
	}

	/**
	 * 调整时间
	 *
	 * @param date
	 * @param field
	 * @param amount
	 * @param timeZone
	 * @return
	 */
	public static Date addDate(Date date, int field, int amount, TimeZone timeZone) {
		Calendar cal = (timeZone == null ? Calendar.getInstance() : Calendar.getInstance(timeZone));
		cal.setTime(date);
		cal.add(field, amount);
		return cal.getTime();
	}
	/**
	 * 调整小时
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHours(Date date, int hour) {
		return addDate(date,Calendar.HOUR,hour);
	}
	/**
	 * 比较2个同时区时间先后，注意:时间的格式必须在String2Date支持的格式范围内
	 *
	 * @param date1 时间1
	 * @param date2 时间2
	 * @return 如果时间1等于时间2，返回0，如果时间1小于时间2，返回负值，如果时间1大于时间2，返回正值
	 */
	public static int compare(String date1, String date2) {
		return String2Date(date1).compareTo(String2Date(date2));
	}

	/**
	 * 比较2个时间先后
	 *
	 * @param date1 时间1
	 * @param date2 时间2
	 * @return 如果时间1等于时间2，返回0，如果时间1小于时间2，返回负值，如果时间1大于时间2，返回正值
	 */
	public static int compare(Date date1, Date date2) {
		return date1.compareTo(date2);
	}

	/**
	 * 查询时区
	 *
	 * @param timediff
	 * @return
	 */
	public static TimeZone findTimeZone(int timediff) {
		String[] ids = TimeZone.getAvailableIDs(timediff * 60000 + TimeZone.getDefault().getOffset(System.currentTimeMillis()));
		if (ids == null)
			return new SimpleTimeZone(timediff * 60000, "UDT");
		return TimeZone.getTimeZone(ids[0]);
	}

	/**
	 * 根据时区名字取得时区
	 * 如果非java已知标准名字，则必须为 GMT[+-]hh:mm 格式
	 * @param id
	 */
	public static TimeZone getTimeZone(String id) {
		if (id == null)
			return null;
		if (TimeZoneIds.contains(id))
			return TimeZone.getTimeZone(id);
		if (TimeZoneCache.containsKey(id))
			return (TimeZone) TimeZoneCache.get(id);
		Pattern p = Pattern.compile("^GMT[+-](0[0-9]|1[01]):([0-5][0-9])$");
		Matcher m = p.matcher("id");
		if (!m.matches())
			return null;
		int hh = Integer.parseInt(id.substring(4, 6));
		int mm = Integer.parseInt(id.substring(7));
		int sign = (id.charAt(3) == '-' ? -1 : 1);
		TimeZone tz = new SimpleTimeZone((hh * 60 + mm) * 60000 * sign, id);
		TimeZoneCache.put(id, tz);
		return tz;
	}

	public static Timestamp string2TimeStamp(Object millions, Object nanos) {
		try {
			Timestamp res = new Timestamp(Long.parseLong((String) millions));
			res.setNanos(Integer.parseInt((String) nanos));

			return res;
		} catch (Exception e) {
			return null;
		}

	}


	/**
	 * <p>
	 * 把用户当地时间转成网银时间。
	 * </p>
	 *
	 * @param date 待转换的时间。
	 * @param dest 用户所在时区。
	 * @return 转换后的时间。
	 */
	public static Date transformDateFrom(Date date, TimeZone dest) {

//		long offset = dest.getOffset(date.getTime()) - timeZoneBeijing.getOffset(date.getTime());
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(date.getTime() - offset);
//		return cal.getTime();
		return transformDate(date, dest, timeZoneBeijing);
	}

	/**
	 * <p>
	 * 把网银时间转成用户当地时间。
	 * </p>
	 *
	 * @param date 待转换的时间。
	 * @param dest 用户所在时区。
	 * @return 转换后的时间。
	 */
	public static Date transformDateInto(Date date, TimeZone dest) {
//		long offset = dest.getOffset(date.getTime()) - timeZoneBeijing.getOffset(date.getTime());
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(date.getTime() + offset);
//		return cal.getTime();
		return transformDate(date, timeZoneBeijing, dest);
	}
	
	private static Date transformDate(Date dateSrc, TimeZone src, TimeZone dest) {
		Calendar cal = Calendar.getInstance(dest);
		cal.setTimeInMillis(dateSrc.getTime());
		int yy = cal.get(Calendar.YEAR);
		int MM = cal.get(Calendar.MONTH);
		int dd = cal.get(Calendar.DATE);
		int HH = cal.get(Calendar.HOUR_OF_DAY);
		int mm = cal.get(Calendar.MINUTE);
		int ss = cal.get(Calendar.SECOND);
		int sss = cal.get(Calendar.MILLISECOND);
		Calendar calBJ = Calendar.getInstance(src);
		calBJ.set(Calendar.YEAR, yy);
		calBJ.set(Calendar.MONTH, MM);
		calBJ.set(Calendar.DATE, dd);
		calBJ.set(Calendar.HOUR_OF_DAY, HH);
		calBJ.set(Calendar.MINUTE, mm);
		calBJ.set(Calendar.SECOND, ss);
		calBJ.set(Calendar.MILLISECOND, sss);
		return calBJ.getTime();
	}

	/**
	 * 校验起始日期和结束日期的合法性<p>
	 * 例如：起始日期距当前日期不超过12个月，起始结束日期间隔不超过3个月，调用<br>
	 * validateDateRange(startDate, endDate, currentDate, 3, 12)
	 * 
	 * @param startDate 起始日期
	 * @param endDate   结束日期
	 * @param currentDate 当前日期
	 * @param maxInterval 起始日期和结束日期的最大距离（单位为月）
	 * @param amount      起始日期和当前日期的最大距离（单位为月）
	 * @return
	 */
	public static boolean validateDateRange(Date startDate, Date endDate, Date currentDate, int maxInterval, int amount) {
		if (startDate.after(endDate))
			return false;

		if (currentDate.after(addDate(startDate, Calendar.MONTH, amount)))
			return false;

		if (endDate.after(addDate(startDate, Calendar.MONTH, maxInterval)))
			return false;

		return true;
	}
	  /**  
   * 计算两个日期之间相差的天数  
   * @param smdate 较小的时间 
   * @param bdate  较大的时间 
   * @return 相差天数 
   * @throws ParseException  
   */    
  public static int daysBetween(Date smdate,Date bdate)     
  {    
      SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); 
      //日期取整，去掉小时分钟秒
	    try {
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate)); 
		} catch (ParseException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
			
		}  
      
      Calendar cal = Calendar.getInstance();    
      cal.setTime(smdate);    
      long time1 = cal.getTimeInMillis();                 
      cal.setTime(bdate); 
      long time2 = cal.getTimeInMillis();         
      long between_days=(time2-time1)/(1000*3600*24);  
          
     return Integer.parseInt(String.valueOf(between_days));           
  }  
  
  /**  
* 计算两个日期之间相差的天数  
* @param smdate 较小的时间 
* @param bdate  较大的时间 
* @return 相差天数 
* @throws ParseException  
*/    
public static int minuteSBetween(Date smdate,Date bdate)     
{    
  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
  //日期取整，去掉小时分钟秒
    try {
		smdate=sdf.parse(sdf.format(smdate));
		bdate=sdf.parse(sdf.format(bdate)); 
	} catch (ParseException e) {
		
		// TODO Auto-generated catch block
		e.printStackTrace();
		return 0;
		
	}  
  
  Calendar cal = Calendar.getInstance();    
  cal.setTime(smdate);    
  long time1 = cal.getTimeInMillis();                 
  cal.setTime(bdate); 
  long time2 = cal.getTimeInMillis();         
  long between_days=(time2-time1)/(1000*60);  
      
 return Integer.parseInt(String.valueOf(between_days));           
}
	   /**
     * 字符串的日期格式的计算
     * @param smdate 较小的时间 
     * @param bdate  较大的时间 
     * @return 相差天数 
     * @throws ParseException
     */
    public static int daysBetween(String startdate,String enddate){ 
    	
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date smdate=String2Date(startdate);
        Date bdate=String2Date(enddate);
        //日期取整，去掉小时分钟秒
        try {
			smdate=sdf.parse(sdf.format(smdate));
			 bdate=sdf.parse(sdf.format(bdate));  
		} catch (ParseException e) {
			e.printStackTrace();
		}  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
       return Integer.parseInt(String.valueOf(between_days));     
    } 
	/**
	 * 取得指定日期是星期几
	 * @param date
	 * @return 1:星期日,2:星期一,3:星期二,4:星期三,5:星期四,6:星期五,7:星期六
	 */
    public static int getWeek(Date date){  
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};  
        Calendar cal = Calendar.getInstance();  
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);  
    } 
    
	/**
	 * 根据放款日、期限和单位及时借款截止日
	 * @param date
	 * @return 截止日
	 */
    public static Date getlendingOverTime(Date startDate,int term,String termUnit){  
    	  //取零时
        Date lendingTime=DateUtils.truncDate(startDate);
        Date over_date=null;
        if(SystemConst.LoanTermUnit.DAY.equals(termUnit)){
        	over_date=DateUtils.addDate(lendingTime, Calendar.DAY_OF_MONTH, term);
        }else{
        	over_date=DateUtils.addDate(lendingTime, Calendar.MONTH, term);
        }
        String start_day=getDay(lendingTime);
        String over_day=getDay(over_date);
        if(!start_day.equals(over_day)&&!SystemConst.LoanTermUnit.DAY.equals(termUnit)){
        	over_date=DateUtils.addDate(over_date, Calendar.DAY_OF_MONTH, 1);
        }
        over_date=DateUtils.addDate(over_date, Calendar.SECOND, -1);
        return over_date; 
    }
    
	/**
	 * 根据还款方式、期限、期限单位、放款时间获取每期开始时间和结束时间
	 * @param repaymentMethod 还款方式
	 * @param term 期数
	 * @param termUnit 期数单位
	 * @param lendingTime 放款时间
	 * @return
	 * @throws ParseException
	 */
	public static List<Map<String, Date>> getStartEndTimeList(String repaymentMethod,Short term,String termUnit,Date lendingTime,Date appointDate) {
		SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		
		List<Map<String, Date>> list = new ArrayList<>();
		try {
			if (appointDate==null) {
				appointDate = new Date();
			}
			String strLendingTime = sdf2.format(lendingTime);//放款时间去掉时分秒
			String strAppointDate = sdf2.format(appointDate);//指定日时间去掉时分秒
			lendingTime = sdf1.parse(strLendingTime+" 00:00:00");//格式化放款时间
			appointDate = sdf1.parse(strAppointDate+" 23:59:59");//格式化指定日时间
			
			Calendar startTime = Calendar.getInstance();//开始时间
			startTime.setTime(lendingTime);
			int startTimeDay = startTime.get(Calendar.DAY_OF_MONTH);//获取开始时间是那一天
			
			Calendar endTime = Calendar.getInstance();
			
			if (SystemConst.RepaymentMethod.ONCE_PAYMENT.equals(repaymentMethod)) {
				//一次性支付
				Map<String, Date> map = new HashMap<String, Date>();
				map.put("start", startTime.getTime());
				endTime.setTime(lendingTime);
				if (SystemConst.LoanTermUnit.MONTH.equals(termUnit)) {
					//期数单位为月
					endTime.add(Calendar.MONTH,term);//到期时间
					int endTimeDay = endTime.get(Calendar.DAY_OF_MONTH);//获取到期时间是那一天
					endTime.add(Calendar.SECOND, -1);
					if(endTimeDay != startTimeDay){
						endTime.add(Calendar.DAY_OF_MONTH, 1);
					}
				}else if (SystemConst.LoanTermUnit.DAY.equals(termUnit)) {
					//期数单位为天
					endTime.add(Calendar.DAY_OF_MONTH,term);//到期时间
					endTime.add(Calendar.SECOND, -1);
				}
				map.put("appoint", appointDate);
				map.put("end", endTime.getTime());
				list.add(map);
			}else if (RepaymentMethod.MONTHLY_INTERSETS.equals(repaymentMethod)) {
				//每月还息到期还本
				Calendar appointTime = Calendar.getInstance();//结息/付息指定日初始化
				appointTime.setTime(appointDate);
				Date appointTimeStart = appointTime.getTime();
				
				if (SystemConst.LoanTermUnit.MONTH.equals(termUnit)) {
					Date start = startTime.getTime();//每期开始时间
					for (int i = 0; i < term; i++) {
						startTime.setTime(lendingTime);
						startTime.add(Calendar.MONTH, i+1);
						int endTimeDay = startTime.get(Calendar.DAY_OF_MONTH);
						endTime.setTime(startTime.getTime());//每期结束时间
						endTime.add(Calendar.SECOND, -1);
						if(endTimeDay != startTimeDay){//处理特殊日期
							endTime.add(Calendar.DAY_OF_MONTH, 1);
						}
						Map<String, Date> map = new HashMap<String, Date>();
						map.put("start", start);
						map.put("end", endTime.getTime());
						map.put("appoint", appointTime.getTime());
						list.add(map);
						
						endTime.add(Calendar.SECOND, 1);
						start = endTime.getTime();//下一期开始时间
						appointTime.setTime(appointTimeStart);//下一期指定计息日
						appointTime.add(Calendar.MONTH, i+1);
					}
					Map<String, Date> lastMap = list.get(list.size()-1);
					Date naturalEnd = lastMap.get("end");//最后一期自然月结束时间
					lastMap.put("naturalEnd",naturalEnd);
				}else if (SystemConst.LoanTermUnit.DAY.equals(termUnit)) {
					Date startDate = startTime.getTime();
					Calendar overTime = Calendar.getInstance();//结束时间
					overTime.setTime(lendingTime);
					overTime.add(Calendar.DAY_OF_MONTH,term);
					overTime.add(Calendar.SECOND,-1);
					Date overDate = overTime.getTime();
					int newTerm = 0;//重新计算新的期数
					while (overDate.getTime()>startDate.getTime()) {
						startTime.add(Calendar.MONTH, 1);//开始日期每次加月和结束时间比较
						startDate = startTime.getTime();
						newTerm++;
					}
					startTime.setTime(lendingTime);//开始时间
					Date start = startTime.getTime();
					for (int i = 0; i < newTerm; i++) {
						startTime.setTime(lendingTime);
						startTime.add(Calendar.MONTH, i+1);
						int endTimeDay = startTime.get(Calendar.DAY_OF_MONTH);
						endTime.setTime(startTime.getTime());
						endTime.add(Calendar.SECOND, -1);//结束时间
						if(endTimeDay != startTimeDay){//处理特殊日期
							endTime.add(Calendar.DAY_OF_MONTH, 1);
						}
						Map<String, Date> map = new HashMap<String, Date>();
						map.put("start", start);
						map.put("end", endTime.getTime());
						map.put("appoint", appointTime.getTime());
						list.add(map);
						
						endTime.add(Calendar.SECOND, 1);
						start = endTime.getTime();//下一期开始时间
						appointTime.setTime(appointTimeStart);//下一期指定计息日
						appointTime.add(Calendar.MONTH, i+1);
					}
					//处理最后一期结束时间
					Map<String, Date> lastMap = list.get(list.size()-1);
					Date naturalEnd = lastMap.get("end");//最后一期自然月结束时间
					Date lastAppoint = lastMap.get("appoint");
					//最后一期结束时间
					lastMap.put("end",overDate);
					lastMap.put("naturalEnd",naturalEnd);
					//处理最后一期计息日指定时间
					if (overDate.getTime()<lastAppoint.getTime()) {
						lastMap.put("appoint",overDate);
					}
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	/**
	 * 返回当前时间前n周，n月，n天
	 * @param timeUnit 时间单位 
	 * @return
	 */
	public static Date getTime(String timeUnit){
	     Calendar c = Calendar.getInstance();
		 if("月".equals(timeUnit)){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			 //过去11个月
		    c.setTime(new Date());
	        c.add(Calendar.MONTH, -11);
	        Date m3 = c.getTime();
	        String mon3 = format.format(m3);
	        System.out.println("过去11个月："+mon3);
	        try {
				return format.parse(mon3);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 }else if("周".equals(timeUnit)){
			 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			 //过去21天
			 Log.info("按周计算，当前时间为："+format.format(new Date()));
			 c.setTime(new Date());
			 String week = DateUtils.getWeek();
			 Log.info("本周是周几："+week);
			 if("星期日".equals(week)|| "Sun".equals(week)){
				 c.add(Calendar.DATE, - 27);
				 Log.info("当前时间减去："+27);
			 }else if("星期一".equals(week)|| "Mon".equals(week)){
				 c.add(Calendar.DATE, - 21);
				 Log.info("当前时间减去："+21);
			 }else if("星期二".equals(week)|| "Tue".equals(week)){
				 c.add(Calendar.DATE, - 22);
				 Log.info("当前时间减去："+22);
			 }else if("星期三".equals(week)|| "Wed".equals(week)){
				 c.add(Calendar.DATE, - 23);
				 Log.info("当前时间减去："+23);
			 }else if("星期四".equals(week)|| "Thu".equals(week)){
				 c.add(Calendar.DATE, - 24);
				 Log.info("当前时间减去："+24);
			 }else if("星期五".equals(week)|| "Fri".equals(week)){
				 c.add(Calendar.DATE, - 25);
				 Log.info("当前时间减去："+25);
			 }else if("星期六".equals(week)|| "Sat".equals(week)){
				 c.add(Calendar.DATE, - 26);
				 Log.info("当前时间减去："+26);
			 }
		     Date d = c.getTime();
		     String day = format.format(d);
		     Log.info("从本周的周一开始，过去三周的时间为："+day);
		     System.out.println("过去三周："+day);
		     try {
				return format.parse(day);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 }else if("日".equals(timeUnit)){
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		    //过去6天
	        c.setTime(new Date());
	        c.add(Calendar.DATE, - 6);
	        Date d = c.getTime();
	        String day = format.format(d);
	        System.out.println("过去6天："+day);
	        try {
				return format.parse(day);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 }
		return null;
	 }
	
	
	
	/**
	 * 返回当前时间前n周，n月，n天
	 * @param timeUnit 时间单位 
	 * @return
	 */
	public static Date getTime2(String week){
		 Calendar c = Calendar.getInstance();
		 c.setTime(new Date());
		 if("星期日".equals(week)|| "Sun".equals(week)){
			 c.add(Calendar.DATE, - 6);
			 Log.info("当前时间减去："+6);
		 }else if("星期一".equals(week)|| "Mon".equals(week)){
			 c.add(Calendar.DATE, - 0);
			 Log.info("当前时间减去："+0);
		 }else if("星期二".equals(week)|| "Tue".equals(week)){
			 c.add(Calendar.DATE, - 1);
			 Log.info("当前时间减去："+1);
		 }else if("星期三".equals(week)|| "Wed".equals(week)){
			 c.add(Calendar.DATE, - 2);
			 Log.info("当前时间减去："+2);
		 }else if("星期四".equals(week)|| "Thu".equals(week)){
			 c.add(Calendar.DATE, - 3);
			 Log.info("当前时间减去："+3);
		 }else if("星期五".equals(week)|| "Fri".equals(week)){
			 c.add(Calendar.DATE, - 4);
			 Log.info("当前时间减去："+4);
		 }else if("星期六".equals(week)|| "Sat".equals(week)){
			 c.add(Calendar.DATE, - 5);
			 Log.info("当前时间减去："+5);
		 }
		 //第一周 
		 Date week1 = c.getTime();
		 return week1;
	 }
	
	/**
	 * @param args
	 * @throws ParseException
	 */
	public static void main(String[] args) throws ParseException {
		// System.out.println(formatDate(parseDate("2010/3/6")));
		// System.out.println(getDate("yyyy年MM月dd日 E"));
		// long time = new Date().getTime()-parseDate("2012-11-19").getTime();
		// System.out.println(time/(24*60*60*1000));
	}
	
	/**  
	 * 计算两个时间之间相差的秒数  
	 * @param smdate 较小的时间 
	 * @param bdate  较大的时间 
	 * @return 相差秒数 
	 * @throws ParseException  
	 */    
	public static float secondSBetween(Date smdate,Date bdate){    
		Calendar cal = Calendar.getInstance();    
		cal.setTime(smdate);    
		long time1 = cal.getTimeInMillis();                 
		cal.setTime(bdate); 
		long time2 = cal.getTimeInMillis();         
		long between_seconds=(time2-time1)/1000;  
		float f = Float.parseFloat(String.valueOf(between_seconds));
		BigDecimal b = new BigDecimal(f);  
		float f1 = b.setScale(3, BigDecimal.ROUND_HALF_UP).floatValue(); //四舍五入，保留两位小数
		return f1;
	}
	
	/**  
	 * 月份加减
	 * @return Date
	 * @throws ParseException  
	 */    
	public static Date addMonth(Date today,int num){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();    
		cal.setTime(today);   
		cal.add(Calendar.MONTH, num);
		Date m=cal.getTime();
		return m;
	}
	
}
