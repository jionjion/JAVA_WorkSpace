package aop.schema.advisors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.core.Ordered;
import org.springframework.dao.PessimisticLockingFailureException;

public class ConcurrentOperationExecutor implements Ordered {

	private static final int DEFAULT_MAX_RETRIES = 2;

	private int maxRetries = DEFAULT_MAX_RETRIES;
	
	private int order = 1;	

	public void setMaxRetries(int maxRetries) {		//从XML文件中获得参数
		this.maxRetries = maxRetries;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {				//从配置文件中获得参数
		this.order = order;
	}

	/**自定义异常,统计重置次数*/
	public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
		int numAttempts = 0;		//记录次数
		PessimisticLockingFailureException lockFailureException;
		do {
			numAttempts++;
			System.out.println("重试次数 : " + numAttempts);
			try {
				return pjp.proceed();
			} catch (PessimisticLockingFailureException ex) {
				lockFailureException = ex;
			}
		} while (numAttempts <= this.maxRetries);
		System.out.println("发生错误 : " + numAttempts);
		throw lockFailureException;
	}
}
