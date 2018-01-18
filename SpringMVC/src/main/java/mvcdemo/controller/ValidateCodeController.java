package mvcdemo.controller;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 	图片验证码
 * @author JionJion
 */
@Controller
@RequestMapping("/code")
public class ValidateCodeController {
	
	/** 跳转图片验证码页面 		 <br>
	 * 	URL:http://localhost:8080/SpringMVC/code/codePage */
	@RequestMapping(value="codePage",method=RequestMethod.GET)
	public String imageFileUploadShow() {
		
		return "CodePage";
	}	
		
	
	/**
	 * 	重新刷新验证码
	 */
	@RequestMapping(value="reloadCode" ,method = RequestMethod.GET)
	public void reloadCode(HttpServletRequest request) {
		
		//创建图片缓存
		BufferedImage image = new BufferedImage(150, 30, BufferedImage.TYPE_INT_RGB);
		//创建画板
		Graphics2D graphics =  image.createGraphics();
		//创建颜色
		Color backColor = new Color(238, 238, 238, 1);
		//画板设置背景颜色
		graphics.setColor(backColor);
		//绘制边框
		graphics.fillRect(0, 0, 80, 30);
		//绘制内容
		
		//数字字符串
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		//保存字符串
		StringBuffer stringBuffer = new StringBuffer();
		//随机获取四个字符
		Random random = new Random();
		for(int i=0 ; i<4 ; i++) {
			//随机颜色
			graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
			//绘制字符串
			//[0,length)
			int index = random.nextInt(ch.length);
			//指定绘制的绘制,注意间隔为10
			graphics.drawString(Character.toString(ch[index]), 30*i + 10, 30);
			stringBuffer.append(ch[index]);
		}
		//将生成的信息存入Session中,等待用户校验
		request.getSession().setAttribute("codeValue", stringBuffer.toString());
		System.out.println("刷新");
	}
}
