package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Adviser;
import com.example.demo.entity.Reserve;
import com.example.demo.form.InquiryForm;
import com.example.demo.repository.AdviserRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.service.InquiryService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class InquiryController {
	private final AdviserRepository adviserRepository;
	private final ReserveRepository reserveRepository;
	private final InquiryService inquiryService;
	private final HttpSession session;
	
	@GetMapping("/inquiryForm")
	public String showForm(InquiryForm inquiryForm) {
		return "inquiryForm";
	}
	
	@PostMapping("/reserveSearch")
	public ModelAndView reserveSearch(@ModelAttribute @Validated InquiryForm inquiryForm,
								BindingResult result,
								ModelAndView mv) {
		Reserve reserve = inquiryService.getInquaryData(inquiryForm, result);
		if(!result.hasErrors()) {
			Adviser adviser = 
				adviserRepository.findById(Integer.parseInt(reserve.getAdviserCd())).get();				
			mv.addObject("reserve", reserve);
			mv.addObject("adviser", adviser);
			session.setAttribute("userMail", reserve.getUserMail());
			session.setAttribute("code", reserve.getCode());
			mv.setViewName("Contents");
		} else {
			mv.setViewName("inquiryForm");
		}
		return mv;
	}
	
	@GetMapping("/cancellationCompleted")
	public String cancellationCompleted() {
		String userMail = (String)session.getAttribute("userMail");
		String code = (String)session.getAttribute("code");
		reserveRepository.deleteByUserMailAndCode(userMail, code);
		return "cancellationCompleted";
	}
}
