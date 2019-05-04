package top.jionjion.api.controller.auth;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import top.jionjion.api.bean.auth.user.User;
import top.jionjion.api.config.Warning;
import top.jionjion.api.config.Wrong;
import top.jionjion.api.dto.ApiResultDto;
import top.jionjion.api.dto.DataDto;
import top.jionjion.api.dto.InfoDto;
import top.jionjion.api.enums.ApiResultStatus;
import top.jionjion.api.exception.UserException;
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

    /** 警告信息 */
    @Autowired
    private Warning warning;

    /** 异常信息 */
    @Autowired
    private Wrong wrong;

    /** 通过用户名,密码,获得最新的信息 */
    @PostMapping(value = {"/info"},produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResultDto getUser(@RequestBody(required = false) User user){
        // 参数不存在,返回访客认证
        if( Objects.isNull(user) || StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())){
            ApiResultDto resultDto = new ApiResultDto(null,new InfoDto(ApiResultStatus.WARN,warning.getUserInfoNotEnough()));
            return resultDto;
        }

        user = userService.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        // 用户不存在,返回错误信息
        if(Objects.isNull(user)){
            throw new UserException(wrong.getThisUserNoRegister());
        }
        // @TODO 使用Bean拷贝,仅将用户名,ID拷贝
        User userReturn = new User();
        BeanUtils.copyProperties(user, userReturn, "id","uuid","password");
        return new ApiResultDto(new DataDto(userReturn)) ;
    }

    /** 新增认证信息 */
    @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ApiResultDto addUser(@RequestBody(required = false) User user){
        // @TODO 验证信息是否完全: username + password
        if(Objects.isNull(user)){
            return new ApiResultDto(null,InfoDto.getErrorInfoDto(warning.getUserInfoNotEnough()));
        }

        // 保存
        User user2 = userService.save(user);

        return new ApiResultDto(new DataDto(user2),InfoDto.getSuccessInfoDto());
    }

    /** 当前前段控制器的异常处理类 */
    @ExceptionHandler()
    public ApiResultDto exceptioHandler(UserException e){

        return new ApiResultDto(null,InfoDto.getErrorInfoDto(e.getMessage()));
    }
}
