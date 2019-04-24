package top.jionjion.api.handle;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import top.jionjion.api.dto.ApiResultDto;
import top.jionjion.api.dto.DataDto;
import top.jionjion.api.dto.InfoDto;
import top.jionjion.api.exception.ApiException;

/**
 * @author Jion
 *  全局的API接口的异常处理
 */
@ControllerAdvice
public class ApiExceptionHandle {

    @ExceptionHandler(value = ApiException.class)
    @ResponseBody
    public ApiResultDto handle(ApiException e) {

        // 异常细分



        return new ApiResultDto(
                new DataDto(e.getMessage()),
                InfoDto.getErrorInfoDto(e.getMessage()));
    }
}
