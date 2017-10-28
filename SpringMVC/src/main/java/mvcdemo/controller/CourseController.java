package mvcdemo.controller;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import mvcdemo.bean.Course;
import mvcdemo.service.CourseService;

/**课程服务类的控制层*/
@Controller
@RequestMapping("/course")
public class CourseController {

	//日志记录
	private static Logger log = LoggerFactory.getLogger(CourseController.class);
	
	//服务类
	@Autowired
	private CourseService courseService;

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	/**	本方法对URL:http://localhost:8080/SpringMVC/course/viewOne?id=1
	 * 	只用GET请求
	 * 	使用Model的形式传递参数*/
	@RequestMapping(value="/viewOne",method=RequestMethod.GET)
	public String viewCourseOne(@RequestParam("id") int id,Model model) {
		System.out.println("收到参数"+id);
		Course course = courseService.getCourseById(id);
		model.addAttribute(course);	//属性名称与设置时一致
		return "Course";
	}
	
	/**	本方法对URL:http://localhost:8080/SpringMVC/course/viewTwo/253
	 * 	只用GET请求
	 * 	使用Map的形式传递参数*/
	@RequestMapping(value="/viewTwo/{id}",method=RequestMethod.GET)
	public String viewCourseTwo(@PathVariable("id") int id,Map<String, Object> model) {
		System.out.println("收到参数"+id);
		Course course = courseService.getCourseById(id);
		model.put("course",course);
		return "Course";
	}
	
	/**	本方法对URL:http://localhost:8080/SpringMVC/course/viewThree?id=266
	 * 	只用GET请求
	 * 	使用Request的形式传递参数*/
	@RequestMapping(value="/viewThree",method=RequestMethod.GET)
	public String viewCourseThree(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("收到参数"+id);
		Course course = courseService.getCourseById(id);
		request.setAttribute("course", course);
		return "Course";
	}
	
	/**跳转到添加页面,add为参数,可以不写	URL:http://localhost:8080/SpringMVC/course/admin?add
	 * */
	@RequestMapping(value="/admin",method=RequestMethod.GET,params="add")
	public String createCourse() {
		
		//转发
		return "AddCourse";
		
	}
	
	/**进行保存的页面*/
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveCourse(@ModelAttribute Course course ) {
		
		System.out.println("从表单获取对象"+course.toString());
		//进行保存....
		course.setId(123);
		//重定向中,注意不需要再写类上的映射
		return "redirect:viewTwo/"+course.getId();
		//转发请求
//		return "forward:viewTwo/"+course.getId();
	}
	
	/**跳转进入文件上传下载**/
	@RequestMapping(value="/showUpload",method=RequestMethod.GET)
	public String showUploadPage() {
		return "Upload";
	}
	
	/**	进行文件上传下载 URL:http://localhost:8080/SpringMVC/course/showUpload
	 * 	使用Spring提供的类
	 *  **/
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String UploadPage(@RequestParam(value="file") MultipartFile file) throws IOException {
		String path = "F:\\JAVA_WorkSpace\\SpringMVC\\src\\main\\webapp\\temp\\"
						+System.currentTimeMillis()+file.getOriginalFilename();
		if( !file.isEmpty()){
			System.out.println("获取并处理文件"+file.getOriginalFilename());
		}
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
		return "Success";
	}
	
	/**对JSON格式的进行支持
	 * URL:http://localhost:8080/SpringMVC/course/jsonOne/2
	 * **/
	@RequestMapping(value="/jsonOne/{id}",method=RequestMethod.GET)
	public @ResponseBody Course getCourseByJsonOne(@PathVariable int id){
		return courseService.getCourseById(id);
	}
	
	/**	对JSON格式进行支持
	 * 	URL:http://localhost:8080/SpringMVC/course/jsonTwo/2
	 * **/
	@RequestMapping(value="/jsonTwo/{id}")
	public ResponseEntity<Course> getCourseByJsonTwo(@PathVariable int id) {
		Course course = courseService.getCourseById(id);
		//返回一个泛型实体类,以及Http的状态
		return new ResponseEntity<Course>(course,HttpStatus.OK);
	}
}
