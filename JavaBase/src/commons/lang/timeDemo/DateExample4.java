package commons.lang.timeDemo;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 	日期类
 * @author JionJion
 */
public class DateExample4 {

	public static void main(String[] args) throws Exception{
		
		// 生成Date对象
		Date date = DateUtils.parseDate("2018/01/01 00:00:00","yyyy/MM/dd HH:mm:ss");

		//格式化
		DateFormatUtils.format(date, "yyyy/MM/dd HH:mm:ss");
		
		// 10天后
		DateUtils.addDays(date, -10);
		// 10天前
		DateUtils.addDays(date, -10);
		

		// 后一个月
		DateUtils.addMonths(date, 1);
		// 前一个月
		DateUtils.addMonths(date, -1);
		
		// 判断是否是同一天
		Date date1 = DateUtils.parseDate("2010/01/01 11:22:33", "yyyy/MM/dd HH:mm:ss");
		Date date2 = DateUtils.parseDate("2010/01/01 22:33:44", "yyyy/MM/dd HH:mm:ss");
		DateUtils.isSameDay(date1, date2);			// true

	}
}
