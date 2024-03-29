package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import bean.Message;
import db.DBAccess;

/**与Message表相关的*/
public class MessageDao {

	/**使用JDBC实现数据库连接查询*/
	public List<Message> queryMessagesListByJDBC(String command,String description ) {
		
		//连接数据库
		List<String> paramList = new ArrayList<>();	//参数变量临时保存
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//创建连接
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis", "root", "123456");
			//使用可变的字符串书写SQL语句
			StringBuilder stringBuilder = new StringBuilder(" select id,command,description,content from message where 1=1 ");
			//对前台传入参数进行判断,不为空切不为空串时,则存入List中,计数的同时拼写SQL文
			if (command !=null && !"".equals(command.trim())) {
				//如果条件不为空
				stringBuilder.append(" and command = ? ");	//拼写SQL文字
				paramList.add(command);						//存入List中,0位置则是command的条件查询
			}
			if (description !=null && !"".equals(description.trim())) {
				//如果条件不为空
				stringBuilder.append("and description like '%' ? '%'");		//MySQL中间有空格,进行模糊查询
				paramList.add(description);
			}
			PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());	//预加载的SQL执行对象,防止注入
			for(int i=0 ; i<paramList.size() ; i++){				//预编译SQL语句
				preparedStatement.setString(i+1, paramList.get(i));	//SQL语句的?由1开始,而数组获取从0开始
			}
			ResultSet resultSet = preparedStatement.executeQuery();	//执行查询,返回查询结果
			//封装进入List
			List<Message> messagesList = new ArrayList<Message>();	
			while (resultSet.next()) {
				Message message = new Message(resultSet.getInt("id"), resultSet.getString("command"), resultSet.getString("description"), resultSet.getString("content"));
				messagesList.add(message);
			}
			return messagesList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	//如果失败,则返回空
	}
	
	/**使用Mybatis实现对数据库的访问查询*/
	public List<Message> queryMessagesList(String command,String description ) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQLSession执行SQL语句,直接在方法中追加传入参数.参数只能传一个.多个参数封装为Map或者对象
			//封装对象,传入查询
			Message message = new Message();
			message.setCommand(command);
			message.setDescription(description);
			List<Message> list = sqlSession.selectList("Message.queryMessagesList",message);
			return list;
		} catch (Exception e) {
			System.err.println("SQLSession连接获取出现问题");
			e.printStackTrace();
		} finally {
			//session关闭
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return null;
	}
	
	/**使用Mybatis的接口式编程实现对数据库的访问查询*/
	public List<Message> queryMessagesListByInterface (Map<String, Object> parameters) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQLSession执行SQL语句,直接在方法中追加传入参数.参数只能传一个.多个参数封装为Map或者对象
			
			//面向接口的编程
			IMessage iMessage = sqlSession.getMapper(IMessage.class);
//			List<Message> list = iMessage.queryMessagesList(parameters);	//传入Map集合,自定义分页
			List<Message> list = iMessage.queryMessagesListByPageInterceptor(parameters);	//传入Map集合,使用拦截器完成分页
			return list;
		} catch (Exception e) {
			System.err.println("SQLSession连接获取出现问题");
			e.printStackTrace();
		} finally {
			//session关闭
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return null;
	}
	
	/**使用Mybatis删除单条数据*/
	public void deleteOneMessage(int id) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			sqlSession.delete("Message.deleteOneMessage",id);
			sqlSession.commit(); 	//事物需要手动提交,Mybatis默认事务管理不自动提交,需要手动提交,构成一个事务
		} catch (Exception e) {
			System.err.println("SQLSession连接获取出现问题");
			e.printStackTrace();
		} finally {
			//session关闭
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}
	
	/**使用Mybatis删除多条数据*/
	public void deleteBatchMessage(List<Integer> ids) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			sqlSession.delete("Message.deleteBatchMessage",ids);
			sqlSession.commit(); 	//事物需要手动提交,Mybatis默认事务管理不自动提交,需要手动提交,构成一个事务
		} catch (Exception e) {
			System.err.println("SQLSession连接获取出现问题");
			e.printStackTrace();
		} finally {
			//session关闭
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
	}

	/**通过面向接口的编程,完成模糊消息的总条数查询*/
	public int count(Message message) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		int totalNumber = 0; //总页数
		try {
			sqlSession = dbAccess.getSqlSession();
			IMessage iMessage = sqlSession.getMapper(IMessage.class);
			totalNumber = iMessage.count(message);
			
		} catch (Exception e) {
			System.err.println("SQLSession连接获取出现问题");
			e.printStackTrace();
		} finally {
			//session关闭
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return totalNumber;
	}
	
}
