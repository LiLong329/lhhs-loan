package com.lhhs.loan.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;


/**
 * 
 * @author yangyang
 * 时间：2016年9月25日
 * 类说明：服务业务工具类
 */
public class UtilTools {
	/**
	 * 或许UUID
	 * @return
	 */
	public static String getUUId(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/**
	 * 获取bean名称以及spring加载的bean文件，在spring容器中获取bean实例
	 * @param fileName 文件名
	 * @param beaname bean id
	 * @return
	 */
	public static Object getBean(String fileName,String beaname){
	   	 String path = ApplicationConfigUtil.class.getClassLoader().getResource("config/spring/"+fileName).getPath();
	   	 ApplicationContext context = new FileSystemXmlApplicationContext(path);
	   	 return context.getBean(beaname);
	    }
	/**
	 * 或许系统内配置文件的属性值
	 * @param filename 配置文件名称
	 * @param propertityName 属性名称
	 * @return
	 */
	public static String getPropertityValue( String filename, String propertityName ){
		String result = null;
		Properties prop = new Properties();
		InputStream in = UtilTools.class.getResourceAsStream( "/" + filename + ".properties" );
		try {
			prop.load(in);
			result = prop.getProperty( propertityName, "" );
			
			
		}
		catch (Exception e) {			
		}
		finally{
			if( in != null ){
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		
		return result;
		
	}
	/**
	 * 动态设置配置文件属性值
	 * @param filename 文件名
	 * @param propertityName 属性名
	 * @param value
	 */
	public static void setPropertityValue( String filename, String propertityName, String value ){
		Properties prop = new Properties();
		
		URL propUrl = UtilTools.class.getResource( "/" + filename + ".properties" );
		
		if( propUrl != null ){
			File file = new File( propUrl.getPath() );		
			
			if( file.exists() ){
				FileInputStream fis = null; 
				FileOutputStream fos = null;
				try {
					fis = new FileInputStream( file );			
					prop.load(fis);
					prop.setProperty( propertityName, value );
					if( fis != null ){
						try {
							fis.close();
						} catch (IOException e) {
						}
					}
					fos = new FileOutputStream( file );
					prop.store( fos, "" );
				}
				catch (Exception e) {			
				}
				finally{
					if( fos != null ){
						try {
							fos.close();
						} catch (IOException e) {
						}
					}
				}
			}
		}
	}
	
	/**
	 * 判断是否来自移动端
	 * @param request
	 * @return
	 */
	public static boolean JudgeIsMoblie(HttpServletRequest request) {
		boolean isMoblie = false;
		String[] mobileAgents = { "iphone", "android", "phone", "mobile", "wap", "netfront", "java", "opera mobi",
				"opera mini", "ucweb", "windows ce", "symbian", "series", "webos", "sony", "blackberry", "dopod",
				"nokia", "samsung", "palmsource", "xda", "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
				"docomo", "up.browser", "up.link", "blazer", "helio", "hosin", "huawei", "novarra", "coolpad", "webos",
				"techfaith", "palmsource", "alcatel", "amoi", "ktouch", "nexian", "ericsson", "philips", "sagem",
				"wellcom", "bunjalloo", "maui", "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
				"pantech", "gionee", "portalmmm", "jig browser", "hiptop", "benq", "haier", "^lct", "320x320",
				"240x320", "176x220", "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq", "bird", "blac",
				"blaz", "brew", "cell", "cldc", "cmd-", "dang", "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs",
				"kddi", "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo", "midp", "mits", "mmef", "mobi",
				"mot-", "moto", "mwbp", "nec-", "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play", "port",
				"prox", "qwap", "sage", "sams", "sany", "sch-", "sec-", "send", "seri", "sgh-", "shar", "sie-", "siem",
				"smal", "smar", "sony", "sph-", "symb", "t-mo", "teli", "tim-", /*"tosh",*/ "tsm-", "upg1", "upsi", "vk-v",
				"voda", "wap-", "wapa", "wapi", "wapp", "wapr", "webc", "winw", "winw", "xda", "xda-",
				"Googlebot-Mobile" };
		if (request.getHeader("User-Agent") != null) {
			for (String mobileAgent : mobileAgents) {
				if (request.getHeader("User-Agent").toLowerCase().indexOf(mobileAgent) >= 0) {
					isMoblie = true;
					break;
				}
			}
		}
		return isMoblie;
	}
	
	/**
	 * 把键值对放入session中
	 * @param request
	 * @param key
	 * @param value
	 */
	public static void putAttrInSession(HttpServletRequest request,String key,Object value){
		request.getSession().setAttribute(key, value);
	}
	
	/**
	 * 从session中使用键取出值
	 * @param request
	 * @param key
	 * @return
	 */
	public static Object getAttrFromSession(HttpServletRequest request,String key){
		Object obj = request.getSession().getAttribute(key);
		return obj;
	}
	
	/**
	 * 从session中移除 所记录的键值对
	 * @param request
	 * @param keys
	 */
	public static void removeFromSession(HttpServletRequest request,String ... keys){
		HttpSession session = request.getSession();
		for (String oneOfKey : keys) {
			session.removeAttribute(oneOfKey);
		}
	}
	
	/**
	 * 将当前登录的平台账户放入session中
	 * @param request
	 * @param platAccount
	 */
	/*public static void setCurrentUser(HttpServletRequest request,ProviderInfo user){
		putAttrInSession(request, systemConst.CURRENT_LOGIN_USER, user);
	}*/
	
	/**
	 * 获取当前登录的用户的平台账户
	 * @param request
	 * @param platAccount
	 * @return
	 */
	/*public static ProviderInfo getCurrentUser(HttpServletRequest request){
		return (ProviderInfo)getAttrFromSession(request, systemConst.CURRENT_LOGIN_USER);
	}*/
	/**
	 * 获取客户端IP
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || "0:0:0:0:0:0:0:1".equals(ipAddress))  {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	/**
	 * 判断是否为ip格式
	 * @param addr ip 地址
	 * @return
	 */
	public static boolean isIP(String addr)
    {
      if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
      {
        return false;
      }
      /**
       * 判断IP格式和范围
       */
      String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
      
      Pattern pat = Pattern.compile(rexp);  
      
      Matcher mat = pat.matcher(addr);  
      
      boolean ipAddress = mat.find();

      return ipAddress;
    }
	
}
