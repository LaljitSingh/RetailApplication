package com.rewards.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rewards.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
}
