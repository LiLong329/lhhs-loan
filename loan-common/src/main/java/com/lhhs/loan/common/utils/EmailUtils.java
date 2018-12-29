/**
 * 
 */
package com.lhhs.loan.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangpenghong
 *
 */
public class EmailUtils {
	private final static Pattern emailer = Pattern.compile("[\\w[.-]]+@[\\w[.-]]+\\.[\\w]+");
	public static boolean matchEmail(String email){
		Matcher matchr = emailer.matcher(email);
		return matchr.matches();
	}
//	public static final String EMAIL_REGEX = "^\w+[-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$";//"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])";

}
