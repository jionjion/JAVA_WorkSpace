package top.jionjion.demo.handler;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Jion
 *  登录拦截器,在MVCConfig中配置
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    /** 拦截前 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 通过session判断
        Object username = request.getSession().getAttribute("username");
        // 获取session中的用户信息
        if(StringUtils.isEmpty(username)){
            // 未登录,返回登录页面
            request.setAttribute("msg","没有权限,请先登录!");
            request.getRequestDispatcher("/login").forward(request, response);
            return false;
        }else{
            // 已登录
            return true;
        }
    }

    /** 拦截方法 */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /** 拦截之后 */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
