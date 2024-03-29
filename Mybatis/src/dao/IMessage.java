package dao;
/**
 * 	1.将命名空间改为接口的路径
 * 	2.将方法名与ID相一致
 * 	3.返回类型与XML配置文件一致
 * 	4.参数类型与XML配置文件一致
 */
import java.util.List;
import java.util.Map;

import bean.Message;

/**和IMessage对应接口式规范*/
public interface IMessage {

	/**查询消息列表的方法*/
	public List<Message> queryMessagesList(Map<String, Object> parameters);

	/**查询模糊条件的总条数的方法*/
	public int count(Message message);
	
	/**使用拦截完成分页查询*/
	public List<Message> queryMessagesListByPageInterceptor(Map<String, Object> parameters);
	
}
