package MicroServices.tool;/**自定义函数类*/
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import freemarker.template.SimpleSequence;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

public class SumListMethod implements TemplateMethodModelEx{

	@SuppressWarnings("deprecation")
	@Override
	public Object exec(List args) throws TemplateModelException {

		//获取前台传入参数,list.中有两个参数,一个是数字数组,另一个是一个字符串sum
		SimpleSequence simpleSequence = (SimpleSequence) args.get(0);	//获得数组,这个为freemark自定义的数组类型
		//转为java的数据类型
		List<BigDecimal> list = simpleSequence.toList();
		int sum = 0;
		for (int i = 0; i < list.size(); i++) {
			sum += list.get(i).intValue();								//将bigNumber类型转为int类型
		}
		return sum;
	}

	
}
