package top.jionjion.demo.config;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 *  @author Jion
 *      自定义异常处理
 */
@Component
public class DemoDefaultErrorAttributes extends DefaultErrorAttributes{

    /** 重写保存错误信息丰富 */
    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

        // 存放自定义属性
        Map<String, Object> model = super.getErrorAttributes(webRequest, includeStackTrace);
        model.put("app","demo");

        // 解析抛出的自定义信息
        Map<String, Object> ext = (Map<String, Object>) webRequest.getAttribute("ext", RequestAttributes.SCOPE_REQUEST);
        model.put("ext", ext);

        return super.getErrorAttributes(webRequest, includeStackTrace);
    }
}
