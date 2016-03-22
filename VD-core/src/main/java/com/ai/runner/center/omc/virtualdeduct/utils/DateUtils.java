package com.ai.runner.center.omc.virtualdeduct.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	public static Timestamp toTimeStamp(Date date){
		Timestamp result = null;
		if(date != null){
			result = new Timestamp(date.getTime());
		}
		return result;
	}
	public static Timestamp getTimestamp(String str,String pattern) {
		Timestamp result = null;
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		
		try {
			return new Timestamp(formatter.parse(str).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;

	}
	public static String format(Date date, String pattern){
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String result = formatter.format(date);

		return result;
	}
	
	public static String getCurrMonth(){
		return format(new Date(),"yyyyMM");
	}
	
	public static Timestamp currTimeStamp(){
		return new Timestamp(System.currentTimeMillis());
	}
	/**
	 * 
	* @Title: monthsAdd 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param montstr
	* @param @param interval
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String monthsAdd(String montstr,int interval){
		String yyyy = montstr.substring(0,4);
		String mm = montstr.substring(4,6);
		String sdesmonth = "";
		int diffyear ;
		int desMonth ;
		int noriyear = Integer.parseInt(yyyy);
	    
		int norimonth = Integer.parseInt(mm);
		
        if ((norimonth + interval) > 0){
        	if ((norimonth + interval)%12 == 0){
				 diffyear = (norimonth + interval)/12 - 1;
				 desMonth = (norimonth + interval)%12 + 12;
        	}else{
				 diffyear = (norimonth + interval)/12;
				 desMonth = (norimonth + interval)%12;
        	}
        }else{
			 diffyear = (norimonth + interval)/12 - 1;
			
			 desMonth = (norimonth + interval)%12 + 12;
        }
        
		int ndesYear = noriyear + diffyear;
		
        if (desMonth<10){
        	sdesmonth = "0" + Integer.toString(desMonth);
        }else{
        	sdesmonth = Integer.toString(desMonth);
        }
        return Integer.toString(ndesYear) + sdesmonth;
	}
	
	/**
	 * 
	* @Title: monthDiffs 
	* @Description: 计算两个月份之间的差值
	* @param @param fisMonth
	* @param @param secMonth
	* @param @return    设定文件 
	* @return Integer    返回类型 
	* @throws
	 */

	public static Integer monthDiffs(String fisMonth,String secMonth){
		Integer nfstyyyy = Integer.parseInt(fisMonth.substring(0,4));
		Integer nfstMonth =  Integer.parseInt(fisMonth.substring(4,6));
		
		Integer nsecyyyy = Integer.parseInt(secMonth.substring(0,4));
		Integer nsecMonth = Integer.parseInt(secMonth.substring(4,6));
	
		 
		Integer nYyyyDiff = nsecyyyy - nfstyyyy;
		
		Integer nMonth = nsecMonth -  nfstMonth;
		
		return nYyyyDiff*12 + nMonth;
	}
	
	
}
