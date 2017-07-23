package servlet;
/**
 * 	消息列表界面*/
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.Message;
import entity.Page;
import service.MessageListService;

//@WebServlet(name = "listServlet", urlPatterns = { "/listServlet" })
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取传入模糊查询
		request.setCharacterEncoding("UTF-8");
		String command = request.getParameter("command");
		String description = request.getParameter("description");
		//分页查询
		String currentPage = request.getParameter("currentPage");
		Page page = new Page();
		Pattern pattern = Pattern.compile("[0-9]{1,9}");	//正则表达式,前端只能传入[0,9]数字
		if (currentPage == null || currentPage.trim().equals("") || !pattern.matcher(currentPage).matches()) {
			page.setCurrentPage(1);
		}else{
			page.setCurrentPage(Integer.parseInt(currentPage));
		}
		//分页,模糊查询
		MessageListService messageListService = new MessageListService();
		List<Message> messagesList = messageListService.queryMessagesList(command, description,page);
		request.setAttribute("messagesList",messagesList);
		//向页面传递参数:条件,分页
		request.setAttribute("command", command);
		request.setAttribute("description", description);
		request.setAttribute("page", page);
		//跳转转发页面
		request.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

}
