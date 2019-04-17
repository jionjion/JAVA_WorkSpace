package top.jionjion.api.dto;

import lombok.Data;

import java.util.Objects;

/**
 * @author Jion
 *  用户认证信息
 */
@Data
public class AuthDto {

    /** 未认证的用户信息 */
    private static AuthDto noAuthUser;

    /** 未认证的用户信息,单例模式 */
    static AuthDto getNoAuthUser(){
        if(Objects.isNull(noAuthUser)){
            noAuthUser = new AuthDto();
            //@TODO 默认值来源暂定
            noAuthUser.setUsername("GUEST");
            noAuthUser.setToken("00000000000000000000000000000000");
            return noAuthUser;
        }else{
            return noAuthUser;
        }
    }
    /** 用户名 */
    private String username;

    /** 令牌 */
    private String token;

    /** 构造方法 */
    private AuthDto(){}

    /** 构造方法 */
    public AuthDto(String username, String token){
        this.username = username;
        this.token = token;
    }
}
