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
	
	//カレンダーから飛んできて、次に時間選択に飛ぶ用
	@PostMapping("/selectTime")
	public ModelAndView selectTime(@RequestParam("confirmDate") String selectDate,
									ModelAndView mv) {
		LocalDate localDate = LocalDate.parse(selectDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		session.setAttribute("selectDate", localDate);
		String adviserCd = session.getAttribute("adviserCd").toString();
		List<Reserve> list = reserveRepository.findAllByAdviserCdAndSelectDate(adviserCd, localDate);
		mv.addObject("reserveList", list);
		mv.setViewName("selectTime");
		return mv;
	}
	
	//次に日時選択に飛ぶようのテストURL
	@GetMapping("/selectTimeTest")
	public ModelAndView selectTime(ModelAndView mv) {
		String adviserCd = "1";
		LocalDate selectDate = LocalDate.of(2024, 07, 04);
		List<Reserve> list = reserveRepository.findAllByAdviserCdAndSelectDate(adviserCd, selectDate);
		mv.addObject("reserveList", list);
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
		Adviser adviser = adviserRepository.findById(Integer.parseInt(adviserCd)).get();
		if(!result.hasErrors()) {
			Reserve reserve = inputForm.getEntity();
			reserve.setAdviserCd(adviserCd);
			reserve.setDate(date);
			reserve.setTime(time);
			reserve.setCode(code);
			mv.addObject("reserve", reserve);
			mv.addObject("adviser", adviser);
			session.setAttribute("reserve", reserve);
			mv.setViewName("Confirmation");
		} else {
			mv.setViewName("inputForm");
		}
		return mv;
	}
	
	@GetMapping("/Completion")
	public String Completion() {
		
		return "Completion.html";
	}
}
