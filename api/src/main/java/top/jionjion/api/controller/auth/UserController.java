package top.jionjion.api.controller.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Password;
import top.jionjion.api.bean.auth.user.User;
import top.jionjion.api.dto.ApiResultDto;
import top.jionjion.api.dto.DataDto;
import top.jionjion.api.enums.ApiResultStatus;
import top.jionjion.api.service.auth.user.UserService;

import java.util.Objects;

/**
 * @author Jion
 *  用户认证信息
 */
@RestController
@RequestMapping("/auth/user")
public class UserController {

    //@TODO 用户信息模块
    //@TODO 接口文档

    @Autowired
    private UserService userService;

    /** 通过用户名,密码,获得最新的信息 */
    @PostMapping(value = {"/info"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResultDto getUser(@RequestBody(required = false) User user){
        // 参数不存在,返回访客认证
        if( Objects.isNull(user) || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())){
            //@TODO
            ApiResultDto resultDto = new ApiResultDto(null, ApiResultStatus.WARN);
            return resultDto;
        }

        user = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        // 用户不存在,返回错误信息
        if(Objects.isNull(user)){
            //@TODO
        }
        return null ;
    }

    /** 新增认证信息 */
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResultDto addUser(@RequestBody(required = false) User user){
        // @TODO 验证信息是否完全 username + password
        if(Objects.isNull(user)){
            return new ApiResultDto(null,ApiResultStatus.ERROR);
        }

        // 保存
        User user2 = userService.save(user);

        return new ApiResultDto(new DataDto(user2),ApiResultStatus.SUCCESS);
    }
}
