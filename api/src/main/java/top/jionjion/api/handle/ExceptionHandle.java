package top.jionjion.api.handle;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.jionjion.api.dto.ApiResultDto;
import top.jionjion.api.dto.DataDto;
import top.jionjion.api.dto.InfoDto;

/**
 * @author Jion
 *  异常处理类,将主要的异常信息进行处理.
 *  异常以JSON格式进行输出
 *     暂不启用
 */

//@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResultDto handle(Exception e) {
        return new ApiResultDto(
                new DataDto(e.getMessage()),
                InfoDto.getErrorInfoDto(e.getMessage()));
    }

}
