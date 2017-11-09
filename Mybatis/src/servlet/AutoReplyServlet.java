package servlet;
/**异步请求的调用类*/
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.QueryService;

/**
 * Servlet implementation class AutoReplyServlet
 */
@WebServlet("/AutoReplyServlet")
public class AutoReplyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AutoReplyServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String content = request.getParameter("content");
		response.setContentType("text/html;charset=utf-8");
		QueryService queryService = new QueryService();
		PrintWriter out = response.getWriter();
		//根据关键字返回查询结果
		/*使用单表查询*/
//		String answer = queryService.queryByCommand(content);
		/*使用多表查询*/
		String answer = queryService.queryByCommandTwo(content);
		out.println(answer);
		out.flush();
		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
