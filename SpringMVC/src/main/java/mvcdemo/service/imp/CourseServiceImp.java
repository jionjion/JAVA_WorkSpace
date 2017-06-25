package mvcdemo.service.imp;
import org.springframework.stereotype.Service;

/**课程服务类的实现类*/
import mvcdemo.bean.Course;
import mvcdemo.service.CourseService;

@Service("courseService")	//使用注解创建Bean,并指定名称;缺省则自动创建
public class CourseServiceImp implements CourseService {

	/**根据ID查询课程*/
	public Course getCourseById(int id) {
		
		Course course = new Course();
		course.setId(1);
		course.setName("课程一");
		return course;
	}

}
