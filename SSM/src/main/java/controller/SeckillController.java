package controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bean.Seckill;
import dto.Exposer;
import dto.SeckillExecution;
import dto.SeckillResult;
import enums.SeckillStatEnum;
import exception.RepeatKillException;
import exception.SeckillCloseException;
import exception.SeckillException;
import service.SeckillService;

@Controller
@RequestMapping("/seckill")	//URL:/模块/资源/{id}/细分
public class SeckillController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	
	/**
	 * 秒杀的商品列表页面
	 * @param model数据模型
	 * @return 跳转页面
	 * URL: 
	 */
	@RequestMapping(value="/seckillList",method=RequestMethod.GET)
	public String getSeckillList(Model model) {
		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute("seckillList",seckillList);
		//seckillList.jsp + model = ModelAndView
		return "seckill/seckillList";	// 对应文件/WEB-INF/jsp/seckill/seckillList.jsp	
	}
	
	
	/**
	 * 商品的详情页面
	 * @param seckillId 使用
	 * @param model数据模型
	 * @return 跳转页面
	 */
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String seckillDetail(@PathVariable("seckillId") Long seckillId,Model model) {
		if (seckillId == null) {
			//URL中不存在参数,则重定向
			return "redirect:/seckill/seckillList";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			//未获取秒杀商品,转发
			return "forward:/seckill/seckillList";
		}
		model.addAttribute("seckill",seckill);
		return "seckill/seckillDetail";
	}

	
	/**
	 * 将秒杀暴露接口,通过JSON数据传出
	 * @return 
	 */
	@RequestMapping(value="/{seckillId}/exposer",method=RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	@ResponseBody	//返回为JSON格式
	public SeckillResult<Exposer> exposer(Long serckillId) {
		SeckillResult<Exposer> seckillResult = null;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(serckillId);
			seckillResult = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage());
			seckillResult = new SeckillResult<>(false, e.getMessage());
		}
		return seckillResult; 
	}
	
	
	/**
	 * 执行秒杀过程
	 * @param seckillId 秒杀物品的ID
	 * @param userPhone 用户的电话
	 * @param md5 :携带的消息摘要
	 * @return :秒杀结果,并封装为JSON格式
	 */
	public SeckillResult<SeckillExecution> execute(	@PathVariable("seckillId") Long seckillId,
													@CookieValue(value = "userPhone", required = false) Long userPhone,
													@PathVariable("md5") String md5) {
		if (userPhone == null) {
			//电话为空,未注册
			return new SeckillResult<SeckillExecution>(false, "当前未注册");
		}
		
		try {
			//获得秒杀执行结果
			SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (RepeatKillException e) {
			//重复秒杀,逻辑异常,不需要日志记录
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(false, seckillExecution);
		} catch (SeckillCloseException e) {
			//秒杀关闭,逻辑异常,不需要日志记录
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END_KILL);
			return new SeckillResult<SeckillExecution>(false, seckillExecution);
		} catch (SeckillException e) {
			//其他异常,需要日志
			logger.error(e.getMessage());
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(false, seckillExecution);
		}
	}
	
	
	/**
	 * 返回服务器的系统时间
	 * @return 服务器的系统时间
	 */
	public SeckillResult<Long> getNowTime(){
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	} 
}