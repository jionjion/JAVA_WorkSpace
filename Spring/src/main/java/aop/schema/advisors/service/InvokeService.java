package aop.schema.advisors.service;
/**正确/错误执行,被通知统计发生错误的次数,终止返回*/
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Service
public class InvokeService {
	
	public void invoke() {
		System.out.println("业务正在执行 ......");
	}
	
	public void invokeException() {
		throw new PessimisticLockingFailureException("");
	}

}
