package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ReservationController {
	private final HttpSession session;
	
	//カレンダーに飛ぶ用。○×用のデータ用意する
	@GetMapping("/selectDate")
	public ModelAndView selectDate(ModelAndView mv) {
		return mv;
	}
	
	//カレンダーから飛んできて、次に日時選択に飛ぶ用
	@PostMapping("/selectTime")
	public ModelAndView selectTime(ModelAndView mv) {
		return mv;
	}
	
	@GetMapping("")
	public String userInfoForm() {
		return "input";
	}
}
