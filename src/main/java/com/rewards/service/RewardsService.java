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
	CustomerRepository customerRepository;

	private static final Logger log = LoggerFactory.getLogger(RewardsService.class);

	/**
	 * Calculate the points.
	 * 
	 * @param amount
	 * @return
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
	 * Calculate rewards on monthly basis.
	 * 
	 * @param transactions
	 * @return
	 */
	public Map<Object, Integer> calculateMonthlyRewards(List<Transaction> transactions) {
		log.info("Calculating monthly rewards");
		return transactions.stream().collect(Collectors.groupingBy(tx -> {
			LocalDate date = tx.getTransactionDate();
			return date.getYear() + "-" + date.getMonth(); // Grouping by "Year-Month"
		},
				// Sum the points for each year-month group using summingInt
				Collectors.summingInt(tx -> calculatePoints(tx.getAmount()))));
	}

	/**
	 * Calculate total rewards for customer.
	 * 
	 * @param transactions
	 * @return
	 */
	public int calculateTotalRewards(List<Transaction> transactions) {
		log.info("Calculating total rewards");
		return transactions.stream().mapToInt(tx -> calculatePoints(tx.getAmount())).sum();
	}

	/**
	 * Get the list of records for the provided customer for the last three months.
	 * 
	 * @param customerId
	 * @return
	 */
	@Transactional
	public List<Transaction> getTransactionsForCustomer(String customerId) {
		LocalDate threeMonthsAgo = LocalDate.now().minus(3, ChronoUnit.MONTHS);
		log.info("Fetching data for particular customer");

		return transactionRepository.findAll().stream()
				.filter(tx -> tx.getCustomer().getCustomerId().equals(customerId)) // Filter by customerId
				.filter(tx -> tx.getTransactionDate().isAfter(threeMonthsAgo)) // Filter by last 3 months
				.collect(Collectors.toList());
	}

	/**
	 * Get all customers transaction details for last three months.
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
	 * Get the customer name by customerId.
	 * 
	 * @param customerId
	 * @return
	 */
	@Transactional
	public String getCustomerNameById(String customerId) {
		log.info("Fetching customer name by customer Id");
		return customerRepository.findAll().stream().filter(value -> value.getCustomerId().equals(customerId))
				.map(value -> value.getName()).findFirst().get();

	}
}
