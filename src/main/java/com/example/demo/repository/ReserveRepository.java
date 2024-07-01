package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
	
	@Query(value = "SELECT * FROM t_reserve "
			+ "WHERE user_mail = :userMail AND reference_code = :code", nativeQuery = true)
	Reserve findAllByUserMailAndCode(@Param("userMail") String userMail, 
										@Param("code") String code);
	@Query(value = "INSERT INTO t_reserve(reserve_date, reserve_time, reference_code, adviser_cd, user_name, user_mail, user_comment) "
			+ "VALUES :no, :date, :time, :code, :adviserCd, :userName, :userMail, :comment", nativeQuery = true)
	void setReserve(@Param("no") Integer no,
					@Param("date") LocalDate date,
					@Param("time") LocalTime time,
					@Param("code") String code,
					@Param("adviserCd") String adviserCd,
					@Param("userName") String userName,
					@Param("userMail") String userMail,
					@Param("comment") String comment);
}
