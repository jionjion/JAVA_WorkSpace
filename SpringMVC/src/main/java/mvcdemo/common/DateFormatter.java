package mvcdemo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

/**
 * 	日期转换的类*/
public class DateFormatter implements Formatter<Date>{

	/**打印日期*/
	@Override
	public String print(Date date, Locale locale) {
		
		return null;
	}

	/**解析日期*/
	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		return new SimpleDateFormat("YYYY-MM-DD").parse(text);
	}

	
}
