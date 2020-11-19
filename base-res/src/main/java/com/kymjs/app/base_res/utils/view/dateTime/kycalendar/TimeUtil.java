package com.kymjs.app.base_res.utils.view.dateTime.kycalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	/**
	 * 获得某月的天数
	 */
	public static int getDays(int year, int month){
		int days = 0;
		String monthStr = month+"";
		if(month<10)
			monthStr = "0"+month;
		try {
			SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy/MM");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(simpleDate.parse(year+"/"+monthStr));			
			days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return days;
	}
	/**
	 * 获得星期
	 */
	public static int getWeek(int year, int month , int date){
		int week = 0;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String monthStr = month+"";
			if(month<10)
				monthStr = "0"+month;
			String dateStr = date+"";
			if(date<10)
				dateStr = "0"+date;
			Date time = format.parse(year+"-"+monthStr+"-"+dateStr);
			week = time.getDay();
			if(week == 0)
				week = 7;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return week;
	}
	/**
	 * 日期比较
	 */
	public static int compare(int year, int month, int date, Date current){
		int rat = 1;
		try {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String monthStr = month+"";
			if(month<10)
				monthStr = "0"+month;
			String dateStr = date+"";
			if(date<10)
				dateStr = "0"+date;
			Date time = format.parse(year+"-"+monthStr+"-"+dateStr);
			if(time.getTime()<current.getTime())
				return -1;
			if(time.getTime() == current.getTime())
				return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return rat;
	}
	/**
	 * 解析日期
	 */
	public static Date parseDate(String date){
		Date time = null;
		if(date == null || date.equals(""))
			return null;
		try {
			SimpleDateFormat df = (SimpleDateFormat)DateFormat.getDateInstance(); 
			df.applyPattern("yyyy-MM-dd");
			df.applyPattern("yyyy/MM/dd");
			time = df.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}
}
