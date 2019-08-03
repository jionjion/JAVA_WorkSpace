package top.jionjion.demo.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Jion
 */
public class DemoListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("容器启动了........");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("容器销毁了........");
    }
}
