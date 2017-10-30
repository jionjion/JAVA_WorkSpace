package mvcdemo.controller;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import mvcdemo.bean.Teacher;
import mvcdemo.bean.User;
import mvcdemo.entity.UserListForm;
import mvcdemo.entity.UserMapForm;
import mvcdemo.entity.UserSetForm;

/***
 *	进行数据绑定的控制层
 */
@Controller
@RequestMapping("/data")
public class DataBing {

	/**数据绑定页面入口*/
	@RequestMapping("/showView")
	public String  showView() {
		return "Data";
	}
	
	/**基本数据类型的绑定*/
	@RequestMapping("/baseTypeInt")
	@ResponseBody	//将返回字符串作为结果		@RequestParam:指定前台参数名称,必须传入
	public String baseTypeInt(@RequestParam(value="param",defaultValue="10") int param) {
		return "收到参数"+param;
	}
	
	/**包装类数据类型的绑定*/
	@RequestMapping("/packTypeInt")
	@ResponseBody	//将返回字符串作为结果		
	public String packTypeInt( Integer param) {
		return "收到参数"+param;	//包装类可以前台不同传递
	}
	
	/**数组类型的绑定*/
	@RequestMapping("/arrType")
	@ResponseBody	//将返回字符串作为结果		@RequestParam:指定前台参数名称,必须传入
	public String arrType(@RequestParam(value="name") String[] arrs) {
		return Arrays.toString(arrs);
	}
	
	/**对象类型的绑定*/
	@RequestMapping("/objType")
	@ResponseBody	//将返回字符串作为结果
	public String objType(Teacher teacher , User user) {
		return teacher.toString() + user.toString();
	}
	/*针对不同对象的相同属性进行赋值*/
	@InitBinder("user")
	public void initUser(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("user.");
	}
	@InitBinder("teacher")
	public void initTeacher(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("teacher.");
	}
	
	/**List容器绑定,绑定的对象必须为包装对象后*/
	@RequestMapping("/listType")
	@ResponseBody	//将返回字符串作为结果		
	public String listType(UserListForm userListForm) {
		return userListForm.toString();
	}
	
	/**Set容器绑定,必须使用初始化后才可以使用,当重写过判断方法后,只能封装指定数量的不同对象*/
	@RequestMapping("/setType")
	@ResponseBody	//将返回字符串作为结果		
	public String setType(UserSetForm userSetForm) {
		return userSetForm.toString();
	}
	
	/**Map容器绑定,绑定的对象必须为包装对象后,传入前必须指定Key*/
	@RequestMapping("/mapType")
	@ResponseBody	//将返回字符串作为结果		
	public String mapType(UserMapForm userMapForm) {
		return userMapForm.toString();
	}
	
	/**JSON对象绑定*/
	@RequestMapping("/jsonType")
	@ResponseBody	//将返回字符串作为结果		@RequestBody:读取字符串对象,完成解析		
	public String jsonType(@RequestBody User user) {
		return user.toString();
	}
	
	/**字符串转为布尔值.在Spring中配置转化器,可以将true,1,yes,on转为true*/
	@RequestMapping("/booleanType")
	@ResponseBody	//将返回字符串作为结果		@RequestBody:读取字符串对象,完成解析		
	public String booleanType(Boolean bool) {
		return bool.toString();
	}
	
	/**字符串绑定为日期*/
	@RequestMapping("/dateOneType")
	@ResponseBody
	public String dateOneType(Date dateOne) {
		return dateOne.toString();
	}
	@InitBinder("dateOne")	//绑定日期格式
	public void initDateOne(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true));
	}
	
	/**通过配置文件,将字符串转为日期*/
	@RequestMapping("/dateTwoType")
	@ResponseBody
	public String dateTwoType(Date dateTwo) {
		return dateTwo.toString();
	}
	
	/**资源获取形式,通过在请求头中修改Content-Type完成资源的表现形式*/
	@RequestMapping(value="/bookType",method=RequestMethod.GET)
	@ResponseBody
	public String bookType(HttpServletRequest request) {
		String contentType = request.getContentType();
		if(contentType == null){
			return "书的默认展现形式";
		}else if(contentType.equals("txt")){
			return "书的txt展现形式";
		}else if(contentType.equals("html")){
			return "书的html展现形式";
		}else{
			return "书的默认展现形式";
		}
	}	
	
	/**RESTful形式URL*/
	@RequestMapping("/{id}/RESTfulType")
	@ResponseBody
	public String RESTfulType(@PathVariable("id") String id) {
		return "收到参数:"+id;
	}
}
