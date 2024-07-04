package com.example.demo.form;

import com.example.demo.entity.Reserve;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InputForm {
	
	@NotBlank(message = "氏名を入力してください")
	private String userName;
	
	@NotBlank(message = "メールアドレスを入力してください")
	private String userMail;
	
	private String comment;
	
	public Reserve getEntity() {
		Reserve reserve = new Reserve();
		reserve.setUserName(userName);
		reserve.setUserMail(userMail);
		reserve.setComment(comment);
		return reserve;
	}
}
