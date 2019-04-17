package top.jionjion.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jion
 *  主页显示
 */
@Controller
@RequestMapping(value = {"/index"})
public class MainPageController {

    /** 获得主页 */
    @RequestMapping("/")
    public String getIndex(){
        return "<h1>主页</h1>";
    }

}
