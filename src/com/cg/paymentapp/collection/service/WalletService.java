package com.cg.paymentapp.collection.service;

import java.math.BigDecimal;
import java.util.List;

import com.cg.paymentapp.collection.beans.Customer;
import com.cg.paymentapp.collection.exception.InsufficientBalanceException;
import com.cg.paymentapp.collection.exception.InvalidInputException;

public interface WalletService {
	public Customer createAccount(String name, String mobileno, BigDecimal amount) throws InvalidInputException;
	public Customer showBalance(String mobileno) throws InvalidInputException;
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount)
			throws InvalidInputException, InsufficientBalanceException;
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException;
	public Customer withdrawAmount(String mobileNo, BigDecimal amount)
			throws InvalidInputException, InsufficientBalanceException;
	public List<String> transactions(String mobileNo) throws InvalidInputException;
}
