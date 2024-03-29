package service;

import java.util.List;
import java.util.Random;

import bean.Command;
import bean.CommandContent;
import bean.Constant;
import bean.Message;
import dao.CommandDao;
import dao.MessageDao;

/**自动回复的查询服务*/
public class QueryService {

	/**根据关键字获得查询结果,作为自动回复返回.使用单表查询*/
	public String queryByCommand(String command) {
		MessageDao messageDao = new MessageDao();
		
		/*帮助,查询所有*/
		if(Constant.HELP_CONTENT.equals(command)){	//回复关键字为'帮助',返回所有命令
			List<Message> messagesList = messageDao.queryMessagesList(null, null);
			StringBuilder stringBuilder = new StringBuilder();	//用于拼接返回字符串
			for (Message message : messagesList) {		
				stringBuilder.append("回复["+message.getCommand()+"]查看:"+message.getDescription()+"</br>");		//返回所有指令
			}
			return stringBuilder.toString();
		}
		
		/*查询条件中的一条*/
		List<Message> messagesList = messageDao.queryMessagesList(command, null);
		if (messagesList != null && messagesList.size()>0) {
			int size = messagesList.size();
			Random random = new Random();	//随机数
			size = random.nextInt(size);
			return messagesList.get(size).getContent();
		}
		return Constant.NO_MATCHING_CONTENT;
	}
	
	/**根据关键字获得查询结果,作为自动回复返回.使用多表查询*/
	public String queryByCommandTwo(String command) {

		CommandDao commandDao = new CommandDao();
		/*帮助,查询所有*/
		if(Constant.HELP_CONTENT.equals(command)){	//回复关键字为'帮助',返回所有命令
			List<Command> messagesList = commandDao.queryCommandsList(null, null);
			StringBuilder stringBuilder = new StringBuilder();	//用于拼接返回字符串
			for (Command message : messagesList) {		
				stringBuilder.append("回复["+message.getName()+"]查看:"+message.getDescription()+"</br>");		//返回所有指令
			}
			return stringBuilder.toString();
		}
		
		/*查询条件中的一条*/
		List<Command> commandsList = commandDao.queryCommandsList(command, null);
		if (commandsList != null && commandsList.size()>0) {
			//从表集合
			List<CommandContent> commandContents = commandsList.get(0).getContentsList();
			int size = commandContents.size();
			Random random = new Random();	//随机数
			size = random.nextInt(size);
			return commandContents.get(size).getContent();
		}
		return Constant.NO_MATCHING_CONTENT;
	}

}
