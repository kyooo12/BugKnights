package com.example.demo.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SelectTimeUtil {
	public static List<Integer> nowTimeJudge(LocalDate selectDate) {
		List<Integer> nowDatelist = new ArrayList<>();
		LocalDate nowDate = LocalDate.now();
		System.out.println("現在の時刻" + nowDate.toString());
		if(selectDate.compareTo(nowDate) == 0) {
			int nowTime = LocalTime.now().getHour();
			for(int i = 0; i < 10; i++) {
				if((i + 10) <= (nowTime + 1)) {
					nowDatelist.add(i + 10);
				}
			}
		}
		return nowDatelist;
	}
}
