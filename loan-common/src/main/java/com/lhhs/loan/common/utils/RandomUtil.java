package com.lhhs.loan.common.utils;
import java.util.Random;
/**
 * 
 * @author yangyang
 * 时间：2016年9月23日
 * 类说明：随机数工具类
 */
public final class RandomUtil
{
  private static Random random = new Random();
  private RandomUtil() {}
  public static int random()
  {
    return (int)(Math.random() * 1.0E7D);
  }
  public static int random(int lower, int upper)
  {
    if ((lower < 0) || (upper < 0)) {
      throw new IllegalArgumentException();
    }
    
    int len = String.valueOf(upper).length();
    int base = 1;
    for (int i = 0; i < len; i++) {
      base *= 10;
    }
    int num;
    do {
      num = (int)(Math.random() * base);
    } while ((num < lower) || (num > upper));
    return num;
  }

  public static String random(int len)
  {
    String sys = "23456789ABCDEFGHIJKLMNQRSTUWXYZ";
    
    String re = "";
    for (int i = 0; i < len; i++) {
      int n = random.nextInt(31);
      re = re + sys.substring(n, n + 1);
    }
    return re;
  }
  
  public static String randomDegital(int len) { StringBuffer result = new StringBuffer();
    for (int i = 0; i < len; i++) {
      result.append(random.nextInt(10));
    }
    return result.toString();
  }
  public static String randomString(int len)
  {
    String result = "";
    
    for (int i = 0; i < len; i++) {
      result = result + randomChar();
    }
    return result;
  }
  
  public static char randomChar()
  {
    int val = random.nextInt(95) + 32;

    if ((val == 48) || (val == 79) || (val == 105) || (val == 111))
      val++;
    return (char)val;
  }
}
