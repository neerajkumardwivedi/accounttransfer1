package com.project.accounttransfer.service;

import java.util.List;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.error.AccountNotFoundException;

public interface AccountService {

	/* Purpose: For saving all account */
	public Account saveAccount(Account acct);

	/* Purpose: for fetching single account */
	public Account getAccount(Long acctId) throws AccountNotFoundException;

	/* Purpose: for fetching all accounts */
	public List<Account> getAllAccounts();

	/* Purpose: For transfer amount between accounts */
	public Account transferAmountBetweenAccounts(Long sourceTxnId, Long destTxnId, Double transferAmount)
			throws AccountNotFoundException;

}
