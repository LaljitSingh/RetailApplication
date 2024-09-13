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
	 * Loads initial data into the Customer and Transaction tables when the 
	 * application starts.
	 * 
	 * @param customerRepository the repository to manage Customer entities
	 * @param transactionRepository the repository to manage Transaction entities
	 * @return CommandLineRunner that inserts sample data into the database
	 */
	@Bean
	public CommandLineRunner loadData(CustomerRepository customerRepository,
			TransactionRepository transactionRepository) {

		return args -> {
			// Create customers
			Customer customer1 = new Customer("customer1", "Karan");
			Customer customer2 = new Customer("customer2", "Varun");
			Customer customer3 = new Customer("customer3", "Ankit");
			Customer customer4 = new Customer("customer4", "Lucky");
			Customer customer5 = new Customer("customer5", "Amit");

			// Save customers to the repository
			customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4, customer5));

			// Create transactions for each customer
			Transaction transaction1 = new Transaction(120, LocalDate.of(2024, 7, 15), customer1);
			Transaction transaction2 = new Transaction(80, LocalDate.of(2024, 8, 10), customer1);
			Transaction transaction3 = new Transaction(55, LocalDate.of(2024, 8, 5), customer2);
			Transaction transaction4 = new Transaction(120, LocalDate.of(2024, 9, 15), customer2);
			Transaction transaction5 = new Transaction(56, LocalDate.of(2024, 6, 25), customer3);
			Transaction transaction6 = new Transaction(130, LocalDate.of(2024, 3, 20), customer3);
			Transaction transaction7 = new Transaction(60, LocalDate.of(2024, 7, 18), customer4);
			Transaction transaction8 = new Transaction(45, LocalDate.of(2024, 8, 18), customer5);

			// Save transactions to the repository
			transactionRepository.saveAll(Arrays.asList(transaction1, transaction2, transaction3, transaction4,
					transaction5, transaction6, transaction7, transaction8));

			// Log a message indicating successful data insertion
			logger.info("Data inserted into repositories");
		};
	}
}
