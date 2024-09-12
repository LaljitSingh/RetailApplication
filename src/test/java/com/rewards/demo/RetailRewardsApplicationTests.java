package com.rewards.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.rewards.entity.Transaction;
import com.rewards.service.RewardsService;

@SpringBootTest
class RetailRewardsApplicationTests {

	@Test
	void contextLoads() {
	}

	private final RewardsService rewardsService = new RewardsService();

	@Test
	public void testCalculatePoints() {
		assertEquals(90, rewardsService.calculatePoints(120));
		assertEquals(50, rewardsService.calculatePoints(100));
		assertEquals(0, rewardsService.calculatePoints(40));
	}

	@Test
	public void testCalculateMonthlyRewards() {
		List<Transaction> transactions = Arrays.asList(new Transaction(120, LocalDate.of(2024, 8, 1)),
				new Transaction(75, LocalDate.of(2024, 8, 15)));
		assertEquals(115, rewardsService.calculateTotalRewards(transactions));
	}
}
