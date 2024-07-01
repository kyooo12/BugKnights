package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InquiryForm {
	
	@NotBlank(message = "メールアドレスを入力してください")
	private String userMail;
	
	@NotBlank(message = "予約番号を入力してください")
	private String code;
}
