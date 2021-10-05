package com.project.accounttransfer.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;

import java.text.NumberFormat;

@Entity
@Data

public class Account
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;
    
    @NotBlank(message = "Please add Account Number")
    private String acctNumber;
    private String email;
    
    @Builder.Default
    @PositiveOrZero
    private double balance=0.0;
   



public Long getAccountId() {
		return accountId;
	}


	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}



	public String getAcctNumber() {
		return acctNumber;
	}


	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public double getBalance() {
		return balance;
	}


	public void setBalance(double balance) {
		this.balance = balance;
	}



public Account() {
	super();
}



public Account(Long accountId, @NotBlank(message = "Please add Account Number") String acctNumber) {
	super();
	this.accountId = accountId;
	this.acctNumber = acctNumber;
}


public Account(Long accountId, @NotBlank(message = "Please add Account Number") String acctNumber, String email,
		@PositiveOrZero double balance) {
	super();
	this.accountId = accountId;
	this.acctNumber = acctNumber;
	this.email = email;
	this.balance = balance;
}


//for checking whether withdrawal is possible or not
public boolean isWithdrawalPossible(double amount,Account acct){

       boolean isWithdrawalPossible=true;

           if (amount > acct.getBalance()) // withdraw value exceeds balance
           {
               isWithdrawalPossible=false;
           }

           return isWithdrawalPossible;
   }

//@Parameter:amount= Withdrawing the amount from account
    public synchronized double withdraw (double amount,Account acct)
    {
        if (amount < 0) // withdraw value is negative
        {
            throw new ArithmeticException("Error: Withdraw amount is invalid.");
        
        }
        else
        if (amount > acct.getBalance()) // withdraw value exceeds balance
        {

            throw new ArithmeticException("Error: Insufficient funds.");
        }
        else
            balance = balance - amount;
        return balance;
    }

    //-----------------------------------------------------------------
    // Validates the transaction, then deposits the specified amount
    // into the account. Returns the new balance.
    //-----------------------------------------------------------------
  //@Parameter:amount= depositing the amount in account
    public synchronized double deposit (double amount)
    {
        if (amount < 0) // deposit value is negative
        {
            System.out.println ("Error: Deposit amount is invalid.");
           
        }
        else
            balance = balance + amount;
        return balance;
    }



}
