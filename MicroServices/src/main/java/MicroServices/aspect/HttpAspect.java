package MicroServices.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import MicroServices.bean.Logs;
import MicroServices.dao.LogsRepository;

/**
 * 	基础的AOP,为当前的访问用户记录*/
@Aspect								//作为AOP
@Component							//引入到Spring容器
public class HttpAspect {

	@Autowired
	private LogsRepository logsRepository;
	
	/*
	 * 	一般的切面编程*/
	
	/**在访问前统一AOP处理*/
//	@Before("execution(public * MicroServices.collector.UserController.userList(..))")						//拦截UserController类下的userList方法
	@Before("execution(public * MicroServices.collector.UserController.*(..)))")						//拦截UserController类下的所有方法
	public void logBefor() {
		System.out.println("------------开始访问--------");
	}
	
	/**在访问后统一AOP处理*/
	@After("execution(public * MicroServices.collector.UserController.*(..)))")
	public void logAfter() {
		
		System.out.println("------------访问结束-----------");
	}
	
	
	/*	
	 * 	使用切点结合切面方法,完成相同方法的不同调用,实现代码重用
	 * 	*/
	 
	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
	 
	/**定义切点*/
	@Pointcut("execution(public * MicroServices.collector.UserController.*(..)))")
	public void log() {}
	
	/**日志记录对象*/
	private Logs logs = new Logs();
	
	/**定义切面,完成切面编程*/
	@Before("log()")
	public void classBefore(JoinPoint joinpoint) {
		
		logger.info("-----各种类进行调用----");

		//获取request对象
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
	
		//获取ip
		String ip = request.getRemoteAddr();
		logger.info("IP>>>{}", ip);
		logs.setIp(ip);
		
		//获取url
		String url = request.getRequestURL().toString();
		logger.info("URL>>>{}", url);
		logs.setUrl(url);
		
		//获取mothod
		String method = request.getMethod();
		logger.info("Method>>>{}",method);
		logs.setMethod(method);
		
		//获取类名
		String className = joinpoint.getSignature().getDeclaringTypeName();
		logs.setClassName(className);
		logger.info("ClassName>>>{}", className);
		logs.setUrl(className);
				
		//获取方法名
		String methodName = joinpoint.getSignature().getName();
		logs.setMethodName(methodName);
		logger.info("MethodName>>>{}", methodName);
		logs.setUrl(methodName);
		
		//参数
		Object[] params =  joinpoint.getArgs();
		logs.setParams(params);
		logger.info("Params>>>{}",params);
		
	}
	
	@After("log()")
	public void classAfter() {
		logger.info("-----各种类结束调用----");
	}
	
//	@AfterReturning(returning="object",pointcut="log()")
//	public void classReturn(Object object){
//		logger.info("-----各种类返回调用结束----");
//		logger.info("Return>>>{}",object);
//		logs.setRetruns(object.toString());
//		logsRepository.save(logs);
//		logs = null;
//	}
	
}
