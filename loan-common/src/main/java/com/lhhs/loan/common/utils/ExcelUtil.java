package com.lhhs.loan.common.utils;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class ExcelUtil {
	 /**
     * 得到Excel表中的值
     * 
     * @param XSSFCell
     *            Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */

	public static String getValue(XSSFCell cell) {
		
    	if(cell==null)return null;
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(cell.getBooleanCellValue()).trim();
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
        	
        	if(HSSFDateUtil.isCellDateFormatted(cell)){
        		return DateUtils.Date2String(cell.getDateCellValue());
        	}else{
        		// 返回数值类型的值,设置单元格为字符型
        		cell.setCellType(cell.CELL_TYPE_STRING);
                return String.valueOf(cell.getStringCellValue()).trim();
        	}
            
        } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
            // 公式型
            return String.valueOf(cell.getCellFormula()).trim();
        } else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {
            // 空值 
            return null;
        }else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue()).trim();
        }
    }
	
	/**
     * 得到Excel表中的值
     * 
     * @param XSSFCell
     *            Excel中的每一个格子
     * @return Excel中每一个格子中的值
     */
	public static String getValue(Cell cell) {
		
    	if(cell==null)return null;
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(cell.getBooleanCellValue()).trim();
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
        	
        	if(HSSFDateUtil.isCellDateFormatted(cell)){
        		return DateUtils.Date2String(cell.getDateCellValue());
        	}else{
        		// 返回数值类型的值,设置单元格为字符型
        		cell.setCellType(cell.CELL_TYPE_STRING);
                return String.valueOf(cell.getStringCellValue()).trim();
        	}
            
        } else if (cell.getCellType() == cell.CELL_TYPE_FORMULA) {
            // 公式型
            return String.valueOf(cell.getCellFormula()).trim();
        } else if (cell.getCellType() == cell.CELL_TYPE_BLANK) {
            // 空值 
            return null;
        }else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue()).trim();
        }
    }
	
	
	
	
	

	/**
	 * 设置单元格的值
	 * @param row 单元格所在的行
	 * @param num 第几个单元格，从0开始
	 * @param obj 要设置的值
	 */
	public static void setCellValue(XSSFRow row,int num,Object obj) {
		if (obj == null)return ;
		if (obj instanceof String){
			row.createCell(num).setCellValue((String)obj);
		}else if (obj instanceof BigDecimal){
			row.createCell(num).setCellValue(((BigDecimal)obj).doubleValue());
		}else if (obj instanceof Date){
			row.createCell(num).setCellValue(DateUtils.Date2String((Date)obj));
		}else{
			row.createCell(num).setCellValue(obj.toString());
		}
			
	}
	/**
	 * 设置单元格的值
	 * @param row 单元格所在的行
	 * @param num 第几个单元格，从0开始
	 * @param obj 要设置的值
	 */
	public static void setCellValue(Row row,int num,Object obj) {
		if (obj == null)return ;
		if (obj instanceof String){
			row.createCell(num).setCellValue((String)obj);
		}else if (obj instanceof BigDecimal){
			row.createCell(num).setCellValue(((BigDecimal)obj).doubleValue());
		}else if (obj instanceof Date){
			row.createCell(num).setCellValue(DateUtils.Date2String((Date)obj));
		}else{
			row.createCell(num).setCellValue(obj.toString());
		}
			
	}
}
