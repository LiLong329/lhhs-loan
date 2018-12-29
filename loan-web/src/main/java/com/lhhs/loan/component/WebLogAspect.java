package com.lhhs.loan.component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.lhhs.bump.api.AuthorityApi;
import com.lhhs.bump.domain.Authority;
import com.lhhs.bump.domain.SecurityUser;
import com.lhhs.loan.common.security.SecurityUserHolder;
import com.lhhs.loan.common.utils.DateUtils;
import com.lhhs.loan.common.utils.LoggerUtils;
import com.lhhs.loan.dao.domain.LoanOperateRecordWithBLOBs;
import com.lhhs.loan.service.OperateRecordService;
 
/**
 * 操作日志
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class WebLogAspect {
	
	private final static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);
	@Autowired
	private OperateRecordService operateRecordService;
	@Reference
	private AuthorityApi authorityApi;
	
	//	ThreadLocal的接口方法
	//	void set(Object value)设置当前线程的线程局部变量的值。
	//	public Object get()该方法返回当前线程所对应的线程局部变量。
	//	public void remove()将当前线程局部变量的值删除，目的是为了减少内存的占用，该方法是JDK 5.0新增的方法。需要指出的是，当线程结束后，对应该线程的局部变量将自动被垃圾回收，所以显式调用该方法清除线程的局部变量并不是必须的操作，但它可以加快内存回收的速度。
	//	protected Object initialValue()返回该线程局部变量的初始值，该方法是一个protected的方法，显然是为了让子类覆盖而设计的。这个方法是一个延迟调用方法，在线程第1次调用get()或set(Object)时才执行，并且仅执行1次。ThreadLocal中的缺省实现直接返回一个null。
    ThreadLocal<LoanOperateRecordWithBLOBs> threadLocalEntity = new ThreadLocal<LoanOperateRecordWithBLOBs>();
   
    //所有的权限
    List<Authority> authorityList = new ArrayList<>();
    
	// 使用@Aspect注解将一个java类定义为切面类
	// 使用@Pointcut定义一个切入点，可以是一个规则表达式，比如下例中某个package下的所有函数，也可以是一个注解等。
	// 根据需要在切入点不同位置的切入内容
	// 使用@Before在切入点开始处切入内容
	// 使用@After在切入点结尾处切入内容
	// 使用@AfterReturning在切入点return内容之后切入内容（可以用来对处理返回值做一些加工处理）
	// 使用@Around在切入点前后切入内容，并自己控制何时执行切入点自身的内容
	// 使用@AfterThrowing用来处理当切入内容部分抛出异常之后的处理逻辑
    
    /**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意包名
     * ~ 第三个 * 代表任意方法.
     * ~ 第四个 * 定义在web包或者子包
     * ~ 第五个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.lhhs.loan.web.controller..*.*(..))")
    public void webLog(){}
     
	/** 
	 * 前置通知，方法调用前被调用 
	 * @param joinPoint 
	 */  
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
    	// 接收到请求，记录请求内容
		logger.info("WebLogAspect.doBefore()");
		//加载权限
		if(authorityList.size() == 0) {
			Authority entity = new Authority();
			entity.setServerId("lhhs_spark");
			authorityList =authorityApi.queryList(entity);
		}
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//创建日志实体
		LoanOperateRecordWithBLOBs record = new LoanOperateRecordWithBLOBs();
		//请求开始时间
		record.setRequestTime(new Date());
		//获取请求sessionId
		String sessionId = request.getRequestedSessionId();
		//请求路径
		String url = request.getRequestURI();
		//请求标题
		String title=null;
		for (int i = 0; i < authorityList.size(); i++) {
			if(url.equals(authorityList.get(i).getUrl())){
				title=authorityList.get(i).getName();
				break;
			}
		}
		//获取请求参数信息
		String paramData = JSON.toJSONString(request.getParameterMap(),SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue);
		if(paramData.length()>4000000){
			paramData="参数大于4兆";
		}
		//设置客户端ip
		record.setClientIp(LoggerUtils.getCliectIp(request));
		//设置请求方法
		record.setMethod(request.getMethod());
		//设置请求类型（json|普通请求）
		record.setType(LoggerUtils.getRequestType(request));
		//设置请求地址
		record.setUri(url);
		//设置请求标题
		record.setTitle(title);
		//设置sessionId
		record.setSessionId(sessionId);
		//请求的类及名称
		record.setClassMethod(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		//设置请求参数内容json字符串
		record.setParamData(paramData);
		threadLocalEntity.set(record);
		logger.info(JSON.toJSONString(record));
	}
     
	/** 
     * 后置返回通知 
     * 这里需要注意的是: 
     *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息 
     *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数 
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值 
     * @param joinPoint 
     * @param keys 
     */  
	@AfterReturning(value = "webLog()",returning = "returnData")
	public void  doAfterReturning(JoinPoint joinPoint,Object returnData){
		// 处理完请求，返回内容
		logger.info("WebLogAspect.doAfterReturning()");
        logger.info("RETURN DATA:"+ returnData);
        //当前用户
        SecurityUser user = null;
        if(!(SecurityUserHolder.getCurrentUser() instanceof String)){
        	user = (SecurityUser) SecurityUserHolder.getCurrentUser();
        }
        //获取response
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = attributes.getResponse();
        //获取请求错误码
        int status = response.getStatus();
        //操作日志实体
        LoanOperateRecordWithBLOBs record = threadLocalEntity.get();
        if(null!=user){
        	record.setUserId(user.getUserId().toString());
        	record.setUsername(user.getStaffName());
        	record.setDeptId(user.getDeptId());
        	record.setCompanyId(user.getCompanyId());
        	record.setUnionId(user.getUnionId());
        }
        //设置返回时间
        record.setReturnTime(new Date());
        //设置请求时间差
        record.setTimeConsuming(DateUtils.secondSBetween(record.getRequestTime(), record.getReturnTime()));
        //设置返回错误码
        record.setHttpStatusCode(status+"");
        //设置返回值
        record.setReturnData(JSON.toJSONString(returnData,SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteMapNullValue));
        //执行将日志写入数据库
        logger.info(JSON.toJSONString(record));
        operateRecordService.save(record);
	}
     
     
	/** 
     * 后置异常通知 
     *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法； 
     *  throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行， 
     *      对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。 
     * @param joinPoint 
     * @param exception 
     */  
    @AfterThrowing(value = "webLog()",throwing = "exception")  
    public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception){  
    	LoanOperateRecordWithBLOBs record = threadLocalEntity.get();
    	if(null!=exception){
    		record.setExceptionMessage(exception.toString());
    	}
    	//当前用户
        SecurityUser user = null;
        if(!(SecurityUserHolder.getCurrentUser() instanceof String)){
        	user = (SecurityUser) SecurityUserHolder.getCurrentUser();
        }
        if(null!=user){
        	record.setUserId(user.getUserId().toString());
        	record.setUsername(user.getStaffName());
        	record.setDeptId(user.getDeptId());
        	record.setCompanyId(user.getCompanyId());
        	record.setUnionId(user.getUnionId());
        }
    	operateRecordService.save(record);
    	logger.info(exception.getMessage());
	}  
   
	/** 
	 * 后置最终通知（目标方法只要执行完了就会执行后置通知方法） 
     * @param joinPoint 
     */  
//  @After(value = "webLog()")  
//  public void doAfterAdvice(JoinPoint joinPoint){  
//  	System.out.println("后置通知执行了!!!!");  
//  }  
   
    /** 
     * 环绕通知： 
     *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。 
     *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型 
     */  
//  @Around(value = "webLog()")  
//  public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){  
//		System.out.println("环绕通知的目标方法名："+proceedingJoinPoint.getSignature().getName());  
// 		try {//obj之前可以写目标方法执行前的逻辑  
//       	Object obj = proceedingJoinPoint.proceed();//调用执行目标方法  
//        	return obj;  
//    	} catch (Throwable throwable) {  
//         	throwable.printStackTrace();  
//   	}  
//      return null;  
//	}  
     
}
