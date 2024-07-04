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
import com.example.demo.service.InquiryService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class InquiryController {
	private final AdviserRepository adviserRepository;
	private final InquiryService inquiryService;
	
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
				adviserRepository.findById(reserve.getNo()).get();				
			mv.addObject("reserve", reserve);
			mv.addObject("adviser", adviser);
			mv.setViewName("Contents");
		} else {
			mv.setViewName("inquiryForm");
		}
		return mv;
	}
}
