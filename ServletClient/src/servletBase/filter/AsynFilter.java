package servletBase.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
/**
 * 	使用注解配置异步支持的过滤器
 * */

@WebFilter(filterName="AsynFilter",value={"/servletBase/*"},dispatcherTypes={DispatcherType.ASYNC,DispatcherType.REQUEST})
public class AsynFilter implements Filter {

    public AsynFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("执行异步过滤器前------------");
		chain.doFilter(request, response);
		System.out.println("执行异步过滤器后------------");
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
