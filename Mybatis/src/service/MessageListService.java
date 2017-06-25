package service;
import java.util.ArrayList;
import java.util.Arrays;
/**
 * 	Message的维护
 * */
import java.util.List;

import bean.Message;
import dao.MessageDao;

/**列表服务类*/
public class MessageListService {

	/**传入参数进行模糊查询,如果没有参数则是全部查询**/
	public List<Message> queryMessagesList(String command,String description ) {
		MessageDao messageDao = new MessageDao();
		List<Message> messagesList = messageDao.queryMessagesListByInterface(command, description);
		return messagesList;
	}
	
	/**传入参数,实现删除*/
	public void deleteOneMessage(String id) {
		if (id != null && !"".equals(id.trim())) {
		 	MessageDao messageDao = new MessageDao();
		 	messageDao.deleteOneMessage(Integer.parseInt(id));
		}
	}
	
	/**传入参数,实现批量删除*/
	public void deleteBatchMessage(String[] ids) {
		//String字符数组转为List数字数组
		List<Integer> list = new ArrayList<Integer>();
		for (String string : ids) {
			list.add(new Integer(string));
		}
		
		if (list != null && list.size()>0) {
		 	MessageDao messageDao = new MessageDao();
		 	messageDao.deleteBatchMessage(list);
		}
	}
}
