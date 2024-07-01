package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Adviser;
import com.example.demo.entity.Reserve;
import com.example.demo.repository.AdviserRepository;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;
 
@AllArgsConstructor
@Controller
public class ViewController {
	private final ReserveRepository reserveRepository;
	private final AdviserRepository adviserRepository;
	
	@GetMapping("/reserveList")
	public ModelAndView showReserveList(ModelAndView mv) {
		List<Reserve> reserveList = reserveRepository.findAll();
		mv.addObject("reserveList", reserveList);
		mv.setViewName("testReserveView");
		return mv;
	}
	
	@GetMapping("/adviserList")
	public ModelAndView showAdviserList(ModelAndView mv) {
		List<Adviser> adviserList = adviserRepository.findAll();
		mv.addObject("adviserList", adviserList);
		mv.setViewName("testAdviserView");
		return mv;
	}
}
