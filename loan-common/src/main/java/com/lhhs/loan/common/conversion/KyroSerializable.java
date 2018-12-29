package com.lhhs.loan.common.conversion;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Registration;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
/**
 * 序列化工具类
 * @author yy
 *
 */
public class KyroSerializable {

	private static LinkedHashMap<String, Registration>  regMap = new LinkedHashMap<String, Registration>();
	private static Object lock = new Object();
	/**
	 * 放置注册类
	 * @param o
	 */
	private static Registration putRegistration(Kryo kryo,Object o){
		String className = o.getClass().getName();
		Object c = regMap.get(className);
		if(c==null){
			synchronized (lock) {
				c = regMap.get(className);
				if(c==null){
					Registration registration = kryo.register(o.getClass());
					regMap.put(className, registration);
					return registration;
				}else{
					return (Registration) c;
				}
			}
		}else{
			return (Registration) c;
		}
	}
	
	/**
	 * 获取注册类
	 * @param o
	 * @throws ClassNotFoundException 
	 */
	private static Registration getRegistration(Kryo kryo,String className) throws ClassNotFoundException{
		Object c = regMap.get(className);
		if(c==null){
			synchronized (lock) {
				c = regMap.get(className);
				if(c==null){
					Registration registration = kryo.register(Class.forName(className));
					regMap.put(className, registration);
					return registration;
				}else{
					return (Registration) c;
				}
			}
		}else{
			return (Registration) c;
		}
	}
	
	/**
	 * Object 转换为String
	 * @param o
	 * @return
	 * @throws Exception 
	 */
	public static String obj2Str(Object o) throws Exception{
		if(null == o)
			return null;
		Kryo kryo = new Kryo(); 
		putRegistration(kryo,o);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		Output output = new Output(outStream); 
		kryo.writeObject(output, o);
		output.flush();
		byte[] tmp = outStream.toByteArray(); 
		output.close();
		outStream.close();
		StringBuffer sb= new StringBuffer();
		sb.append(o.getClass().getName()).append(";");
		sb.append(new String(tmp,"ISO-8859-1"));
		return sb.toString();
	}
	
	/**
	 * String转化为Object
	 * @param o
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static Object str2Obj(String encoding) throws Exception{
		if(null == encoding)
			return null;
		Object o = null;
		int i= encoding.indexOf(";");
		if(i<1){
			new Exception ("bytes format is error!");
		}else{
			String className = encoding.substring(0,i);
			String encode = encoding.substring(i+1);
			byte[] bytes = encode.getBytes("ISO-8859-1");
			Kryo kryo = new Kryo(); 
			Input input = new Input(bytes);
			Registration registration = getRegistration(kryo,className);
			o = kryo.readObject(input, registration.getType());
		}
		return o;
	}
	
	
//	public static void main(String[] args) throws Exception {
//		TestBean a = new TestBean();
//		a.setA("sdhsdhjsd");
//		a.setB("sdhsdhjsd");
//		a.setC("sdhsdhjsd");
//		a.setD(new Date());
//		
//		TestBean b = null;
//		long start = System.currentTimeMillis();
//		for(int i=0;i<100000;i++){
//			String aa = KyroSerializable.obj2Str(a);
//			//System.out.println(aa);
//			b = (TestBean) KyroSerializable.str2Obj(aa);
//		}
//		System.out.println(System.currentTimeMillis() - start);
//		System.out.println(b);
//	}
	
//	static class TestBean{
//		
//		
//		String A;
//		String B;
//		String C;
//		Date D;
//		
//		public TestBean(){
//			
//		}
//		
//		public String getA() {
//			return A;
//		}
//		public void setA(String a) {
//			A = a;
//		}
//		public String getB() {
//			return B;
//		}
//		public void setB(String b) {
//			B = b;
//		}
//		public String getC() {
//			return C;
//		}
//		public void setC(String c) {
//			C = c;
//		}
//		public Date getD() {
//			return D;
//		}
//		public void setD(Date d) {
//			D = d;
//		}
//		
//	}
}

