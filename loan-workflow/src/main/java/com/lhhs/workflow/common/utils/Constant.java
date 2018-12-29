package com.lhhs.workflow.common.utils;


/**
 * 工作流常量 
 * @author df
 *
 */
public  class Constant {
	/**
	 * 任务状态
	 * 
	 *
	 */
	public  class TaskStatus{
		/**已签收**/
		public static final String TODO="todo";
		/**待签收**/
		public static final String CHAIM="claim";
		/**已完成**/
		public static final String FINISH="finish";
	}
	/**
	 * 任务状态
	 * 
	 *
	 */
	public  class ProcDefStatus{
		/**已激活**/
		public static final String ACTIVE="已激活";
		/**已挂起**/
		public static final String SUSPEND="已挂起";
	}
	/**
	 * 特殊任务节点
	 * 
	 *
	 */
	public  class UserTaskKey{
		/**开始节点**/
		public static final String START="start";
		/**结束节点**/
		public static final String END="end";
		/**调整申请节点**/
		public static final String MODIFY="modify";
		/**会签节点**/
		public static final String COUNTERSIGN="countersign";
	}

	/**
	 * 决议类型编号
	 * 
	 *
	 */
	public  class ResolutionKey{
		/**同意**/
		public static final String YES="1";
		/**驳回**/
		public static final String NO="0";
		/**驳回修改**/
		public static final String REJECT="-2";
		/**退回**/
		public static final String BACK="-3";
		/**撤销 **/
		public static final String CANCEL="2";
		/**结束 **/
		public static final String OVER="90";
		/**补件 **/
		public static final String PATCH="4";
		/**会签（选择角色） **/
		public static final String SIGN="5";
	}

	/**
	 * 处理结果返回码
	 * 
	 *
	 */
	public  class ReturnCode{
		/**处理成功**/
		public static final int SUCCESS=200;
		/**处理失败**/
		public static final int ERROR=500;
	}
	public class Field{
		/**系统标识**/
		public static final String SYS="sys";
		/**渠道标识**/
		public static final String CHANNEL="channel";
		/**任务申请人**/
		public static final String APPLY="apply";
		/**任务申请人名称**/
		public static final String APPLYNAME="applyName";
		/**多实例计数器**/
		public static final String YESCOUNTER="yescounter";
		/**多实例用户集合**/
		public static final String ASSIGNEELIST="assigneelist";
		/**多实例用户集合数**/
		public static final String ASSIGNEECOUNTER="assigneecounter";
		/**实例总数**/
		public static final String NROFINSTANCES="nrOfInstances";
		/**任务标题**/
		public static final String TITLE="title";
		/**流程实例ID**/
		public static final String PROCINSID="procInsId";
		/**处理结果返回编码：成功true，失败false**/
		public static final String CODE="code";
		/**处理结果错误提示信息**/
		public static final String MSG="msg";
		/**业务流水号**/
		public static final String BUSINESSID="businessId";
		/**子流程建立集合**/
		public static final String SUBLIST="sublist";
		/**子流程建立集合变量**/
		public static final String SUBPARM="subparm";
		/**子流程建立流程变量KEY**/
		public static final String SUBKEY="subkey_";
		/**子流程建立任务和流程流程变量对应关系**/
		public static final String SUBTASK="subtask_";
		/**多实例子任务流程变量KEY**/
		public static final String SUBTEMP="_sub_temp";
		/**备注**/
		public static final String REMARK="remark";
		/**合作伙伴**/
		public static final String PARTNER="partner";
		/**金额**/
		public static final String AMOUNT="amount";
		/**订单编号**/
		public static final String ORDERNO="orderNo";
		/**部门**/
		public static final String DEPARTMENT="department";
		/**部门名称**/
		public static final String DEPARTMENTNAME="departmentName";
		/**平台**/
		public static final String BRANCHCOM="branchcom";
		/**销售办公室**/
		public static final String SALESOFFICE="salesoffice";
		/**事业部**/
		public static final String PRODUCTDPT="productdpt";
		/**产品线**/
		public static final String PRODUCTLINE="productline";
		/**预留字段**/
		public static final String FIELD1="field1";
		/**预留字段**/
		public static final String FIELD2="field2";
		/**预留字段**/
		public static final String FIELD3="field3";
		/**预留字段**/
		public static final String FIELD4="field4";
		/**预留字段**/
		public static final String FIELD5="field5";
		/**备注**/
		public static final String COMPANYID="companyId";
		/**备注**/
		public static final String UNIONID="unionId";
		/**备注**/
		public static final String DEPID="depId";
		
	}

}
