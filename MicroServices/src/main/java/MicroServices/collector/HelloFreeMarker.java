package MicroServices.collector;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import MicroServices.bean.User;
import MicroServices.tool.SumListMethod;

@Controller
@RequestMapping("/freemarker")
public class HelloFreeMarker {

	/**	该模块的显示页面
	 * 	调用freemaker模板
	 *  URL: http://localhost:8080/MicroServices/freemarker/hello
	 * */
    @RequestMapping("hello")
    public String hello(Model model){
    	System.out.println("----------进入控制层-------------");
        model.addAttribute("message", "freemarker");
        return "hello";
    }
	
    /**	用户展示页面
     * 	URL: http://localhost:8080/MicroServices/freemarker/userList  
     * */
    @GetMapping("/userList")
    public ModelAndView userList() {
		ModelAndView modelAndView = new ModelAndView("user");
		//基本数据类型
		int intType = 123;
		long longType = 12345678910l;
		double doubleType = 3.141592678d;		//精度由配置文件指定
		boolean booleanType = true;
		modelAndView.addObject("intType",intType);
		modelAndView.addObject("longType",longType);
		modelAndView.addObject("doubleType",doubleType);
		modelAndView.addObject("booleanType", booleanType);
		
		//日期类型
		Date dateType = new Date(new java.util.Date().getTime());	//对SQL的Date类型进行转换
		modelAndView.addObject("dateType", dateType);
		
		//引用类型
		User user = new User();
		user.setUsername("Jion");
		modelAndView.addObject("user",user);
		
		//字符串数据类型
		modelAndView.addObject("message","后台消息");
		modelAndView.addObject("briefType", "<em>富文本字符串</em>");
		
		//List集合数据类型
		List<String> listType = new ArrayList<String>();
		listType.add("元素一");
		listType.add("元素二");
		listType.add("元素三");
		modelAndView.addObject("listType", listType);
		
		//Map集合数据类型,每次获取的时候顺序不一致
		Map<String, String> mapType = new HashMap<String,String>();
		mapType.put("keyOne", "元素一");
		mapType.put("keyTwo", "元素二");
		mapType.put("keyThree", "元素三");
		modelAndView.addObject("mapType", mapType);
		
		//自定义函数,求和数列
		SumListMethod sumListMethod = new SumListMethod();
		modelAndView.addObject("sum_list",sumListMethod);
    	return modelAndView;
	}
}
