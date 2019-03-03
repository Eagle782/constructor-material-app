package com.cloudsky.material.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getToday(){
		Date dt=new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		return formater.format(dt);
	}
}
