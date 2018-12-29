package com.lhhs.loan.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
	//对日减一法则的计算
	public static void main(String[] args) throws Exception {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = dateFormat.parse("2016-12-31 00:00:00");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		System.out.println(day);
		Calendar calendar1 = Calendar.getInstance();
		String aa = dateFormat.format(calendar.getTime());
		for (int i = 0; i < 2; i++) {
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, i+1);
			int day2 = calendar.get(Calendar.DAY_OF_MONTH);
			calendar1.setTime(calendar.getTime());
			calendar1.add(Calendar.SECOND, -1);
			if(day2 != day){
				calendar1.add(Calendar.DAY_OF_MONTH, 1);
			}
			System.out.println(aa+"-------"+dateFormat.format(calendar1.getTime()));
			calendar1.add(Calendar.SECOND, 1);
			aa = dateFormat.format(calendar1.getTime());
		}
		
	}
	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
