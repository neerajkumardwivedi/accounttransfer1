package com.project.accounttransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.error.AccountNotFoundException;
import com.project.accounttransfer.service.AccountService;

@RestController
public class AccountRestController {

	@Autowired(required = true)
	private AccountService accServiceDao;

	/* For logging purpose */
	private final Logger logger = LoggerFactory.getLogger(Account.class);

	/*
	 * @Description: Saving Account where accNumber is mandatory & default value of
	 * balance is 0.0 e.g.
	 * "acctNumber":"345443543","email":"a@gmail.com","balance":"400"
	 */
	@PostMapping(value = "/save")
	public ResponseEntity<Account> saveAccount(@RequestBody Account acct) {
		try {
			logger.info("Inside saveAccount");
			accServiceDao.saveAccount(acct);
			return ResponseEntity.status(HttpStatus.CREATED).build();
			
		}catch(Exception ex) {
			ex.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}

	/* @Description: Fetching total no of Accounts.No parameter to pass */
	@GetMapping("/totalaccounts")
	public ResponseEntity<List<Account>> getAllAccounts() {
		List<Account> list=accServiceDao.getAllAccounts();
		if(list.size()<=0) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(list));

	}

	/*
	 * @Description: Fetching detail of particular account.
	 * @Parameter: Mandatory accountid
	 */
	@GetMapping("/account/{id}")
	public ResponseEntity<Account> getAccount(@PathVariable("id") Long accountId) throws AccountNotFoundException {
		
		Account acc=accServiceDao.getAccount(accountId);
		if(acc==null) {
			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.of(Optional.of(acc));
	
	}

	/*
	 * @Description: Transferring amount from one accountid to other
	 * @Parameter: Mandatory sourceaccountid,destinationAccountid and amount to be
	 * transferred
	 * e.g.localhost:8080/transfer?sourceAccountId=3&destAccountId=1&amount=400
	 */
	@PutMapping("/transfer")
	public ResponseEntity<Account> transferAmountBetweenAccounts(@RequestParam("sourceAccountId") Long sourceAccountId,
			@RequestParam("destAccountId") Long destAccountId, @RequestParam("amount") Double transferAmount)
			throws AccountNotFoundException {

		Account acct=null;
		logger.info("Inside transferAmountBetweenAccounts function");
		// amount should not be negative
  		if (transferAmount < 0) // deposit value is negative
  		{
  			throw new ArithmeticException("Transfer amount should not be negative" + transferAmount);
  		}
          try {
        	  acct=accServiceDao.transferAmountBetweenAccounts(sourceAccountId, destAccountId, transferAmount);
        	  if(acct==null) {
        		  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        		  
        	  }else {       		  
        		  return ResponseEntity.ok().body(acct);
        	  }
        	  
          }catch(Exception ex) {
        	  ex.printStackTrace();
        	  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
          }            
	}

}
