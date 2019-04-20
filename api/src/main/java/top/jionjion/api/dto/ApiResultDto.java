package top.jionjion.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.io.Serializable;

/**
 * @author Jion
 *  {
 *      date:[{
 *        A:"jion",
 *        B:"12",
 *        C:"1994-04-12"
 *      }],
 *      auth:{
 *          username:"",
 *          token:""
 *      }
 *      status:Success
 *      code:200
 *  }
 */
@Getter
public class ApiResultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 数据 */
    @JsonProperty(value = "data")
    private DataDto dataDto;

    /** 认证 */
    @JsonProperty(value = "auth")
    private AuthDto authDto;

    /** 响应信息 */
    @JsonProperty(value = "_info")
    private InfoDto infoDto;

    /** 私有构造方法,拒绝被直接调用 */
    private ApiResultDto(){}

    /** 构造方法,默认状态为处理成功  */
    public ApiResultDto(DataDto dataDto){

        this(dataDto, InfoDto.getSuccessInfoDto());
    }

    /** 构造方法,根据Session中的信息进行验证 */
    public ApiResultDto(DataDto dataDto, InfoDto infoDto){
        this.dataDto = dataDto;
        this.infoDto = infoDto;
        //@TODO 从Sesion中获取认证信息,并替换来宾账号
        this.authDto = AuthDto.getNoAuthUser();
    }

    /** 构造方法 */
    public ApiResultDto(DataDto dataDto, InfoDto infoDto, AuthDto authDto){
        this.dataDto = dataDto;
        this.authDto = authDto;
        this.infoDto = infoDto;
    }
}
