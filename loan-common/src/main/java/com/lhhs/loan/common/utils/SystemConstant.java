package com.lhhs.loan.common.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-10-21
 * Time: 下午1:34
 * To change this template use File | Settings | File Templates.
 */
public class SystemConstant {
    private static final String JAVA_DATE_FORMATTER = "yyyy-MM-dd";

    private static final String JAVA_DATETIME_FORMATTER_24 = "yyyy-MM-dd HH:mm:ss";

    private static final String DB_DATE_FORMATTER = "YYYY-MM-DD";

    private static final String DB_DATETIME_FORMATTER_24 = "YYYY-MM-DD HH24:MI:SS";

    private static final String DB_DATETIME_FORMATTER_AM = "YYYY-MM-DD HH:MI AM";

    private static final String JAVA_DATETIME_FORMATTER_AM = "yyyy-MM-dd h:mm a";

    /**
     * @Fields CURR_JAVA_DATETIME_FORMATTER：JAVA_DATETIME_FORMATTER_24="yyyy-MM-dd HH:mm:ss"
     */
    public static final String CURR_JAVA_DATETIME_FORMATTER = JAVA_DATETIME_FORMATTER_24;

    /**
     * @Fields CURR_DB_DATETIME_FORMATTER：DB_DATETIME_FORMATTER_24 = "YYYY-MM-DD HH24:MI:SS"
     */
    public static final String CURR_DB_DATETIME_FORMATTER   = DB_DATETIME_FORMATTER_24;

    /**
     * @Fields CURR_JAVA_DATE_FORMATTER：JAVA_DATE_FORMATTER = "yyyy-MM-dd"
     */
    public static final String CURR_JAVA_DATE_FORMATTER = JAVA_DATE_FORMATTER;

    /**
     * @Fields CURR_DB_DATE_FORMATTER：DB_DATE_FORMATTER = "YYYY-MM-DD"
     */
    public static final String CURR_DB_DATE_FORMATTER = DB_DATE_FORMATTER;

    /**
     * @Fields DEFAULTNNULLVAL：""
     */
    public static final String DEFAULTNNULLVAL = "";
}
