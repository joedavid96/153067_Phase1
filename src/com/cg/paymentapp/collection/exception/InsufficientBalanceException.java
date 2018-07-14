package com.cg.paymentapp.collection.exception;

@SuppressWarnings("serial")
public class InsufficientBalanceException extends RuntimeException {
	
	public InsufficientBalanceException(String msg) {
		super(msg);
	}

}
