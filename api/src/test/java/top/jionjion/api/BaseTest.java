package top.jionjion.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * @author Jion
 *  基础的测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc   // MVC测试
@Slf4j
@AutoConfigureRestDocs(outputDir="target/generated-snippets") // 文档导出目录
public class BaseTest {


    /** 创建生成文档,在每次基础测试方法调用前测试 */
    @Before
    public void adocBuild() throws IOException {
        // 获得项目目录
        String apiDir = System.getProperty("user.dir");
        // 模板文件,将要存放位置
        String adocPath = apiDir + "\\src\\main\\asciidoc\\index.adoc";
        // 文档内容
        StringBuilder content = new StringBuilder();
        // 引入标准头文件
        content.append("include::" + apiDir + "\\src\\main\\asciidoc\\preview.adoc[]").
                append(System.getProperty("line.separator")).
                append(System.getProperty("line.separator"));

        // api基础目录位置
        File apidirs = new File(apiDir + "\\target\\generated-snippets");
        for (File apidir : apidirs.listFiles()) {
            // 各接口目录名
            String apiName = apidir.getName();
            content.append("== " + apiName + System.getProperty("line.separator"));
            // 各接口目录含有文件
            fileAppend(content, apidir + "\\http-request.adoc", ".http-request 请求信息");
            fileAppend(content, apidir + "\\request-headers.adoc", ".request-headers 请求头说明");
            fileAppend(content, apidir + "\\request-parameters.adoc", ".request-parameters 请求参数说明");
            fileAppend(content, apidir + "\\request-body.adoc", ".request-body 请求体说明");
            fileAppend(content, apidir + "\\http-response.adoc", ".http-response 响应信息");
            fileAppend(content, apidir + "\\response-fields.adoc", ".response-fields 返回值说明");
            content.append(System.getProperty("line.separator"));
        }
        // 输出文件
        File file = new File(adocPath);
        writeStringToFile(file, content.toString(), "UTF-8");
    }

    private void writeStringToFile(File file, String content, String character) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        OutputStreamWriter osw = new OutputStreamWriter(fos, character);
        osw.write(content);
        osw.flush();
    }

    private void fileAppend(StringBuilder content, String include, String title) {
        File file = new File(include);
        if (file.exists()) {
            content.append(title).append(System.getProperty("line.separator"));
            content.append("include::").append(include).append("[]").append(System.getProperty("line.separator"));
        }
    }
}
