package bean;
/**
 * 	模拟商品类*/
public class Product {

	private Integer id;
	
	private Double price;
	
	private String pname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Product() {
		super();
	}

	public Product(Integer id, Double price, String pname) {
		super();
		this.id = id;
		this.price = price;
		this.pname = pname;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", price=" + price + ", pname=" + pname + "]";
	}
	
}
