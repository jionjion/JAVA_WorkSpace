package servlet;
/***
 * 	初始化对话页面
 */
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class InitTalkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public InitTalkServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取传入
		request.setCharacterEncoding("UTF-8");
		//跳转转发页面
		request.getRequestDispatcher("/WEB-INF/jsp/front/talk.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
