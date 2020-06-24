package exportWord.core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

/**
 *	导出请假条
 *	使用freemarker.jar,进行模板替换,完成xml格式的Word文档导出
 *	xml来自Word文档的另存为.xml格式
 *	变量的字体与正文字体一致,注意区分中英文字体样式
 *	默认的XML不能进行换行格式化等处理,否则会破坏文档格式
 *	变量替换使用{变量名!},避免空指针的出现.
 */
public class ExportLeaveApply {

		//模板引擎配置文件
	    private Configuration configuration = null;  
	      
	    //构造函数
	    public ExportLeaveApply(){  
	    	//传入版本号 V2.3.0,获得配置信息
	        configuration = new Configuration(new Version(2, 3, 0));
	        //字符编码
	        configuration.setDefaultEncoding("UTF-8");  
	    }  
	      
	    /** 生成Word */
	    public void createWord(){
	    	//数据Map
	        Map<String,Object> dataMap = getData();
	        //模板文件所在路径,根路径
	        configuration.setClassForTemplateLoading(this.getClass(), "/");
	        Template t=null;  
	        try {
	            t = configuration.getTemplate("/exportWord/template/请假模板.xml"); //获取模板文件
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        //导出文件
	        File outFile = new File("请假条.doc"); 
	        Writer out = null;  
	        try {  
	            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));  
	        } catch (FileNotFoundException e1) {  
	            e1.printStackTrace();  
	        } catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	           
	        try {
	            t.process(dataMap, out); //将填充数据填入模板文件并输出到目标文件 
	        } catch (TemplateException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    /** 参数列表 */
	    private Map<String, Object> getData() {  
	    	Map<String, Object> dataMap = new HashMap<String, Object>();
	        dataMap.put("userStudentName", "囧囧");
	        dataMap.put("leaveData",new SimpleDateFormat("yyyy年mm月dd日").format(System.currentTimeMillis()));  
	        dataMap.put("userParentName", "家长同意");
	        dataMap.put("userTeacherName", "班主任同意"); 
	        dataMap.put("teacherLeaderName", "年级长同意");
	          
	        /* List数据类型
	        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();  
	        for (int i = 0; i < 10; i++) {  
	            Map<String,Object> map = new HashMap<String,Object>();  
	            map.put("XXX", i);  
	            map.put("XXX", "内容"+i);  
	            list.add(map);  
	        }  
	        dataMap.put("list", list);*/  
	        
	        return dataMap;
	    }  		
	    
	    //主函数
	    public static void main(String[] args) {  
	        ExportLeaveApply test = new ExportLeaveApply();  
	        test.createWord();  
	        System.out.println("输出完成!");
	    }  

}
