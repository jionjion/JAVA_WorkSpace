package MicroServices.tool;
/**返回结果的工具类*/

import MicroServices.domain.ResultMessage;

public class ResultUtil {

	/**成功返回结果*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ResultMessage success(Object object) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setCode(200);
		resultMessage.setMessage("成功");
		resultMessage.setDate(object);
		return resultMessage;
	}
	
	/**成功且不返回结果*/
	@SuppressWarnings("rawtypes")
	public static ResultMessage success() {
		return success(null);
	}
	
	/**失败返回失败结果*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResultMessage error(Integer code,String message) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setCode(code);
		resultMessage.setMessage(message);
		resultMessage.setDate(null);
		return resultMessage;
	}
}
