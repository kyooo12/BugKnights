package com.example.demo.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Model.SelectDateModel;
import com.example.demo.Model.SelectDateUtil;
import com.example.demo.entity.Reserve;
import com.example.demo.repository.ReserveRepository;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ReservationController {
	private final HttpSession session;
	private final ReserveRepository reserveRepository;
	
	//カレンダーに飛ぶ用。○×用のデータ用意する
	@PostMapping("/selectDate")
	public ModelAndView selectDate(@RequestParam("adviserCode") String adviserCd,
									ModelAndView mv) {
		session.setAttribute("adviserCd", adviserCd);
		LocalDate nowDate = LocalDate.now();
		LocalDate afterDate = nowDate.plusMonths(2);
		List<Reserve> reserveList = reserveRepository.findAllByAdviserCdAndDate(adviserCd, nowDate, afterDate);
		List<SelectDateModel> list = SelectDateUtil.getSelectDate(reserveList, nowDate, afterDate);
		Map<String, Boolean> map = new HashMap<>();
		list.forEach( d -> {
			map.put(d.getDate(), d.isMaxTime());
		});
		mv.addObject("dateMap", map);
		mv.setViewName("selectDate");
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
