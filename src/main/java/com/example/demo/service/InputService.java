package com.example.demo.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.demo.entity.Reserve;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class InputService {
	
	private final ReserveRepository reserveRepository;
	
	//同じメールアドレスで同じ時間に予約できないようにチェック
	public void bookingCheck(String userMail,LocalDate date, LocalTime time,BindingResult result) {
		
		Reserve optionalReserve = reserveRepository.findByBooking(userMail, date, time);
		if(Objects.nonNull(optionalReserve)) {
			result.addError(new FieldError(
				result.getObjectName(), "userMail", "既に同じ日時で他のアドバイザーを予約しています。"	
			));
		}
		
	}

}
