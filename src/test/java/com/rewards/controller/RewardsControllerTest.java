package com.rewards.controller;

import com.rewards.entity.Customer;
import com.rewards.entity.Transaction;
import com.rewards.service.RewardsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

class RewardsControllerTest {

	private MockMvc mockMvc;

	@Mock
	private RewardsService rewardsService;

	@InjectMocks
	private RewardsController rewardsController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(rewardsController).build();
	}

	/**
	 * Test for fetching rewards for a valid customer ID
	 */
	@Test
	public void testGetRewards_ValidCustomer() throws Exception {
		List<Transaction> transactions = Arrays.asList(new Transaction(120, LocalDate.of(2024, 8, 10)));

		Map<String, Object> mockMonthlyRewards = new HashMap<>();
		mockMonthlyRewards.put("2024-August", 90);

		when(rewardsService.getTransactionsForCustomer(anyString())).thenReturn(transactions);
		when(rewardsService.calculateTotalRewards(transactions)).thenReturn(90);
		when(rewardsService.getCustomerNameById(anyString())).thenReturn("Karan");

		mockMvc.perform(get("/api/rewards").param("customerId", "customer1").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.customerId").value("customer1"))
				.andExpect(jsonPath("$.customerName").value("Karan")).andExpect(jsonPath("$.totalRewards").value(90));
	}

	/**
	 * Test for fetching rewards for an empty customer ID
	 */
	@Test
	public void testGetRewards_EmptyCustomerId() throws Exception {
		mockMvc.perform(get("/api/rewards").param("customerId", "").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").value("Customer ID cannot be empty"));
	}

	/**
	 * Test for fetching all customers with transactions
	 */
	@Test
	public void testGetAllCustomers_Valid() throws Exception {
		List<Customer> customers = Arrays.asList(new Customer("customer1", "Karan",
				Arrays.asList(new Transaction(120.0, LocalDate.of(2024, 8, 10), null))));

		when(rewardsService.getAllCustomersWithTransactionsInLast3Months()).thenReturn(customers);

		mockMvc.perform(get("/api/getAllCustomerTransaction").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].customerId").value("customer1"))
				.andExpect(jsonPath("$[0].name").value("Karan"));
	}

}
