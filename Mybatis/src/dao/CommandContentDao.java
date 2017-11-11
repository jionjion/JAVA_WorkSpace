package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import bean.CommandContent;
import db.DBAccess;

/**
 *	批量插入操作
 */
public class CommandContentDao {

	/**
	 * 	使用JDBC完成批量插入操作.
	 */
	public void insertBatchByJdbc(List<CommandContent> contentList) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis", "root", "123456");
			String insertSql = "insert into COMMAND_CONTENT(CONTENT,COMMAND_ID) values(?,?)";
			PreparedStatement statement = connection.prepareStatement(insertSql);
			for(CommandContent content : contentList) {
				statement.setString(1, content.getContent());
				statement.setLong(2, content.getCommandId());
				statement.addBatch();
			}
			statement.executeUpdate();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 *  使用Mybatis完成批量插入差插入操作.
	 */
	public void insertBatch(List<CommandContent> contentList) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			ICommandContent commandContent = sqlSession.getMapper(ICommandContent.class);
			commandContent.insertBatch(contentList);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
	}
}