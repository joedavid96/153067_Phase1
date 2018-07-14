package com.cg.paymentapp.collection.beans;

import java.io.Serializable;

public class Customer implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//member variables
	
	private String name;
	private String mobileNo;
	private Wallet wallet;
	
	//getters and setters
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
	//toString
	
	@Override
	public String toString() {
		return "Customer [name=" + name + ", mobileNo=" + mobileNo + ", wallet=" + wallet + "]";
	}
	
	//constructors
	
	public Customer(String name, String mobileNo, Wallet wallet) 
	{
		super();
		
		this.name = name;
		this.mobileNo = mobileNo;
		this.wallet = wallet;
	}
	public Customer(String name, String mobileNo) {
		super();
		this.name = name;
		this.mobileNo = mobileNo;
	}
	public Customer() {
		super();
	}
	
}
