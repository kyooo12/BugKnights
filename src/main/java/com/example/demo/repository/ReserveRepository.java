package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Reserve;

import jakarta.transaction.Transactional;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
	//予約機能SQL
	@Query(value = "SELECT * FROM t_reserve "
			+ "WHERE adviser_cd = :adviserCd AND reserve_date BETWEEN :nowDate AND :afterDate", nativeQuery = true)
	List<Reserve> findAllByAdviserCdAndDate(@Param("adviserCd") String adviserCd,
											@Param("nowDate") LocalDate nowDate,
											@Param("afterDate") LocalDate afterDate);
	
	@Query(value = "SELECT * FROM t_reserve "
			+ "WHERE adviser_cd = :adviserCd AND reserve_date = :selectDate", nativeQuery = true)
	List<Reserve> findAllByAdviserCdAndSelectDate(@Param("adviserCd") String adviserCd,
									@Param("selectDate") LocalDate selectDate);
	
	@Query(value = "SELECT * FROM t_reserve "
			+ "WHERE adviser_cd = :adviserCd AND reserve_date = :selectDate "
			+ "AND reserve_time = :selectTime", nativeQuery = true)
	Reserve checkReserve(@Param("adviserCd") String adviserCd,
						@Param("selectDate") LocalDate selectDate,
						@Param("selectTime") LocalTime selectTime);
	
	//予約照会SQL
	@Query(value = "SELECT * FROM t_reserve "
			+ "WHERE user_mail = :userMail AND reference_code = :code", nativeQuery = true)
	Reserve findAllByUserMailAndCode(@Param("userMail") String userMail, 
										@Param("code") String code);
	
	@Transactional
	@Modifying
	//予約照会データ削除SQL
	@Query(value = "DELETE FROM t_reserve WHERE user_mail = :userMail AND reference_code = :code", nativeQuery = true)
	void deleteByUserMailAndCode(@Param("userMail") String userMail,
					@Param("code") String code);
	
	//予約登録
	@Transactional
	@Modifying
	@Query(value = "INSERT INTO t_reserve(reserve_date, reserve_time, reference_code, adviser_cd, user_name, user_mail, user_comment) "
			+ "VALUES (:date, :time, :code, :adviserCd, :userName, :userMail, :comment)", nativeQuery = true)
	void setReserve(@Param("date") LocalDate date,
					@Param("time") LocalTime time,
					@Param("code") String code,
					@Param("adviserCd") String adviserCd,
					@Param("userName") String userName,
					@Param("userMail") String userMail,
					@Param("comment") String comment);
	
	//同日時に同じメールアドレスで予約しているかの確認
	@Query(value = "select * from t_reserve where user_mail = :userMail "
			+ "AND reserve_date = :date AND reserve_time = :time", nativeQuery = true)
	Reserve findByBooking(@Param("userMail")String userMail,
			              @Param("date")LocalDate date,
			              @Param("time")LocalTime time);
	
	
	
	
	
	
	
	
}
