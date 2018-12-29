package com.lhhs.loan.common.shared.zip;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class AnalyseTransExcel {
	
	
	private static final Logger logger = LoggerFactory
	        .getLogger(AnalyseTransExcel.class);
	/**
	 * excel 每一个sheet工作表显示的记录数
	 */
	private static int counts = 10000;
	/**
	 * 下载excel
	 * @param request
	 * @param os
	 * @param type
	 */
	@SuppressWarnings(value={"all"})
	public static void downLoadExcel(HttpServletRequest request,
			 HttpServletResponse response,
			 String filename,
			 List list,
			 Class clazz,
			 Map<String,String> titles){
		logger.info("下载开始");
		int length = 0;//sheet工作表的个数
		List result = null;//临时list
		if(list!=null){
			//记录的总个数
			length = list.size();
		}
		//计算出需要几个sheet工作表
		length = (length%counts==0)?length/counts:(length/counts)+1;
		//要解析的实体类
		Object tempObject=null;
	    try {
	    	//得到需要的实体类型
	    	tempObject = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e1) {
			e1.printStackTrace();
			logger.error("下载错误");
		}
		Date tempDate = null;//临时date类型
		ByteArrayOutputStream outStream=new ByteArrayOutputStream();
		OutputStream os = outStream;
		try {
				if(list!=null && list.size()>0){
					//创建一个excel
					HSSFWorkbook workBook=new HSSFWorkbook();
					for(int k=0;k<length;k++){
						int tempk = (k+1)*counts;
						if(tempk<=list.size()){
							//result = getSubList(k*counts,(k+1)*counts,list);
							result = new ArrayList(list.subList(k*counts, (k+1)*counts));
						}else{
							//result = getSubList(k*counts,list.size(),list);
							result = new ArrayList(list.subList(k*counts,list.size()));
						}
						//创建测试表,每个sheet的名称需要不同
						HSSFSheet sheet = workBook.createSheet("sheet"+k);
						sheet.createFreezePane(0, 1, 0, 1);
						//创建标题行
						HSSFRow headRow=sheet.createRow(0);
						//创建单元格列，生成标题行
						int valueIndex = 0;
						for(Map.Entry<String, String> entry : titles.entrySet()) {  
				             headRow.createCell(valueIndex).setCellValue(entry.getValue());
							 valueIndex++;
				        }  
						/*for (String value : titles.values()) {
							headRow.createCell(valueIndex).setCellValue(value);
							valueIndex++;
						}*/
						HSSFRow row=null;
						//生成每条记录
						for(int i=0;i<result.size();i++){
							sheet.setColumnWidth(i, 30*256);
							tempObject=result.get(i);
							row=sheet.createRow(i+1);
							if(tempObject!=null){
								if(tempObject instanceof Map){
									int keyIndex = 0;
									for (String key : titles.keySet()) {
										Object newObeject = ((Map) tempObject).get(key);
										if(newObeject!=null){
											//如果是时间类型
											if(newObeject instanceof Date){
												tempDate = (Date) newObeject;
												row.createCell(keyIndex).setCellValue(dateToStr(tempDate));
											}else if(newObeject instanceof String){
												row.createCell(keyIndex).setCellValue(newObeject.toString());
											}else {
												row.createCell(keyIndex).setCellValue(newObeject.toString());
											}
										}else{
											row.createCell(keyIndex).setCellValue("");
										}
										keyIndex++;
									}
								}else{
									int keyIndex = 0;
									for (String key : titles.keySet()) {
										Object newObeject = invokeMethod(key, tempObject);
										if(newObeject!=null){
											//如果是时间类型
											if(newObeject instanceof Date){
												tempDate = (Date) newObeject;
												row.createCell(keyIndex).setCellValue(dateToStr(tempDate));
											}else if(newObeject instanceof String){
												row.createCell(keyIndex).setCellValue(newObeject.toString());
											}else {
												row.createCell(keyIndex).setCellValue(newObeject.toString());
											}
										}else{
											row.createCell(keyIndex).setCellValue("");
										}
										keyIndex++;
									}
								}
								
							}else{
								System.out.println("数据为空!");
							}
						}
				}
				workBook.write(os);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出失败");
		}
		
		//浏览器响应处理
		responseProcessing(request, response, filename, outStream);
	}
	
	
	 /**
	  * 响应处理
	  * @param request
	  * @param response
	  * @param filename
	  * @param outStream
	  */
	 public static void responseProcessing(HttpServletRequest request,
			 HttpServletResponse response,
			 String filename,
			 ByteArrayOutputStream outStream){
		 try {
				response.setContentType("application/vnd.ms-excel");
				filename = toUtf8String(filename,request);
				response.setHeader("Content-Disposition", "inline;filename="+filename+".xls");
				response.getOutputStream().write(outStream.toByteArray());
				response.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		 
	 };
	 
	 public static String toUtf8String(String filename,HttpServletRequest request){ 
		 String agent = request.getHeader("User-Agent"); 
			boolean isFireFox = (agent != null && agent.toLowerCase().indexOf("firefox") != -1); 
			try {
				if (isFireFox) {
					filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");
				} else {
					filename = URLEncoder.encode(filename, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
			}
		  return filename;
	}
	 
	 
	// 调用对象方法
	public static Object invokeMethod(String attriName, Object obj)
			throws Exception {
		String methodName = getMethodName(attriName);
		Method method = obj.getClass().getMethod(methodName);
		Object value = method.invoke(obj, null);
		return value;
	}

	public static String getMethodName(String attriName) {
		String first = attriName.charAt(0) + "";
		first = first.trim().toUpperCase();
		attriName = attriName.substring(1);
		attriName = "get" + first + attriName;
		return attriName;
	}
	
	
	public static String dateToStr(Date time){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = null;
		if(time!=null){
			result=sdf.format(time);
		}else{
			result=sdf.format(new Date());
		}
		return result;
	}
	
	/*//截取list
	@SuppressWarnings("all")
	public static List getSubList(int start,int end,List list){
		List result = new ArrayList();
		while (start<end) {
			result.add(list.get(start));
			start++;
		}
		return result;
	};*/
}
