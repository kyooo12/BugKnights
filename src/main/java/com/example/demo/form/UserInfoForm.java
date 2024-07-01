package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserInfoForm {
	
	@NotBlank(message = "氏名を入力してください")
	private String userName;
	
	@NotBlank(message = "メールアドレスを入力してください")
	private String userMail;
	
	@NotBlank(message = "ご要望・ご相談を入力してください")
	private String comment;
}
