package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Model.ConfirmationUtil;
import com.example.demo.Model.SelectDateModel;
import com.example.demo.Model.SelectDateUtil;
import com.example.demo.Model.SelectTimeUtil;
import com.example.demo.entity.Adviser;
import com.example.demo.entity.Reserve;
import com.example.demo.form.InputForm;
import com.example.demo.repository.AdviserRepository;
import com.example.demo.repository.ReserveRepository;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class ReservationController {
	private final HttpSession session;
	private final ReserveRepository reserveRepository;
	private final AdviserRepository adviserRepository;
	
	@GetMapping("/")
	public String index() {
		return "top.html";
	}
	
	//カレンダーに飛ぶ用。○×用のデータ用意する
	@PostMapping("/selectDate")
	public ModelAndView selectDate(@RequestParam("adviserCode") String adviserCd,
									ModelAndView mv) {
		Adviser adviser = adviserRepository.findById(Integer.parseInt(adviserCd)).get();
		session.setAttribute("adviserName", adviser.getName());
		session.setAttribute("adviserCd", adviserCd);
		LocalDate nowDate = LocalDate.now();
		LocalDate afterDate = nowDate.plusMonths(2);
		List<Reserve> reserveList = reserveRepository.findAllByAdviserCdAndDate(adviserCd, nowDate, afterDate);
		List<SelectDateModel> list = SelectDateUtil.getSelectDate(reserveList, nowDate, afterDate);
		Map<String, Boolean> map = new HashMap<>();
		list.forEach( d -> {
			map.put(d.getDate(), d.isMaxTime());
		});
		mv.addObject("adviserName", adviser.getName());
		mv.addObject("dateMap", map);
		mv.setViewName("selectDate");
		return mv;
	}
	
	//時間選択から日付選択に戻る
	@GetMapping("/selectDate")
	public ModelAndView backDate(ModelAndView mv) {
		String adviserCd = (String)session.getAttribute("adviserCd");
		Adviser adviser = adviserRepository.findById(Integer.parseInt(adviserCd)).get();
		session.setAttribute("adviserName", adviser.getName());
		LocalDate nowDate = LocalDate.now();
		LocalDate afterDate = nowDate.plusMonths(2);
		List<Reserve> reserveList = reserveRepository.findAllByAdviserCdAndDate(adviserCd, nowDate, afterDate);
		List<SelectDateModel> list = SelectDateUtil.getSelectDate(reserveList, nowDate, afterDate);
		Map<String, Boolean> map = new HashMap<>();
		list.forEach( d -> {
		map.put(d.getDate(), d.isMaxTime());
		});
		mv.addObject("adviserName", adviser.getName());
		mv.addObject("dateMap", map);
		mv.setViewName("selectDate");
		return mv;
	}
	
	//カレンダーから飛んできて、次に時間選択に飛ぶ用
	@PostMapping("/selectTime")
	public ModelAndView selectTime(@RequestParam("confirmDate") String stringSelectDate,
									ModelAndView mv) {
		LocalDate selectDate = LocalDate.parse(stringSelectDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		session.setAttribute("selectDate", selectDate);
		String adviserName = (String)session.getAttribute("adviserName");
		String adviserCd = (String)session.getAttribute("adviserCd");
		List<Reserve> list = reserveRepository.findAllByAdviserCdAndSelectDate(adviserCd, selectDate);
		List<Integer> nowTimeList = SelectTimeUtil.nowTimeJudge(selectDate);
		mv.addObject("adviserName", adviserName);
		mv.addObject("selectDate", selectDate);
		mv.addObject("reserveList", list);
		mv.addObject("nowTimeList", nowTimeList);
		mv.setViewName("selectTime");
		return mv;
	}
	
	//時間選択からお客様情報入力に飛ぶ
	@PostMapping("/inputForm")
	public String userInfoForm(@RequestParam("selectTime") String selectTime,
								InputForm inputForm) {
		session.setAttribute("selectTime", selectTime);
		return "inputForm";
	}
	
	//お客様情報入力から確認画面に飛ぶ
	@PostMapping("/Confirmation")
	public ModelAndView confitmationView(@ModelAttribute @Validated InputForm inputForm,
										BindingResult result,
										ModelAndView mv) {
		String adviserCd = (String)session.getAttribute("adviserCd");
		LocalDate date = (LocalDate)session.getAttribute("selectDate");
		String stringTime = (String)session.getAttribute("selectTime");
		LocalTime time = LocalTime.parse(stringTime);	
		String code = ConfirmationUtil.codeGenerate(10);
		String adviserName = (String)session.getAttribute("adviserName");
		if(!result.hasErrors()) {
			Reserve reserve = inputForm.getEntity();
			reserve.setAdviserCd(adviserCd);
			reserve.setDate(date);
			reserve.setTime(time);
			reserve.setCode(code);
			mv.addObject("reserve", reserve);
			mv.addObject("adviserName", adviserName);
			session.setAttribute("reserve", reserve);
			mv.setViewName("Confirmation");
		} else {
			mv.setViewName("inputForm");
		}
		return mv;
	}
	
	@GetMapping("/Completion")
	public ModelAndView Completion(ModelAndView mv) {
		Reserve reserve = (Reserve)session.getAttribute("reserve");
		reserveRepository.setReserve(reserve.getDate(),
									reserve.getTime(),
									reserve.getCode(),
									reserve.getAdviserCd(),
									reserve.getUserName(),
									reserve.getUserMail(),
									reserve.getComment());
		mv.addObject("code", reserve.getCode());
		mv.setViewName("Completion");
		return mv;
	}
}
