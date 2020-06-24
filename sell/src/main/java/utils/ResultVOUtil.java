package utils;

import top.jionjion.enums.ResultStatusEnum;
import top.jionjion.vo.ResultVO;

/**
 *  @author Jion
 *      返回状态工具包
 */
public class ResultVOUtil {

    /** 返回Success状态接口返回 */
    public static ResultVO<Object> success(Object object){
        ResultVO<Object>  resultVO = new ResultVO<>();
        resultVO.setCode(ResultStatusEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultStatusEnum.SUCCESS.getMgs());
        resultVO.setData(object);
        return resultVO;
    }

    /** 返回Success,但无返数据 */
    public static ResultVO<Object> success(){
        return success(null);
    }

    /** 返回Error,无数据 */
    public static ResultVO<Object> error(){
        ResultVO<Object>  resultVO = new ResultVO<>();
        resultVO.setCode(ResultStatusEnum.ERROR.getCode());
        resultVO.setMsg(ResultStatusEnum.ERROR.getMgs());
        return resultVO;
    }
}
