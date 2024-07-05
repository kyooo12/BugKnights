package com.example.demo.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SelectTimeUtil { 
	public static List<Integer> nowTimeJudge(LocalDate selectDate) {
		LocalDate nowDate = LocalDate.now();
		List<Integer> nowDatelist = new ArrayList<>();
		if(selectDate.compareTo(nowDate) == 0) {
			int intNowTime = LocalTime.now().getHour();
			for(int i = 0; i < 10; i++) {
				if((i + 10) <= (intNowTime + 1)) {
					nowDatelist.add(i + 10);
				}
			}
		}
		return nowDatelist;
	}
	
	public static String getMonthAndDay(LocalDate nowDate) {
		int month = nowDate.getMonthValue();
		int day = nowDate.getDayOfMonth();
		return month + "月" + day + "日";
	}
}
