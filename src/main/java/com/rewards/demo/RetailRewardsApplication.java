package com.rewards.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The entry point for the Retail Rewards Application.
 * 
 * This class bootstraps the Spring Boot application and configures the
 * component scanning, entity scanning, and JPA repository management for the
 * rewards system.
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.rewards.service", "com.rewards.controller", "com.rewards.loader",
		"com.rewards.exception" })
@EnableJpaRepositories(basePackages = "com.rewards.dao")
@EntityScan(basePackages = "com.rewards.entity")
@EnableTransactionManagement
public class RetailRewardsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RetailRewardsApplication.class, args);
	}
}
