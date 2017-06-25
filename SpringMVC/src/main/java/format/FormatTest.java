package format;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Locale;
import org.junit.Test;
import org.springframework.format.number.CurrencyFormatter;

import junit.framework.Assert;

/**
 *	对货币类型转换器进行测试
 */
public class FormatTest {

	@Test
	public void testOne() throws ParseException {
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2); 		//小数点后保留2位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);	//四舍五入的模式.ceiling:四舍五入
		
		//断言测试,将货币字符串转为对象为大的数字类型,并进行四舍五入 
		Assert.assertEquals(new BigDecimal("123.13"), currencyFormatter.parse("$123.123" , Locale.US));
	}
	
	@Test
	public void testTwo() {
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2); 		//小数点后保留2位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);	//四舍五入的模式.ceiling:四舍五入
		
		//断言测试,将大的数字类型转为带美元符号的字符串
		Assert.assertEquals("$123.00", currencyFormatter.print(new BigDecimal("123"), Locale.US));
	}
}
