package mvcdemo.controller;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
	public void reloadCode(HttpServletRequest request , HttpServletResponse response) throws IOException {
		
		//创建图片缓存
		BufferedImage image = new BufferedImage(82, 20, BufferedImage.TYPE_INT_RGB);
		//创建画板
		Graphics2D graphics =  image.createGraphics();
		//创建颜色
		Color backColor = new Color(217, 237, 237);
		//画板设置背景颜色
		graphics.setColor(backColor);
		//绘制边框
		graphics.fillRect(0, 0, 82, 20);
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
			//字体样式
			Font font = new Font("仿宋", Font.BOLD, 18);
			graphics.setFont(font);
			int index = random.nextInt(ch.length);
			//绘制字符串
			//[0,length)
			//指定绘制的绘制,注意间隔为10
			graphics.drawString(Character.toString(ch[index]), 20*i + 7, 15);
			stringBuffer.append(ch[index]);
		}
		//将生成的信息存入Session中,等待用户校验
		request.getSession().setAttribute("codeValue", stringBuffer.toString());
		ImageIO.write(image, "JPG", response.getOutputStream());
		System.out.println("刷新");
	}
	
	
	/**
	 * 	验证码登录
	 */
	@RequestMapping(value="login" ,method = RequestMethod.POST)
	public String Login(HttpServletRequest request , HttpServletResponse response) throws IOException {
		//从Session中获得验证码
		String sessionCode = (String) request.getSession().getAttribute("codeValue");
		//从请求中获得输入验证码
		String requestCode = (String) request.getParameter("imageCode");
		if (StringUtils.equalsIgnoreCase(sessionCode, requestCode)) {
			return "Success";
		}
		return "Error";
	}
}
