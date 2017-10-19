package dao;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/***
 * 	商品的管理类
 * 	使用Spring的所带的HibernateDaoSupport
 * 	在Spring的xml中注入SessionFactory后可以直接使用
 */
import bean.Product;

public class ProductDao extends HibernateDaoSupport {

	public void save(Product product) {
		
		this.getHibernateTemplate().save(product);
		System.out.println("Dao层中保存了对象");
	}

}
