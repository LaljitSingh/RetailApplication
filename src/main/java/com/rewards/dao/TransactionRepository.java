package com.rewards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rewards.entity.Transaction;

/**
 * Repository interface for managing Transaction entities.
 * 
 * This interface provides CRUD operations and database access for Transaction 
 * entities by extending JpaRepository.
 * 
 * @see JpaRepository
 * @param Transaction the entity type
 * @param Long the type of the entity ID (Transaction ID)
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
}
