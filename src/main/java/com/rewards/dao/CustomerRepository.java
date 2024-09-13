package com.rewards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rewards.entity.Customer;

/**
 * Repository interface for managing Customer entities.
 * 
 * This interface provides CRUD operations and database access for Customer 
 * entities by extending JpaRepository.
 * 
 * @see JpaRepository
 * @param Customer the entity type
 * @param String the type of the entity ID (Customer ID)
 */
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
