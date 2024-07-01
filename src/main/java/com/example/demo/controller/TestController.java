package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Reserve;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;
 
@AllArgsConstructor
@Controller
public class TestController {
	private final ReserveRepository reserveRepository;
	
	@GetMapping("/reserveList")
	public ModelAndView showReserveList(ModelAndView mv) {
		List<Reserve> reserveList = reserveRepository.findAll();
		mv.addObject("reserveList", reserveList);
		mv.setViewName("testReserveView");
		return mv;
	}
}
