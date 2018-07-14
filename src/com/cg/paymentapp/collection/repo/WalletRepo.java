package com.cg.paymentapp.collection.repo;

import java.util.List;

import com.cg.paymentapp.collection.beans.Customer;

public interface WalletRepo 
{

	public boolean save(Customer customer);
	
	public Customer findOne(String mobileNo);
	
	public boolean createTransactionsList(String mobileNo, Customer customer);
	
	public boolean saveTransactions(String mobileNo, String log);
	
	public List<String> getList(String mobileNo);
	
}
