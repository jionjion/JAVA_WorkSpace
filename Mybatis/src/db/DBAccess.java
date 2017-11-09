package db;

import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/***
 *	访问数据库的类
 */
public class DBAccess {

	/**获取SQLSession对象的类*/
	public SqlSession getSqlSession() throws Exception{
		//1.读取配置文件
		Reader reader = Resources.getResourceAsReader("config/Configuration.xml");
		//2.创建SQLSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		//3.通过SQLSessionFactory创建一个数据库会话
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//返回一个会话实例
		return sqlSession;
	}
}
