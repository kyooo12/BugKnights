package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Adviser;

public interface AdviserRepository extends JpaRepository<Adviser, Integer> {

}
