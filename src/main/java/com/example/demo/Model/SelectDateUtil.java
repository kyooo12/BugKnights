package com.example.demo.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.entity.Reserve;

public class SelectDateUtil {
	public static List<SelectDateModel> getSelectDate(List<Reserve> reserveList,
												LocalDate nowDate,
												LocalDate afterDate) {
		List<SelectDateModel> list = new ArrayList<>();
		checkFirstAndLastDate(list, nowDate, afterDate);
		checkReserve(reserveList, list, nowDate, afterDate);
		return list;
	}
	
	//現在の日付から前と、三か月より後のデータをチェック
	public static void checkFirstAndLastDate(List<SelectDateModel> list,
											LocalDate nowDate,
											LocalDate afterDate) {
		LocalDate firstCurrentDate = nowDate;
		int firstLength = nowDate.lengthOfMonth();
		
		for(int i = 0; i < firstLength; i++) {
			firstCurrentDate = firstCurrentDate.withDayOfMonth(i + 1);
			if(nowDate.compareTo(firstCurrentDate) > 0) {
				list.add(new SelectDateModel(firstCurrentDate.toString(), true));
			}
		}
		
		LocalDate lastCurrentDate = nowDate.plusMonths(2);
		int lastLength = lastCurrentDate.lengthOfMonth();		
		for(int i = 0; i < lastLength; i++) {
			lastCurrentDate = lastCurrentDate.withDayOfMonth(i + 1);
			if(afterDate.compareTo(lastCurrentDate) <= 0) {
				list.add(new SelectDateModel(lastCurrentDate.toString(), true));
			}
		}
	}
	
	//予約DBで時間が埋まっている日付をチェック
	public static void checkReserve(List<Reserve> reserveList,
									List<SelectDateModel> list,
									LocalDate nowDate,
									LocalDate afterDate) {
		LocalDate currentDate = nowDate;
		for(int i = 0; i < 3; i++) {
			int length = currentDate.lengthOfMonth();
			for(int j = 0; j < length; j++) {
				List<Reserve> ansList = new ArrayList<>();
				currentDate = currentDate.withDayOfMonth(j + 1);
				for(Reserve reserve : reserveList) {
					if(currentDate.compareTo(reserve.getDate()) == 0) {
						ansList.add(reserve);
					}
				}
				if(currentDate.compareTo(nowDate) == 0) {
					Integer[] reserves = new Integer[10];
					for(int k = 0; k < ansList.size(); k++) {
						reserves[ansList.get(k).getTime().getHour() - 10] = ansList.get(k).getTime().getHour();
					}
					int nowHour = LocalTime.now().getHour();
					for(int k = 0; k < 10; k++) {
						if(reserves[k] == null && (k + 10) <= nowHour + 1) {
							ansList.add(new Reserve());
						}
					}
				}
				if(ansList.size() == 10) {
					list.add(new SelectDateModel(currentDate.toString(), true));
				}
			}
			currentDate = currentDate.plusMonths(1);
		}
	}
}
