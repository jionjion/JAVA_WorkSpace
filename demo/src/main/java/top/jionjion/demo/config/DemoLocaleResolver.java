package top.jionjion.demo.config;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author Jion
 *      自定义区域语言解析器,替换原有的.
 *          注意Bean的ID相同
 */
@Component("localeResolver")
public class DemoLocaleResolver implements LocaleResolver {

    /** 解析区域信息 */
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 区域信息
        Locale locale = Locale.getDefault();

        String l = request.getParameter("l");
        // 请求参数,携带语言信息
        if(!StringUtils.isEmpty(l)){
            // en_US 拆分为 语言信息和国家信息
            String[] info = l.split("_");
            // 创建地址信息
            locale = new Locale(info[0], info[1]);
            return locale;
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
