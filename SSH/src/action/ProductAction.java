package action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.Product;
/***
 * 	商品的Action的类
 * 	使用插件包,默认开启了自动装配,不需要在私有属性或者set()方法上使用@AutoWire
 * 
 */
import service.ProductService;

public class ProductAction extends ActionSupport implements ModelDriven<Product>{

	private static final long serialVersionUID = 1L;
	
	/*使用模型驱动装配传入参数*/
	private Product product = new Product();
	@Override
	public Product getModel() {
		return product;
	}
	
	/*使用插件包,完成自动装配*/
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/**保存商品的方法,注意方法名和Struts2的配置文件的一致*/
	public String save() {
		
		System.out.println("Action执行了:"+product.toString());
		productService.save(product);
		//无返回页面
		return this.NONE;
	}
	
}
