package com.rewards.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rewards.dao.CustomerRepository;
import com.rewards.dao.TransactionRepository;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RewardsService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CustomerRepository customerRepository;

	private static final Logger log = LoggerFactory.getLogger(RewardsService.class);

	/**
	 * Calculates the reward points based on the transaction amount.
	 * 
	 * @param amount Transaction amount.
	 * @return Total reward points earned for the transaction.
	 */
	public int calculatePoints(double amount) {
		int points = 0;
		if (amount > 100) {
			points += (amount - 100) * 2;
			amount = 100;
		}
		if (amount > 50) {
			points += (amount - 50);
		}
		return points;
	}

	/**
	 * Calculates rewards for each month based on transactions.
	 * 
	 * @param transactions List of customer transactions.
	 * @return A map of Year-Month and the total points earned.
	 */
	public Map<Object, Integer> calculateMonthlyRewards(List<Transaction> transactions) {
		log.info("Calculating monthly rewards");
		return transactions.stream().collect(Collectors.groupingBy(tx -> {
			LocalDate date = tx.getTransactionDate();
			return date.getYear() + "-" + date.getMonth();
		}, Collectors.summingInt(tx -> calculatePoints(tx.getAmount()))));
	}

	/**
	 * Calculates the total rewards for a customer.
	 * 
	 * @param transactions List of customer transactions.
	 * @return Total reward points earned.
	 */
	public int calculateTotalRewards(List<Transaction> transactions) {
		log.info("Calculating total rewards");
		return transactions.stream().mapToInt(tx -> calculatePoints(tx.getAmount())).sum();
	}

	/**
	 * Fetches transactions for a customer in the last three months.
	 * 
	 * @param customerId ID of the customer.
	 * @return List of transactions made by the customer in the last 3 months.
	 */
	@Transactional
	public List<Transaction> getTransactionsForCustomer(String customerId) {
		LocalDate threeMonthsAgo = LocalDate.now().minus(3, ChronoUnit.MONTHS);
		log.info("Fetching data for a particular customer");
		return transactionRepository.findAll().stream()
				.filter(tx -> tx.getCustomer().getCustomerId().equals(customerId))
				.filter(tx -> tx.getTransactionDate().isAfter(threeMonthsAgo)).collect(Collectors.toList());
	}

	/**
	 * Fetches all customers and their transactions for the last three months.
	 * 
	 * @return List of customers and their transactions in the last 3 months.
	 */
	@Transactional
	public List<Customer> getAllCustomersWithTransactionsInLast3Months() {
		log.info("Fetching all customers transaction details");
		LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
		return customerRepository.findAll().stream().map(customer -> {
			Customer customerDTO = new Customer();
			customerDTO.setCustomerId(customer.getCustomerId());
			customerDTO.setName(customer.getName());

			List<Transaction> transactions = customer.getTransactions().stream()
					.filter(tx -> tx.getTransactionDate().isAfter(threeMonthsAgo)).map(tx -> {
						Transaction txDTO = new Transaction();
						txDTO.setTransactionId(tx.getTransactionId());
						txDTO.setAmount(tx.getAmount());
						txDTO.setPoints(calculatePoints(tx.getAmount()));
						txDTO.setTransactionDate(tx.getTransactionDate());
						return txDTO;
					}).collect(Collectors.toList());

			customerDTO.setTransactions(transactions);
			customerDTO.setTotalRewards(calculateTotalRewards(transactions));
			return customerDTO;
		}).collect(Collectors.toList());
	}

	/**
	 * Retrieves the customer's name by their customer ID.
	 * 
	 * @param customerId ID of the customer.
	 * @return Name of the customer.
	 */
	@Transactional
	public String getCustomerNameById(String customerId) {
		log.info("Fetching customer name by customer Id");
		return customerRepository.findAll().stream().filter(value -> value.getCustomerId().equals(customerId))
				.map(value -> value.getName()).findFirst().get();
	}
}
