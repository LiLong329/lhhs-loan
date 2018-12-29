package com.lhhs.loan.common.utils;

import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class StrUtils {
	public static String emptyIfNull(String value) {
		return (value == null ? "" : value);
	}

	public static boolean isNullOrEmpty(String src) {
		if (src == null || "".equals(src) || "null".equals(src)) {
			return true;
		}
		return false;

	}

	/**
	 * transform string to integer
	 * 
	 * @param src
	 * @param defaultVal
	 *            if conversion fail,return this value
	 * @return
	 */
	public static int toInteger(String src, int defaultVal) {
		int iResult;
		try {
			iResult = Integer.parseInt(src);
		} catch (Exception e) {
			iResult = defaultVal;
		}
		return iResult;

	}

	/**
	 * transform string to double
	 * 
	 * @param src
	 * @param defaultVal
	 *            if conversion fail,return this value
	 * @return
	 */
	public static double toDouble(String src, double defaultVal) {
		double iResult;
		try {
			iResult = Double.parseDouble(src);
		} catch (Exception e) {
			iResult = defaultVal;
		}
		return iResult;

	}

	/**
	 * This method carves up the character string "sourse" by "delim"
	 * 
	 * @param source
	 *            Original character string which need to carve up
	 * @param delim
	 *            Compartmental character string of the word.
	 * @return While Array is carved up,if "source" is null,the array equal to
	 *         String[0], else if "delim" is null,comma by the way of the
	 *         compartmental character string.
	 * @since 0.1
	 */
	public static String[] split(String source, String delim) {
		String[] wordLists;
		if (source == null) {
			return (new String[0]);

		}
		if (delim == null) {
			delim = ",";
		}
		StringTokenizer st = new StringTokenizer(source, delim);
		int total = st.countTokens();
		wordLists = new String[total];
		for (int i = 0; i < total; i++) {
			wordLists[i] = st.nextToken();
		}
		return wordLists;
	}

	/**
	 * This method carves up the character string "sourse" by "delim"
	 * 
	 * @param source
	 *            Original character string which need to carve up
	 * @param delim
	 *            Compartmental character string of the word.
	 * @return While Array is carved up,if "source" is null,return the array
	 *         which uses it as the only argument.
	 * @since 0.1
	 */
	public static String[] split(String source, char delim) {
		return split(source, String.valueOf(delim));
	}

	public static String[] split(String source) {
		return split(source, ",");
	}

	/**
	 * Repace the content of the string with the "values". If the content
	 * contains expression than it can't repace.
	 * 
	 * @param prefix
	 *            The prefix of the variant
	 * @param source
	 *            Original string
	 * @param values
	 * 
	 * @return The string which has been replaced. If the prefix is empty than
	 *         the "%" looked as the prefix; If the source or values is null
	 *         than return "source"; If the length of the values is great than
	 *         the parameter's count than the redundant values is ignored; If
	 *         the length of the values is less than the parameter's count than
	 *         the other parameters are all the last value of the values.
	 * @since 0.1
	 */
	public static String getReplaceString(String prefix, String source,
			String[] values) {
		String result = source;
		if (source == null || values == null || values.length < 1) {
			return source;
		}
		if (prefix == null) {
			prefix = "%";
		}

		for (int i = 0; i < values.length; i++) {
			String argument = prefix + Integer.toString(i + 1);
			int index = result.indexOf(argument);
			if (index != -1) {
				String temp = result.substring(0, index);
				if (i < values.length) {
					temp += values[i];
				} else {
					temp += values[values.length - 1];
				}
				temp += result.substring(index + 2);
				result = temp;
			}
		}
		return result;
	}

	/**
	 * Replace the variant(the prefix of the variant is '%') of the string with
	 * the contant of the values
	 * 
	 * Nesting is forbidden in the course of replace,it doesn't replace anything
	 * if the contents of replace contain variant expression.
	 * 
	 * @param source
	 *            Original string.
	 * @param values
	 *            String array for replace.
	 * @return The string which has been replaced.
	 * @since 0.1
	 */
	public static String getReplaceString(String source, String[] values) {
		return getReplaceString("%", source, values);
	}

	/**
	 * Whether the strings contain the appointed string.
	 * 
	 * @param strings
	 *            Original strings.
	 * @param string
	 *            Appointed string.
	 * @param caseSensitive
	 * @return true if the String[] contains the String; false otherwise.
	 * @since 0.4
	 */
	public static boolean contains(String[] strings, String string,
			boolean caseSensitive) {
		for (int i = 0; i < strings.length; i++) {
			if (caseSensitive == true) {
				if (strings[i].equals(string)) {
					return true;
				}
			} else {
				if (strings[i].equalsIgnoreCase(string)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Whether the strings contain the appointed string. It is sensitive to
	 * capital or small letter.
	 * 
	 * @param strings
	 *            Original strings.
	 * @param string
	 *            Appointed string.
	 * @return true if the String[] contains the String; false otherwise.
	 * @since 0.4
	 */
	public static boolean contains(String[] strings, String string) {
		return contains(strings, string, true);
	}

	/**
	 * 
	 * Whether the strings contains the appointed string. Neglect capital or
	 * small letter.
	 * 
	 * @param strings
	 *            Original strings.
	 * @param string
	 *            Appointed string.
	 * @return true if the String[] contains the String; false otherwise.
	 * @since 0.4
	 */
	public static boolean containsIgnoreCase(String[] strings, String string) {
		return contains(strings, string, false);
	}

	/**
	 * Unite the strings with appointed list separator to one string.
	 * 
	 * @param array
	 *            String array.
	 * @param delim
	 *            List separator,while it is null,replace it with "".
	 * @return String which has been united.
	 * @since 0.4
	 */
	public static String combineStringArray(String[] array, String delim) {
		int length = array.length - 1;
		if (delim == null) {
			delim = "";
		}
		StringBuffer result = new StringBuffer(length * 8);
		for (int i = 0; i < length; i++) {
			result.append(array[i]);
			result.append(delim);
		}
		result.append(array[length]);
		return result.toString();
	}

	/**
	 * Convert a byte variant to corresponding value of ASCII.
	 * 
	 * @param b
	 *            byte type variant to convert.
	 * @return int which has been converted
	 * @since 0.1
	 */
	public static int byte2int(byte b) {
		if (b < 0)
			return (int) b + 0x100;
		return b;
	}

	/**
	 * Show the percentage based on the appointed number of fraction digits.
	 * 
	 * @param dblPercent
	 *            java.lang.double
	 * 
	 * @param min
	 *            the minimum number of fraction digits to be shown;
	 * @param max
	 *            the maximum number of fraction digits to be shown;
	 * @return java.lang.String[]
	 */
	public static String getPercent(double dblPercent, int min, int max) {
		NumberFormat dataformat = NumberFormat.getInstance();
		dataformat.setMaximumFractionDigits(max);
		dataformat.setMinimumFractionDigits(min);
		if (Double.isNaN(dblPercent)) {
			dblPercent = 0.0;
		}
		String tmp = dataformat.format(dblPercent * 100);
		if ("0".equals(tmp)) {
			tmp = "*";
		}
		return tmp;
	}

	/**
	 * Convert the string which contains html Tag to common string.
	 * 
	 * @param value
	 *            string to convert.
	 * @return the result string.
	 */
	public static String escapeHTMLTags(String sourcestr) {

		if (sourcestr == null)
			return "";

		int strlen;
		String restring = "";
		String destr = "";
		strlen = sourcestr.length();

		for (int i = 0; i < strlen; i++) {
			char ch = sourcestr.charAt(i);

			switch (ch) {
			case '<':
				destr = "&lt;";
				break;
			case '>':
				destr = "&gt;";
				break;
			case '\"':
				destr = "&quot;";
				break;
			case '&':
				destr = "&amp;";
				break;
			case 13:
				destr = "<br>";
				break;
			case 32:
				destr = "&nbsp;";
				break;
			default:
				destr = "" + ch;
				break;
			}
			restring = restring + destr;
		}
		return "" + restring;
	}

	/**
	 * Function to get random ID,the default value is a random string of 15 bits
	 * length.
	 * 
	 * @retuen A random string of 15 bits length.
	 */
	public static Long getRandomNumber() {

		Random randGen = new Random();
		String strID = String.valueOf(Math.abs(randGen.nextLong()));

		return Long.valueOf(strID.substring(0, 15));
	}

	/**
	 * Check-up the validity of the email address.
	 * 
	 * @param email
	 *            email address
	 * @return true if the email address is valid; false otherwise.
	 */
	public static boolean isValidMail(String email) {

		boolean checked_status = true;
		boolean at_let = false;
		boolean point_let = false;
		int at_ps = -1;
		int str_length = email.length() - 1;
		email = email.toUpperCase();

		for (int i = 0; i <= str_length; i++) {

			char ch = email.charAt(i);
			if ((ch < 'A' || ch > 'Z') && (ch < '0' || ch > '9')) {

				switch (ch) {

				case '@': {
					if (i == 0 || i == str_length || at_let) {
						checked_status = false;
					}
					at_let = true;
					at_ps = i;
					break;
				}

				case '.': {
					if (i == 0 || i == str_length || i == at_ps + 1
							|| i == at_ps - 1) {
						checked_status = false;
					}
					point_let = true;
					break;
				}

				case '_':

				case '-': {
					if (i == 0 || i == str_length) {
						checked_status = false;
					}
					break;
				}

				default:
					checked_status = false;
					break;
				}
			}

			if (!checked_status) {
				break;
			}
		}

		checked_status = checked_status & point_let & at_let;
		return checked_status;
	}

	/**
	 * Convert the string (yes or no, 1 or 0, y or n) to corresponding value:
	 * true or false.
	 * 
	 * @param theString
	 *            yes or no, 1 or 0, y or n
	 * @return true or false.
	 */
	public static boolean toBoolean(String theString) {
		if (theString == null) {
			return false;
		}

		theString = theString.trim();
		if (theString.equalsIgnoreCase("y")
				|| theString.equalsIgnoreCase("yes")
				|| theString.equalsIgnoreCase("true")
				|| theString.equalsIgnoreCase("1")) {
			return true;
		}
		return false;
	}

	/**
	 * Judge whether a string is null or "" .
	 * 
	 * @param theString
	 *            Any string, possibly null
	 * @param theMessage
	 *            the detail message.
	 */
	public static void assertNotBlank(String theString, String theMessage) {
		if (theString == null) {
			throw new IllegalArgumentException("Null argument not allowed: "
					+ theMessage);
		}
		if (theString.trim().equals("")) {
			throw new IllegalArgumentException("Blank argument not allowed: "
					+ theMessage);
		}
	} /* assertNotBlank(String, String) */

	/**
	 * Convert the number 1 to 26 to corresponding capital letter, for example:1
	 * correspond to A, 2 correspond to B,26 correspond to Z.
	 * 
	 * @param number
	 *            the number to convert to a character.
	 * @param upperCaseFlag
	 *            the uppercase equivalent of the character, if any; otherwise,
	 *            the character itself.
	 * @return java.lang.String Capital letter.
	 * @throws Exception
	 */
	public static String numberToLetter(int number, boolean upperCaseFlag)
			throws Exception {

		// add nine to bring the numbers into the right range (in java, a= 10, z
		// =
		// 35)
		if (number < 1 || number > 26) {
			throw new Exception("The number is out of the proper range (1 to "
					+ "26) to be converted to a letter.");
		}
		int modnumber = number + 9;
		char thechar = Character.forDigit(modnumber, 36);
		if (upperCaseFlag) {
			thechar = Character.toUpperCase(thechar);
		}
		return "" + thechar;
	} /* numberToLetter(int, boolean) */

	/**
	 * Format an int to string which length is appointed, if the length is less
	 * than appointed length,add 0 ahead of the int.
	 * 
	 * @param i
	 *            an int to foamat.
	 * @param len
	 *            the length of this string.
	 * @return a string representation of the int argument.
	 */
	public static String formatIntToStr(int i, int len) {
		String s = "";
		String t = "";
		s = String.valueOf(i);

		if (s.length() < len) {
			for (int j = 0; j < (len - s.length()); j++)
				t = t + "0";
		}
		return (t + s);
	}

	/**
	 * Format a string to another string which length is appointed, if the
	 * formatted string's length is less than appointed length,add appointed
	 * character at the end of it.
	 * 
	 * @param str
	 *            string to format.
	 * @param len
	 *            appointed length to format.
	 * @param s
	 *            appointed character add to the back of the string.
	 * @return String which has been formatted.
	 */
	public static String formatStr(String str, int len, String s) {

		String t = "";

		if (s.length() < len) {
			for (int j = 0; j < (len - str.length()); j++)
				t = t + s;
		}
		return (str + t);
	}

	public static String expandStr(String src, int iLen, char supply) {
		if (src == null)
			src = "";
		if (src.length() > iLen)
			return src;

		char content[] = new char[iLen];

		src.getChars(0, src.length(), content, iLen - src.length());
		Arrays.fill(content, 0, iLen - src.length(), supply);

		return new String(content);

	}

	/**
	 * only replace first one occur
	 * 
	 * @param src
	 * @param srcSubStr
	 * @param descStr
	 * @return
	 */
	public static String replaceFirst(String src, String srcSubStr,
			String descStr) {

		if ((null == src || "".equals(src))
				|| (null == srcSubStr || "".equals(srcSubStr))
				|| descStr == null || descStr.equals(srcSubStr))
			return src;
		StringBuffer buffer = new StringBuffer(src);
		int iParamePos = buffer.toString().indexOf(srcSubStr);
		if (iParamePos >= 0) {
			int iPlaceSignLen = srcSubStr.length();
			buffer.replace(iParamePos, iParamePos + iPlaceSignLen, descStr);
			iParamePos = buffer.toString().indexOf(srcSubStr);
		}
		return buffer.toString();
	}

	/**
	 * replace all occur
	 * 
	 * @param src
	 * @param srcSubStr
	 * @param descStr
	 * @return
	 */
	public static String replace(String src, String srcSubStr, String descStr) {

		if ((null == src || "".equals(src))
				|| (null == srcSubStr || "".equals(srcSubStr))
				|| descStr == null || descStr.equals(srcSubStr))
			return src;
		StringBuffer buffer = new StringBuffer(src);
		int iParamePos = buffer.toString().indexOf(srcSubStr);
		while (iParamePos >= 0) {
			int iPlaceSignLen = srcSubStr.length();
			buffer.replace(iParamePos, iParamePos + iPlaceSignLen, descStr);
			iParamePos = buffer.toString().indexOf(srcSubStr);
		}
		return buffer.toString();
	}

	public static String decode(String str) {
		try {
			return str == null ? "" : new String(str.getBytes("ISO8859_1"),
					"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 
	 * @Title: trimLastComma  
	 * @Description: 去掉数据库查询参数中最后一个逗号  
	 * @param str
	 * @return  
	 * @return String    返回类型  
	 * @throws
	 */
	public static String trimLastComma(String str){
		if(null==str){
			return str;
		}
		if(str.endsWith(",")){
			str=str.substring(0,str.length()-1);
		}
		return str;
	}
	
	/**
	 * 
	 * @Title: retNull2Str  
	 * @Description: 处理返回结果（NULL转换为""格式）
	 * @param str
	 * @return  
	 * @return String    返回类型  
	 * @throws
	 */
	public static String retNull2Str(String str){
		return (str==null||"null".equals(str)) ? "" : str;
	}
	
	/**
	 * 将字符串ids转换为Long[] 
	 * @param ids
	 * @return
	 */
	public static Long[] returnArray(String ids){
		if(ids==null)
			return null;
		ids=trimLastComma(ids);
		String[] arr = ids.split(",");
		Long[] larr = new Long[arr.length];
		for (int i = 0; i < arr.length; i++) {
			larr[i] = Long.parseLong(arr[i]);
		}
		return larr;
	}
	
}
