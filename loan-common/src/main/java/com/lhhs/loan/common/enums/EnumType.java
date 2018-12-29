package com.lhhs.loan.common.enums;

/**
 * 一种通过配置，即可实现数据库导出时，对使用编号表示的列进行字典翻译的方法
 * 1、配置要翻译字段的枚举类型
 * 2、配置枚举类型的别名在keyEnum中
 * 3、调用位置配置要翻译的字段名字，（别名元字段名要一致）
 * 4、除枚举类其他不要添加其他类
 * 5、类自动在indexListenr注入
 * @author Ghoul
 *
 */
public class EnumType {
	
	
	
	/**
	   * <p>Title: </p>
	   * <p>Description:信用状态</p>
	   * <p>Company: lhhs</p>
	   * @author xuejinxiong, 
	   * @date 2017年6月16日 下午5:06:29
	 */
	public  enum CreditStatus {
		无逾期,有逾期  
	}
	
	/**
	 * * <p>Title: </p>
	   * <p>Description: 性别</p>
	   * <p>Company: lhhs</p>
	   * @author xuejinxiong, 
	   * @date 2017年6月16日 下午5:06:29
	 */
	public  enum Sex {
		男,女  
	}

	/**
	 * * <p>Title: </p>
	   * <p>Description:户口性质 </p>
	   * <p>Company: lhhs</p>
	   * @author xuejinxiong, 
	   * @date 2017年6月16日 上午11:41:50
	 */
	public enum Category {
	   城镇,农村;
	}
	/**
	 * * <p>Title: </p>
	   * <p>Description: 驾龄</p>
	   * <p>Company: lhhs</p>
	   * @author xuejinxiong, 
	   * @date 2017年6月16日 上午11:13:04
	 */
	public enum NoviceDriver {
	  一年以下,一到三年,三到五年,五年以上;
	}
	/**
	 * * <p>Title: </p>
	   * <p>Description: 学校</p>
	   * <p>Company: lhhs</p>
	   * @author xuejinxiong, 
	   * @date 2017年6月16日 上午11:12:01
	 */
	public enum School {
		小学,中学,高中,中专,大专,本科,研究生,博士,博士后
	}
	/**
	 * * <p>Title: </p>
	   * <p>Description: 健康状况</p>
	   * <p>Company: lhhs</p>
	   * @author xuejinxiong, 
	   * @date 2017年6月16日 上午11:11:10
	 */
	public enum Situation {
	 良好,一般,差;
	}
	
	/**
	 * * <p>Title: </p>
	   * <p>Description: 健康状况</p>
	   * <p>Company: lhhs</p>
	   * @author xuejinxiong, 
	   * @date 2017年6月16日 上午11:11:10
	 */
	public enum CredentialsType {
		基本资质 ,房产资质,车产资质 ,信用资质,合同资质 ,其他资质
	}
	/**
	 * * <p>Title: </p>
	   * <p>Description:轮播图的图片位置 </p>
	   * <p>Company: lhhs</p>
	   * @author xuejinxiong, 
	   * @date 2017年7月14日 上午9:29:55
	 */
	public enum AdvertisingLocationid {
		PC首页轮播图,APP首页轮播图,H5首页轮播图
	}

	public enum OrderInfo {
		初评,
		下户,
		面审,
		终审,
		放款申请,
		放款审核,
		签约公证,
		权证抵押,
		放款确认,
		财务放款,
		已撤单,
		已拒贷,
		放款成功,
		已还款,
		已逾期,
		已还清
	}		
	
	public enum TaskNode {
		初评,
		下户,
		面审,
		终审,
		放款申请,
		放款审核,
		签约公正,
		权证抵押,
		放款确认,
		财务放款,
		已撤单,
		已拒贷,
		放款成功

	}
	
	public enum TaskNodeStatus{
		初评,
		下户,
		面审,
		终审,
		放款申请,
		放款审核,
		签约公正,
		权证抵押,
		放款确认,
		财务放款,
		已撤单,
		已拒贷,
		放款成功,
		初评补件,
		下户补件,
		终审补件
	}
	
	public enum LetResult{
		同意,
		拒贷,
		补件,
		撤单,
		补件完成,
		会签,
		资金审批同意,
		资金审批拒贷
	}
	public enum LftResult{
		同意,
		会签,
		拒贷
	}
	//抵押顺位
	public enum MortgagePosition{
		一抵 ,
		二抵 ,
		解查封 ,
		垫资过桥
	}
	
	public enum HouseType{
		住宅,
		商住,
		公寓,
		底商,
		别墅,
		办公,
		其它
	}
	public enum LiveSituation{
		出租,
		自住,
		亲戚居住,
		其他
	}
	
	public enum Whether{
		否 ,是
	}
	public enum Status{
		禁用 ,启用
	}
	
	public enum ExpenditureVariety{
		通道费,       
		平台服务费,     
		资金管理经费,    
		保费支出,      
		保证金支出,     
		定金支出,      
		返佣金,       
		刷卡手续费支出,   
		借款公证费支出,   
		委托公证费支出,   
		登记费支出,   
		其他三方费用收入
	}
	//发放方式 : 0:无\r\n1:按月发放\r\n2:一次性发放'
	public enum Disbursement{
		无,
		按月发放,
		一次性发放
	}
}
