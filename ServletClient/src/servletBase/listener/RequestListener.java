package servletBase.listener;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 *	Request对象的监听器
 */
@WebListener//使用注解完成配置
public class RequestListener implements ServletRequestListener, ServletRequestAttributeListener {

    public RequestListener() {
    }

    public void requestInitialized(ServletRequestEvent arg0)  { 
    	System.out.println("Request对象创建");
    }
    public void requestDestroyed(ServletRequestEvent arg0)  {
    	System.out.println("Request对象销毁");
    }
    public void attributeAdded(ServletRequestAttributeEvent arg0)  { 
    	System.out.println("Request对象属性新增");
    }
    public void attributeReplaced(ServletRequestAttributeEvent arg0)  { 
    	System.out.println("Request对象属性修改");
    }
    public void attributeRemoved(ServletRequestAttributeEvent arg0)  { 
    	System.out.println("Request对象属性删除");
    }
	
}
