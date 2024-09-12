package com.rewards.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import com.rewards.dao.CustomerRepository;
import com.rewards.dao.TransactionRepository;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataLoader {
	private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

	/**
	 * Insert data into Customer and Transaction tables.
	 */
	@Bean
	public CommandLineRunner loadData(CustomerRepository customerRepository,
			TransactionRepository transactionRepository) {

		return args -> {
			Customer customer1 = new Customer("customer1", "Karan");
			Customer customer2 = new Customer("customer2", "Varun");
			Customer customer3 = new Customer("customer3", "Ankit");
			Customer customer4 = new Customer("customer4", "Lucky");
			Customer customer5 = new Customer("customer5", "Amit");

			customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5));

			Transaction transaction1 = new Transaction(120, LocalDate.of(2024, 7, 15), customer1);
			Transaction transaction2 = new Transaction(80, LocalDate.of(2024, 8, 10), customer1);
			Transaction transaction3 = new Transaction(55, LocalDate.of(2024, 8, 5), customer2);
			Transaction transaction4 = new Transaction(120, LocalDate.of(2024, 9, 15), customer2);
			Transaction transaction5 = new Transaction(56, LocalDate.of(2024, 6, 25), customer3);
			Transaction transaction6 = new Transaction(130, LocalDate.of(2024, 3, 20), customer3);
			Transaction transaction7 = new Transaction(60, LocalDate.of(2024, 7, 18), customer4);
			Transaction transaction8 = new Transaction(45, LocalDate.of(2024, 8, 18), customer5);

			transactionRepository.saveAll(Arrays.asList(transaction1, transaction2, transaction3, transaction4,
					transaction5, transaction6, transaction7, transaction8));

			logger.info("data inserted into repositories");
		};
	}
}
