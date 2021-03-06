package dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import bean.Command;
import db.DBAccess;

/**数据库命令主表对应的Dao层,实现对多表的联合维护*/
public class CommandDao {

	/**使用Mybatis实现对数据库的访问查询,查询指令列表*/
	public List<Command> queryCommandsList(String name,String description ) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQLSession执行SQL语句,直接在方法中追加传入参数.参数只能传一个.多个参数封装为Map或者对象
			//封装对象,传入查询
			Command command = new Command();
			command.setName(name);
			command.setDescription(description);
			List<Command> list = sqlSession.selectList("Command.queryCommandsList",command);
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
}
