package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "t_reserve")
@Data
public class Reserve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserve_no")
	private Integer no;
	
	@Column(name = "reserve_date")
	private LocalDate date;
	
	@Column(name = "reserve_time")
	private LocalTime time;
	
	@Column(name = "reference_code")
	private String code;
	
	@Column(name = "adviser_cd")
	private String adviserCd;
	
	@Column(name = "user_name")
	private String userName;
	
	@Column(name = "user_mail")
	private String userMail;
	
	@Column(name = "user_comment")
	private String comment;
}
