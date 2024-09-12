package com.rewards.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rewards.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {
}
