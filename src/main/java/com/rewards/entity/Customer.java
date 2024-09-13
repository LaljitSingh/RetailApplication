package com.rewards.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.OneToMany;

@Entity
public class Customer {

	@Id
	private String customerId;
	private String name;
	private int totalRewards;
	@OneToMany(mappedBy = "customer")
    @JsonManagedReference
	private List<Transaction> transactions;

	public Customer() {
		super();
	}

	public Customer(String customerId, String name) {
		super();
		this.customerId = customerId;
		this.name = name;
	}
	public Customer(String customerId, String name, List<Transaction> transactions) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.transactions = transactions;
	}
	public int getTotalRewards() {
		return totalRewards;
	}

	public void setTotalRewards(int totalRewards) {
		this.totalRewards = totalRewards;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getName() {
		return name;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	@Override
	public String toString() {
		return "Customer [name=" + name + "]";
	}
}
