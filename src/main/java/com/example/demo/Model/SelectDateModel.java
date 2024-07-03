package com.example.demo.Model;

public class SelectDateModel {
	private String date;
	private boolean maxTime;
	
	public SelectDateModel(String date, boolean maxTime) {
		this.date = date;
		this.maxTime = maxTime;
	}
	public String getDate() {
		return date;
	}
	public boolean isMaxTime() {
		return maxTime;
	}
}
