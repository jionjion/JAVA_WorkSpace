package top.jionjion.jta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页
 *
 * @author Jion
 */
@RestController
@RequestMapping("/api")
public class HomeResource {

    /**
     *  http://127.0.0.1:8080/api/hello
     */
    @GetMapping("/hello")
    public String sayHello(){
        return "Hello World";
    }
}
