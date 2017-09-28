package javaEail.until;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

/**邮件的工具类*/
public class EmailUntil {

	/**将发件人的Email地址传进去,以及UUD传入,完成验证*/
	public static void sendEmail(String to ,String code) {
		
		try {
			//创建连接对象
			//创建配置服务信息
			//QQ邮箱的授权码  lulyxaugngsfhhad
			Properties props = new Properties();
			// 开启debug调试
			props.setProperty("mail.debug", "true");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			// 设置邮件服务器主机名
			props.setProperty("mail.host", "smtp.qq.com");
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");
			
			//SSH加密协议
		    MailSSLSocketFactory sf = new MailSSLSocketFactory();
		    sf.setTrustAllHosts(true);
		    props.put("mail.smtp.ssl.enable", "true");
		    props.put("mail.smtp.ssl.socketFactory", sf);
		    
			Session session = Session.getInstance(props);
			//创建邮件对象
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("1434501783@qq.com"));			//发件人
			message.setRecipient(RecipientType.TO, new InternetAddress(to));	//发送类型,收件人	
			message.setSubject("主题:这是我的JAVA测试邮件");							//设置邮件主题
			String html = "	<h1>你好,很高兴认识你</hi><p>您的UUID为:"+code+"</p>";		//设置邮件内容
			message.setContent(html, "text/html;charset=UTF-8");				//设置邮件文本格式
			
			//发送邮件
			Transport transport = session.getTransport();
	        transport.connect("smtp.qq.com", "1434501783@qq.com", "lulyxaugngsfhhad");					//获取连接(连接方式,发送人邮箱,发送人密码)
	        transport.sendMessage(message, new Address[] { new InternetAddress(to) });	//以列表形式发送邮件.群发
	        transport.close();
		} catch (Exception e) {
			System.err.println("创建邮件服务发生意外.....");
			e.printStackTrace();
		}
	}
}
