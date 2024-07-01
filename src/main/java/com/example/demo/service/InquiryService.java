package com.example.demo.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.example.demo.entity.Reserve;
import com.example.demo.form.InquiryForm;
import com.example.demo.repository.ReserveRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class InquiryService {
	private final ReserveRepository reserveRepository;
	
	public Reserve getInquaryData(InquiryForm inquiryForm, BindingResult result) {
		Reserve reserve =
				reserveRepository.findAllByUserMailAndCode(inquiryForm.getUserMail(), inquiryForm.getCode());
		if(Objects.isNull(reserve)) {
			System.out.println("実行されてます！");
			result.addError(new FieldError(
					result.getObjectName(),
					"code",
					"データが見つかりませんでした。確認の上もう一度ご入力ください"));
		}
		return reserve;
	}
}
