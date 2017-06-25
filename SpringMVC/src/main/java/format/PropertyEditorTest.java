package format;

import java.beans.PropertyEditorSupport;

import org.junit.Test;

import mvcdemo.bean.User;

/***
 *	自定义的类型转换器
 *	继承PropertyEditorSupport实现
 */
public class PropertyEditorTest extends PropertyEditorSupport{

	/*自定义的文字传输顺序,并将其转换为对象属性,最终返回一个对象*/
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
	
		User user = new User();
		String[] textArray = text.split(",");	//将传入的字符串依据逗号分隔
		user.setUsername(textArray[0]);
		user.setPassword(textArray[1]);			//属性绑定
		this.setValue(user);					//将值压入,用来返回调用
	}

	//测试
	@Test
	public void test() {
		PropertyEditorTest editor = new PropertyEditorTest();
		editor.setAsText("张三,123456");		//传入字符串,注意顺序
		User user = (User) editor.getValue();	//获得对象
		System.out.println("将字符串转为对象:"+user.toString());	//验证
	}
}
