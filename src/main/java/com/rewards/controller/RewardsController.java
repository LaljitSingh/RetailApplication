package com.rewards.controller;

import com.rewards.service.RewardsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.exception.CustomerNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RewardsController {

	@Autowired
	private RewardsService rewardsService;

	private static final Logger logger = LoggerFactory.getLogger(RewardsController.class);

	/**
	 * Calculate rewards and prepare report for a customer.
	 * 
	 * @param customerId
	 * @return
	 */
	@GetMapping("/rewards")
	public ResponseEntity<Map<String, Object>> getRewards(@RequestParam String customerId) {
		Map<String, Object> response = new HashMap<>();
		List<Transaction> transactions = null;

		if (null != customerId && customerId.isBlank()) {
			response.put("error", "Customer ID cannot be empty");
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} else {
			customerId = customerId.trim();
			transactions = rewardsService.getTransactionsForCustomer(customerId);
		}
		if (transactions.isEmpty()) {
			logger.error("Customer not found.");
			throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
		} else {
			response.put("monthlyRewards", rewardsService.calculateMonthlyRewards(transactions));
			response.put("totalRewards", rewardsService.calculateTotalRewards(transactions));
			response.put("customerName", rewardsService.getCustomerNameById(customerId));
			response.put("customerId", customerId);
			logger.error("Calculated rewards and prepared report for a customer");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
	}

	/**
	 * Get all customers transaction details for last three months.
	 */
	@GetMapping("/getAllCustomerTransaction")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> listData = rewardsService.getAllCustomersWithTransactionsInLast3Months();
		return new ResponseEntity<>(listData, HttpStatus.OK);
	}
}
