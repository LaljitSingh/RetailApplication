package com.rewards.service;

import com.rewards.dao.CustomerRepository;
import com.rewards.dao.TransactionRepository;
import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RewardsServiceTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private CustomerRepository customerRepository;

	@InjectMocks
	private RewardsService rewardsService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Test case for calculating points.
	 */
	@Test
	public void testCalculatePoints() {
		assertEquals(90, rewardsService.calculatePoints(120));
		assertEquals(50, rewardsService.calculatePoints(100));
		assertEquals(5, rewardsService.calculatePoints(55));
	}

	/**
	 * Test case for calculating monthly rewards.
	 */
	@Test
	public void testCalculateMonthlyRewards() {
		List<Transaction> transactions = Arrays.asList(new Transaction(150, LocalDate.of(2024, 4, 5)),
				new Transaction(120, LocalDate.of(2024, 8, 2)));

		Map<Object, Integer> monthlyRewards = rewardsService.calculateMonthlyRewards(transactions);
		assertEquals(60 + 30, monthlyRewards.get("2024-AUGUST"));
	}

	/**
	 * Test case for calculating total rewards.
	 */
	@Test
	public void testCalculateTotalRewards() {
		List<Transaction> transactions = Arrays.asList(new Transaction(120, LocalDate.of(2024, 8, 1)),
				new Transaction(80, LocalDate.of(2024, 9, 2)));

		int totalRewards = rewardsService.calculateTotalRewards(transactions);

		assertEquals(90 + 30, totalRewards);
	}

		
	 /**
     * Test case for fetching all customers with transactions in the last 3 months.
     */
    @Test
    public void testGetAllCustomersWithTransactionsInLast3Months() {
        List<Customer> customers = Arrays.asList(
                new Customer("customer1", "Karan", Arrays.asList(new Transaction(130, LocalDate.of(2024, 12, 5)))),
                new Customer("customer2", "Varun", Arrays.asList(new Transaction(60, LocalDate.of(2024, 8, 6))))
        );

        when(customerRepository.findAll()).thenReturn(customers);

        List<Customer> result = rewardsService.getAllCustomersWithTransactionsInLast3Months();

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findAll();
    }


	/**
	 * Test case for fetching a customer name by ID.
	 */
	@Test
	public void testGetCustomerNameById() {
		List<Customer> customers = Arrays.asList(new Customer("customer1", "Karan", null));

		when(customerRepository.findAll()).thenReturn(customers);

		String customerName = rewardsService.getCustomerNameById("customer1");

		assertEquals("Karan", customerName);
		verify(customerRepository, times(1)).findAll();
	}
}
