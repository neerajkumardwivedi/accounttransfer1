package com.project.accounttransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.error.AccountNotFoundException;
import com.project.accounttransfer.service.AccountServiceDao;

@RestController
public class AccountRestController {

	@Autowired(required = true)
	private AccountServiceDao accServiceDao;

	private final Logger logger = LoggerFactory.getLogger(Account.class);

	// @Description: Saving Account where accNumber is mandatory & default value of
	// balance is 0.0
	// e.g. "acctNumber":"345443543","email":"a@gmail.com","balance":"400"
	@PostMapping(value = "/save")
	public Account saveAccount(@RequestBody Account acct) {
		logger.info("Inside saveDepartment");
		return accServiceDao.saveAccount(acct);
	}

	// @Description: Fetching total no of Accounts.No parameter to pass
	@GetMapping("/totalaccounts")
	public List<Account> getAllAccounts() {
		return accServiceDao.getAllAccounts();

	}

	// @Description: Fetching detail of particular account.
	// @Parameter: Mandatory accountid
	@GetMapping("/account/{id}")
	public Account getAccount(@PathVariable("id") Long accountId) throws AccountNotFoundException {
		return accServiceDao.getAccount(accountId);

	}

	// @Description: Transferring amount from one accountid to other
	// @Parameter: Mandatory sourceaccountid,destinationAccountid and amount to be
	// transferred
	// e.g.localhost:8080/transfer?sourceAccountId=3&destAccountId=1&amount=400
	@PutMapping("/transfer")
	public Account transferAmountBetweenAccounts(@RequestParam("sourceAccountId") Long sourceAccountId,
			@RequestParam("destAccountId") Long destAccountId, @RequestParam("amount") Double transferAmount)
			throws AccountNotFoundException {

		logger.info("Inside transferAmountBetweenAccounts function");

		// amount should not be negative
		if (transferAmount < 0) // deposit value is negative
		{
			throw new ArithmeticException("Transfer amount should not be negative" + transferAmount);
		}
		return accServiceDao.transferAmountBetweenAccounts(sourceAccountId, destAccountId, transferAmount);
	}

}
