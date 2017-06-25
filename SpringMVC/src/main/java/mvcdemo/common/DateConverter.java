package mvcdemo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;


public  class DateConverter implements Converter<String, Date>{


	@Override
	public Date convert(String source) {
		try {
			return new SimpleDateFormat("YYYY-MM-DD").parse(source);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	
}
