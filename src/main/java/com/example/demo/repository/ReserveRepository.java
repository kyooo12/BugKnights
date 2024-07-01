package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Integer> {

}
