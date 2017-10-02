package hql;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bean.Grade;
import bean.Student;
@SuppressWarnings({"unchecked","rawtypes"})
public class StudentHQLTest {

	//会话工厂
	private SessionFactory sessionFactory;
	//会话对象
	private Session session;
	//事物对象
	private Transaction transaction;
	
	/*测试方法前执行*/
	@Before
	public void init() {
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
	}
	
	/*测试方法后执行*/
	@After
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*查询所有对象*/
	@Test
	public void testFromStudent() {
		//不需要指定全类型,直接引入类名;缺省由Hibernate框架的映射补全
		String hql = "from Student as s";			//可以使用别名
		Query query = session.createQuery(hql);		//查询结果封装在Query中
		List<Student> students = query.list();		//已知查询结果为List
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询一方所有对象,包含多方对象,程序默认不查询,除非关闭懒加载;这里因为用到,所以都查询*/
	@Test
	public void testFromGrade() {
		String hql = "from bean.Grade";		//可以使用全类型
		Query query = session.createQuery(hql);		//查询结果封装在Query中
		List<Grade> grades = query.list();		//已知查询结果为List
		for (Grade grade : grades) {
			System.out.println("查询到学生:"+grade.toString());
		}
	}
	
	/*以对象数组形式返回一个List*/
	@Test
	public void testSelectClauseObjectArray(){
		String hql = "select s.sid,s.sname from Student s";		//省略as使用别名
		Query query = session.createQuery(hql);
		List<Object[]> list = query.list();
		for (Object[] objects : list) {
			System.out.println("学生编号:"+objects[0]+"\t"+"学生姓名:"+objects[1]);
		}
	}
	
	/*以对象形式返回一个List	当查询的为一个属性时,返回的为对象*/
	@Test
	public void testSelectClauseObject(){
		String hql = "select s.sname from Student s";		//省略as使用别名
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		for (Object object : list) {
			System.out.println("学生姓名:"+object);
		}
	}
	
	/*以List集合形式返回一个List*/
	@Test
	public void testSelectClauseList() {
		String hql = "select new list(s.sid,s.sname) from Student s";
		Query query = session.createQuery(hql);
		List<List> lists = query.list();
		for (List list : lists) {
			System.out.println("学生编号:"+list.get(0)+"\t"+"学生姓名:"+list.get(1));
		}
	}
	
	/*以Map集合形式返回一个List*/
	@Test
	public void testSelectClauseMap() {
		String hql = "select new map(s.sid as id,s.sname) from Student s";
		Query query = session.createQuery(hql);
		List<Map> lists = query.list();
		for (Map map : lists) {		//遍历每一个封装的map对象
			//可以通过别名或者序号获取Map的封装属性,注意传入的是一个String类型的
			System.out.println("学生编号:"+map.get("id")+"\t"+"学生姓名:"+map.get("1"));	
		}
	}
	
	/*以自定义类型返回一个List;需要在持久化类中创建相应的构造器,在select子句中调用定义的构造器*/
	@Test
	public void testSelectClauseConstructor() {
		String hql = "select new Student(s.sid ,s.sname) from Student s";
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("学生编号:"+student.getSid()+"\t"+"学生姓名:"+student.getSname());
		}
	}	
	
	/*查询去重,使用distinct关键字*/
	@Test
	public void testDistinct(){
		String hql = "select distinct s.gender from Student s";
		Query query = session.createQuery(hql);
		List<Object> objects = query.list();
		for (Object object : objects) {
			System.out.println(object);
		}
	}
	
	/*查询条件子句,判断大小关系*/
	@Test
	public void testWhereClauseMathLogic(){
		String hql = "from Student s where s.sid<2";
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,判断是否为空*/
	@Test
	public void testWhereClauseDecideNull(){
		String hql = "from Student s where s.sid is not null";
//		String hql = "from Student s where s.sid <> null";		//也可以
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,范围测试*/
	@Test
	public void testWhereClauseScope(){
		String hql = "from Student s where s.sid between 1 and 2";	//查询是个闭区间
//		String hql = "from Student s where s.sid not between 1 and 2";	
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,罗列条件测试*/
	@Test
	public void testWhereClauseRange(){
		String hql = "from Student s where s.sid in (1,3,5)";
//		String hql = "from Student s where s.sid not in (1,3,5)";
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生"+student.toString());
		}
	}
	
	/*查询条件子句,模糊查询,需要用单引号对其进行测试*/
	@Test
	public void testWhereClauseLike(){
//		String hql = "from Student s where s.sname like '张_'";
		String hql = "from Student s where s.sname like '%'";	//全部查询
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,逻辑查询*/
	@Test
	public void testWhereClauseLogic(){
//		String hql = "from Student s where s.sname like '张_' and s.sid=2";		//逻辑与
		String hql = "from Student s where s.sname like '张_' or s.sid=1";		//逻辑或
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,集合运算,对一对多的多方集合进行查询是否为空*/
	@Test
	public void testWhereClauseAssemble(){
		String hql = "from Grade g where g.pupils is not empty";		
		Query query = session.createQuery(hql);
		List<Grade> grades = query.list();
		for (Grade grade : grades) {
			System.out.println("查询到学生:"+grade.toString());
		}
	}
	
	/*查询条件子句,四则运算*/
	@Test
	public void testWhereClauseArithmetic(){
		String hql = "from Student s where s.sid < (2+1)*3";	
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,单个查询,要求查询结果为单一或者为空*/
	@Test
	public void testWhereClauseSingle(){
		String hql = "from Student s where s.sid =1";		//注意条件的控制
		Query query = session.createQuery(hql);
		Student student = (Student) query.uniqueResult();
		System.out.println("查询到学生:"+student.toString());
	}
	
	/*查询条件子句,进行排序 desc:降序	asc:升序*/
	@Test
	public void testWhereClauseOrder(){
		String hql = "from Student s order by s.sid asc,s.birthday desc";	//多个条件间用逗号分隔	
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
}
