package com.example.demo.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SelectTimeUtil {
	public static List<Integer> nowTimeJudge(LocalDate selectDate,
											LocalDate nowDate,
											LocalTime nowTime,
											List<Integer> nowDatelist) {
		if(selectDate.compareTo(nowDate) == 0) {
			int intNowTime = nowTime.getHour();
			for(int i = 0; i < 10; i++) {
				if((i + 10) <= (intNowTime + 1)) {
					nowDatelist.add(i + 10);
				}
			}
		}
		return nowDatelist;
	}
}
