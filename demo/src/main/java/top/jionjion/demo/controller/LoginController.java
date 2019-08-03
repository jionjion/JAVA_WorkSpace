package top.jionjion.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author Jion
 */
@Controller
public class LoginController {

    @RequestMapping(value = {"/user/login"}, method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Map<String, Object> model) {

        if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
            // 存入Session
            session.setAttribute("username", username);
            // 成功.重定向页面
            return "redirect:/dashboard";
        }
        // 失败.
        model.put("msg","用户名密码错误!");
        return "login";
    }
}
