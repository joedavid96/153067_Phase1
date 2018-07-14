package com.cg.paymentapp.collection.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import com.cg.paymentapp.collection.beans.Customer;
import com.cg.paymentapp.collection.beans.Wallet;
import com.cg.paymentapp.collection.exception.InsufficientBalanceException;
import com.cg.paymentapp.collection.exception.InvalidInputException;
import com.cg.paymentapp.collection.repo.WalletRepo;
import com.cg.paymentapp.collection.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService 
{

	WalletRepo repo;
	
	public WalletServiceImpl()
	{
		repo = new WalletRepoImpl();
	}
	

	public WalletServiceImpl(Map<String, Customer> data) {
		super();
	}

	public boolean isValidName(String name)
	{
		
		if(((name!=null)  && name.matches("[A-Z][a-z]+")))
		{
			return true;
		}
		else
		{
			throw new InvalidInputException(" : Name cannot be NULL (or) INVALID Name.");
		}
		
	}
	
	public boolean isValidNumber(String mobileNo)
	{
		if((mobileNo!=null) && (mobileNo.matches("[4-9][0-9]{9}")))
		{
			return true;
		}
		else
		{
			throw new InvalidInputException(" : MobileNo cannot be NULL (or) INVALID Number.");
		}
	}
	
	public boolean validAmount(BigDecimal amount)
	{
		if(amount!=null && amount.compareTo(new BigDecimal(0))>0)
		{
			return true;
		}
		else
		{
			throw new InvalidInputException(" : Amount cannot be NEGATIVE.");
		}
	}

	@Override
	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException 
	{
		if(isValidName(name) && isValidNumber(mobileNo) && validAmount(amount))
		{
			if(repo.findOne(mobileNo)!=null)
			{
			throw new InvalidInputException(" : Account linked to the Entered Number ALREADY EXISTS: Please try again.\n");
			}
			else
			{
			Wallet w = new Wallet(amount);
			Customer c = new Customer(name, mobileNo, w);
			boolean flag = repo.createTransactionsList(mobileNo, c);
			boolean flag1 = repo.save(c);
			if(flag && flag1)
			{
				return c;
			}
			else
			{
				throw new InvalidInputException(" : Try at a later time. Thank you.\n");
			}
			}
		}
		else
		{
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public Customer showBalance(String mobileNo) throws InvalidInputException 
	{
		if(isValidNumber(mobileNo))
		{
		if(repo.findOne(mobileNo)==null)
		{
			throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST: Please try again. \n");
		}
		else
		{
			String log = Instant.now() + "\tViewed Balance.";
			repo.saveTransactions(mobileNo, log);
			return repo.findOne(mobileNo);
		}
		}
		else
		{
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) 
			throws InvalidInputException, InsufficientBalanceException
	{
		if( isValidNumber(sourceMobileNo) && isValidNumber(targetMobileNo) && validAmount(amount))
		{
		if(repo.findOne(sourceMobileNo)!=null)
		{
			if(repo.findOne(targetMobileNo)!=null)
			{
				BigDecimal b = repo.findOne(sourceMobileNo).getWallet().getBalance();
				BigDecimal c = repo.findOne(targetMobileNo).getWallet().getBalance();
				
				int i = b.compareTo(amount);
				
				if(i>=0)
				{
					b = b.subtract(amount);
					repo.findOne(sourceMobileNo).getWallet().setBalance(b);
					
					String log1 = Instant.now() + "\tAmount of" + amount + " Debited from A/C towards A/C linked with Number" + sourceMobileNo + ". Balance in A/C : " + b ;
					repo.saveTransactions(sourceMobileNo, log1);
					
					c = c.add(amount);
					repo.findOne(targetMobileNo).getWallet().setBalance(c);
					
					String log2 = Instant.now() + "\tAmount of" + amount + " Credited to A/C from A/C linked with Number" + targetMobileNo + ". Balance in A/C : " + c ;
					repo.saveTransactions(targetMobileNo, log2);
					
					return repo.findOne(sourceMobileNo);
				}
				else
				{
					throw new InsufficientBalanceException(" : Insufficient Balance : Please Check Balance and Try Again. Thanks.\n");
				}
			}
			else
			{
				throw new InvalidInputException(" : A/C with Entered Recipient Phone Number Does NOT EXIST : \n");
			}
		}
		else
		{
			throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
		}
		}
		else
		{
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException 
	{
		if(isValidNumber(mobileNo) && validAmount(amount))
		{
		if(repo.findOne(mobileNo)!=null)
		{
			BigDecimal b = repo.findOne(mobileNo).getWallet().getBalance();
			
			b = b.add(amount);
			
			repo.findOne(mobileNo).getWallet().setBalance(b);
			
			String log = Instant.now() + "\tAmount of" + amount + " Credited to A/C. Balance in A/C : " + b ;
			repo.saveTransactions(mobileNo, log);
			
			return repo.findOne(mobileNo);
		}
		else
		{
			throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
		}
		}
		else
		{
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException
	{
		if(isValidNumber(mobileNo) && validAmount(amount))
		{
		if(repo.findOne(mobileNo)!=null)
		{
			BigDecimal b = repo.findOne(mobileNo).getWallet().getBalance();
			int i = b.compareTo(amount);
			
			if(i>=0)
			{
			
				b = b.subtract(amount);
				
				repo.findOne(mobileNo).getWallet().setBalance(b);
				
				String log = Instant.now() + "\tAmount of" + amount + " Debited from A/C. Balance in A/C : " + b ;
				repo.saveTransactions(mobileNo, log);
				
				return repo.findOne(mobileNo);
				
			}
			else
			{
				throw new InsufficientBalanceException(" : Insufficient Balance : Please Check Balance and Try Again. Thanks.\n");
			}
			
		}
		else
		{
			throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
		}
		}
		else
		{
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public List<String> transactions(String mobileNo) throws InvalidInputException 
	{
		if(isValidNumber(mobileNo))
		{
		if(repo.getList(mobileNo)!=null)
		{
			return repo.getList(mobileNo);
		}
		else
		{
			throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
		}
		}
		else
		{
			throw new InvalidInputException(" : INVALID Input : ");
		}

	}
}
