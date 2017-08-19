package exception;
/**
 *	重复秒杀异常
 *	Spring的声明式事务只接受运行期异常,然后执行事务回滚
 */
public class RepeatKillException extends SeckillException{

	private static final long serialVersionUID = 1L;

	public RepeatKillException() {
		super();
	}

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatKillException(String message) {
		super(message);
	}

	
}
