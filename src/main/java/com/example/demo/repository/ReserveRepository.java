package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
	
	@Query(value="SELECT * FROM t_reserve "
			+ "WHERE user_mail=:userMail AND reference_code=:code", nativeQuery = true)
	Reserve findAllByUserMailAndCode(@Param("userMail") String userMail, 
											@Param("code") String code);
}
