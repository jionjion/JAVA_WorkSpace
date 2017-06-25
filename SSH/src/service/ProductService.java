package service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 	业务逻辑层
 * 	使用注解完成事物*/
import bean.Product;
import dao.ProductDao;
@Transactional
public class ProductService {

	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void save(Product product) {
		productDao.save(product);
		System.out.println("Service的方法执行");
	}
	
}
