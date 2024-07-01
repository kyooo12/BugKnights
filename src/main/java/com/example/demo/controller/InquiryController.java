package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Reserve;
import com.example.demo.form.InquiryForm;
import com.example.demo.repository.AdviserRepository;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class InquiryController {
	private final ReserveRepository reserveRepository;
	private final AdviserRepository adviserRepository;
	
	@GetMapping("/inquiryForm")
	public String showForm(InquiryForm inquiryForm) {
		return "inquiryForm";
	}
	
	@PostMapping("/reserveSearch")
	public ModelAndView reserveSearch(@ModelAttribute @Validated InquiryForm inquiryForm,
								BindingResult result,
								ModelAndView mv) {
		System.out.println(inquiryForm.getUserMail() + inquiryForm.getCode());
		if(!result.hasErrors()) {
			Reserve reserve =
				reserveRepository.findAllByUserMailAndCode(inquiryForm.getUserMail(), inquiryForm.getCode());
			mv.addObject("reserve", reserve);
			mv.setViewName("testReserveView2");
		} else {
			mv.setViewName("inquiryForm");
		}
		return mv;
	}
}
