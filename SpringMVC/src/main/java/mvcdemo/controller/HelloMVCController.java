package mvcdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**控制器层**/

@Controller
@RequestMapping("/hello")
public class HelloMVCController {

	@RequestMapping("/mvc")
	public String helloMVC() {
		System.out.println("进入控制器");
		return "HelloWorld";
	}
}
