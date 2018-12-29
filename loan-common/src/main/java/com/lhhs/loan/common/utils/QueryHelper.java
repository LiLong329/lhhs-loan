package com.lhhs.loan.common.utils;

import java.util.Iterator;
import java.util.Map;


public class QueryHelper {
	private static final String DEFAULT_ORDERBY_PLACE = "%%%ORDERBY%%%";
	private static final String ROWNUMFIELD = "rn_0192837465";

	/**
	 * 该函数用户拼装查询语句，把原始的查询和order by语句分离，主要为了提升count(*)的性能。
	 * 
	 * @param originalSQL
	 *            original query SQL
	 * @param paramMaps
	 *            参数值Map
	 * @param conditionMaps
	 *            条件脚本
	 * @return
	 */
	public static String createQuerySQL(String originalSQL, Map paramsMap,
			Map conditionsMap) {
		if (originalSQL == null)
			return null;

		StringBuffer sqlBuffer = new StringBuffer(originalSQL);

		if (conditionsMap != null) {
			Iterator it = conditionsMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry entry = (Map.Entry) it.next();
				String paramName = (String) entry.getKey();
				Object obj=entry.getValue();
				String paramValue = null;
				if (paramsMap != null) {
					paramValue = (String) paramsMap.get(paramName);
				}
				paramValue =(paramValue == null)?"":paramValue.trim();
				paramValue=paramValue.replaceAll("\\?","");
				String paramScript =null;
				if(obj!=null && Map.class.isAssignableFrom(obj.getClass())){
	                Map whereMap=(Map)obj;
	                for(Iterator ita=whereMap.keySet().iterator();ita.hasNext();){
	                	String key=(String)ita.next();
	                	if(paramName.equals(key) || paramValue.equals(key)){
						   paramScript=(String)whereMap.get(key);
						   break;
	                	}
	                }
				}else{
					paramScript = (String)obj;
				}
				String placeSign = new StringBuffer("[").append(
						paramName.toUpperCase()).append("]").toString();
				int iParamePos = sqlBuffer.toString().toUpperCase().indexOf(placeSign);
				while (iParamePos >= 0) {
					int iPlaceSignLen = placeSign.length();
					if (paramScript!=null && paramValue.length()>0) {
						int iPos = 0;
						StringBuffer buffer = new StringBuffer(paramScript);
						while(iPos != -1){
							if(iPos>0){ 
								paramValue = ByteUtils.filter(paramValue, '\'', "''");
								paramValue = StrUtils.replace(paramValue, "&quot;","'");
								buffer.replace(iPos, iPos + 1, paramValue);	
							}
							iPos = buffer.toString().indexOf("?");
						}
						sqlBuffer.replace(iParamePos, iParamePos
								+ iPlaceSignLen, buffer.toString());
					} else {
						sqlBuffer.replace(iParamePos, iParamePos
								+ iPlaceSignLen, "1=1");
					}
					iParamePos = sqlBuffer.toString().toUpperCase().indexOf(placeSign);
				}
			}
		}
		return sqlBuffer.toString();
	}

	/**
	 * 去除查询语句中的"%%%ORDERBY%%%，生成查询总数的效率比较高的脚本
	 * 
	 * @param strSQL
	 * @return
	 */
	public static String evictOrderByInfo(String strSQL) {
		return replaceOrderByInfo(strSQL, "");
	}

	/**
	 * 把查询语句中的order by占位符替换为实际的order by语句
	 * 
	 * @param strSQL
	 * @param orderBySQL
	 * @return
	 */
	public static String replaceOrderByInfo(String strSQL, String orderBySQL) {
		
		String tempOrderBySQL = orderBySQL;
		if (tempOrderBySQL==null){
			tempOrderBySQL = "";
		}
		
		String tempOrderByPlace = DEFAULT_ORDERBY_PLACE.toUpperCase();
		StringBuffer sqlBuffer = new StringBuffer(strSQL);

		int iParamePos = sqlBuffer.toString().toUpperCase().indexOf(
				tempOrderByPlace);

		while (iParamePos >= 0) {
			int iPlaceSignLen = tempOrderByPlace.length();
			sqlBuffer.replace(iParamePos, iParamePos + iPlaceSignLen,
					tempOrderBySQL);
			iParamePos = sqlBuffer.toString().indexOf(tempOrderByPlace);
		}
		return sqlBuffer.toString();
	}
	
	/**
	 * 实现oracle的分页查询的语句
	 * @param originalSql
	 * @param rowCountPerPage
	 * @param totalRows
	 * @param page
	 * @return
	 */
	public static String parsePageOracleSQL(String originalSql, int rowCountPerPage,int totalRows, int page) {
		int iBegin = 0;
		int iEnd = 0;
		int pageCount = 0;

		if (rowCountPerPage > 0 && totalRows > rowCountPerPage) {
			if (totalRows % rowCountPerPage == 0)
				pageCount = totalRows / rowCountPerPage;
			else
				pageCount = totalRows / rowCountPerPage + 1;

			if (page < 1)
				page = 1;
			if (page > pageCount) {
				page = pageCount;
			}
			iBegin = (page - 1) * rowCountPerPage + 1;
			iEnd = iBegin + rowCountPerPage;

			StringBuffer tmpBuffer = new StringBuffer();
			tmpBuffer.append("SELECT * FROM (SELECT originTable.*, ROWNUM ");
			tmpBuffer.append(ROWNUMFIELD);
			tmpBuffer.append(" FROM (");
			tmpBuffer.append(originalSql);
			tmpBuffer.append(")");
			tmpBuffer.append(" originTable ");
			
			//syp 屏蔽，解决翻页数据错误的问题
			/*if (iEnd <= totalRows) {
				tmpBuffer.append(" WHERE ROWNUM<");
				tmpBuffer.append(Integer.toString(iEnd));
			}*/

			tmpBuffer.append(") WHERE ");
			tmpBuffer.append(ROWNUMFIELD);
			tmpBuffer.append(">=");
			tmpBuffer.append(Integer.toString(iBegin));
			if (iEnd <= totalRows) {
				tmpBuffer.append(" and ");
				tmpBuffer.append(ROWNUMFIELD);
				tmpBuffer.append("<");
				tmpBuffer.append(Integer.toString(iEnd));
			}

			originalSql = tmpBuffer.toString();
		}else{
			StringBuffer tmpBuffer = new StringBuffer();
			tmpBuffer.append("SELECT * FROM (SELECT originTable.*, ROWNUM ");
			tmpBuffer.append(ROWNUMFIELD);
			tmpBuffer.append(" FROM (");
			tmpBuffer.append(originalSql);
			tmpBuffer.append(")");
			tmpBuffer.append(" originTable )");
			originalSql = tmpBuffer.toString();
		}
		return originalSql;
	}
	
	/**
	 * 实现mysql的分页查询的语句
	 * @param originalSql
	 * @param rowCountPerPage
	 * @param totalRows
	 * @param page
	 * @return
	 */
	public static String parsePageMysqlSQL(String originalSql, int rowCountPerPage,int totalRows, int page) {
		int iBegin = 0;
		int pageCount = 0;

		if (rowCountPerPage > 0 && totalRows > rowCountPerPage) {
			if (totalRows % rowCountPerPage == 0)
				pageCount = totalRows / rowCountPerPage;
			else
				pageCount = totalRows / rowCountPerPage + 1;

			if (page < 1)
				page = 1;
			if (page > pageCount) {
				page = pageCount;
			}
			iBegin = (page - 1) * rowCountPerPage ;

			StringBuffer tmpBuffer = new StringBuffer();
			tmpBuffer.append("SELECT originTable.* ");
			tmpBuffer.append(" FROM (");
			tmpBuffer.append(originalSql);
			tmpBuffer.append(")");
			tmpBuffer.append(" originTable ");
			tmpBuffer.append("limit ");
			tmpBuffer.append(Integer.toString(iBegin));
			tmpBuffer.append(",");
			tmpBuffer.append(rowCountPerPage);

			originalSql = tmpBuffer.toString();
		}
		return originalSql;
	}
}
