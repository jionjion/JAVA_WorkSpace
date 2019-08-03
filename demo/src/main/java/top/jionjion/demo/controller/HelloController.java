package top.jionjion.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Jion
 */
@Controller
public class HelloController {

    @GetMapping(value = "/hello")
    @ResponseBody
    public String hello(){
        return "hello Word!";
    }


    @GetMapping(value = "success")
    public String success(Map<String,Object> model){
        model.put("hello","<h1>你好</h1>");

        model.put("users", Arrays.asList("Jion", "Bao", "Arise"));


        // classpath:/templates/success.html
        return "success";
    }
}
