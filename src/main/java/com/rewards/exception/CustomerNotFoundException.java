package com.rewards.exception;

/**
 * Exception thrown when a customer is not found in the system.
 */
public class CustomerNotFoundException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new CustomerNotFoundException with the specified detail message.
	 *
	 * @param message the detail message explaining the reason for the exception
	 */
	public CustomerNotFoundException(String message) {
        super(message);
    }
}

