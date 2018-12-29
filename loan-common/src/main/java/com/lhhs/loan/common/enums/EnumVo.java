package com.lhhs.loan.common.enums;
/**
 * * <p>Title: </p>
   * <p>Description: 枚举所需要的两个变量   order：代表0,1,2,3,4 value：代表汉字</p>
   * <p>Company: lhhs</p>
   * @author xuejinxiong, 
   * @date 2017年6月16日 下午5:40:04
 */
public class EnumVo {
		String order;
		String value;
		public String getOrder() {
			return order;
		}
		public void setOrder(String order) {
			this.order = order;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public EnumVo(String order, String value) {
			super();
			this.order = order;
			this.value = value;
		}
		
}
