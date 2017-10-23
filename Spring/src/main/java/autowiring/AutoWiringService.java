package autowiring;
/**自动装配的Service层
 * 1.私有bean的属性,创建set方法,完成自动注入,需要配置xml
 * 2.通过构造器,传入Dao层,完成注入,需要配置xml
 * */
public class AutoWiringService {
	
	private AutoWiringDAO autoWiringDAO;
	
	
	
	public AutoWiringService(AutoWiringDAO autoWiringDAO) {
		System.out.println("通过构造器,自动注入Dao层");
		this.autoWiringDAO = autoWiringDAO;
	}

	
	public void say(String word) {
		this.autoWiringDAO.say(word);
	}
	
	public void setAutoWiringDAO(AutoWiringDAO autoWiringDAO) {
		System.out.println("通过set方法,自动注入Dao层");
		this.autoWiringDAO = autoWiringDAO;
	}
	


}
