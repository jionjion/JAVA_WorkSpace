package servletBase.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * 	过滤器实例
 */
public class EncodeFilter implements Filter {

	/**初始化方法*/
	public void init(FilterConfig fConfig) throws ServletException {
	}

	/**过滤方法*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		chain.doFilter(request, response);
	}

	/**销毁方法*/
	public void destroy() {
	}

}
