package com.rewards.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;
	private double amount;
	private LocalDate transactionDate;
	private int points;
	@ManyToOne
	@JsonBackReference
	private Customer customer;

	public Transaction() {
		super();
	}

	public Transaction(double amount, LocalDate transactionDate, Customer customer) {
		super();
		this.amount = amount;
		this.transactionDate = transactionDate;
		this.customer = customer;
	}

	public Transaction(double amount, LocalDate transactionDate) {
		super();
		this.amount = amount;
		this.transactionDate = transactionDate;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public double getAmount() {
		return amount;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
