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
	 * Fetches rewards for a given customer by ID, calculating monthly and total
	 * rewards.
	 * 
	 * @param customerId ID of the customer.
	 * @return Response with customer details and rewards, or error if not found.
	 */
	@GetMapping("/rewards")
	public ResponseEntity<Map<String, Object>> getRewards(@RequestParam String customerId) {
		Map<String, Object> response = new HashMap<>();
		List<Transaction> transactions = null;

		if (null != customerId && customerId.isBlank()) {
			response.put("error", "Customer ID cannot be empty");
			logger.error("Found customer id is empty");
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
	 * Retrieves all customers with their transactions from the last 3 months.
	 * 
	 * @return List of customers and their transactions, or error if no customers
	 *         found.
	 */
	@GetMapping("/getAllCustomerTransaction")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		List<Customer> customers = rewardsService.getAllCustomersWithTransactionsInLast3Months();
		if (customers.isEmpty()) {
			logger.error("Not found any customer");
			throw new CustomerNotFoundException("Not found any customer");
		}
		return new ResponseEntity<>(customers, HttpStatus.OK);
	}
}
