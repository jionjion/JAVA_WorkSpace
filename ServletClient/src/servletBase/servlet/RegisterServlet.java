package servletBase.servlet;
/**
 * 	用户注册的提交Servlet*/
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import servletBase.bean.User;


@WebServlet("/servletBase/servlet/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		User user = new User();
		String username = null;
		String password = null;
		String email = null;
		String sex = null;
		Date birthday = null;
		String[] hobby  = null;
		String introduce = null;
		boolean flag = false;
		try {
			//获取前台参数
			username = request.getParameter("username");
			password = request.getParameter("password");
			email = request.getParameter("email");
			sex = request.getParameter("sex");
			birthday = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthday"));
			hobby = request.getParameterValues("hobby");
			introduce = request.getParameter("introduce");
			if( request.getParameterValues("flag")!= null ){
				flag  = true;
			}
			//封装对象属性
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setSex(sex);
			user.setBirthday(birthday);
			user.setHobby(hobby);
			user.setIntroduce(introduce);
			user.setFlag(flag);
			//保存到session中
			request.getSession().setAttribute("user", user);
			//跳转请求
			request.getRequestDispatcher("/modules/servletBase/userinfo.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取表单参数中出现异常.....");
		}
	}

}
