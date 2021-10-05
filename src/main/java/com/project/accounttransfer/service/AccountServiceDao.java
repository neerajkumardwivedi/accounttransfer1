package com.project.accounttransfer.service;

import java.util.List;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.error.AccountNotFoundException;

public interface AccountServiceDao {

	public Account saveAccount(Account acct);

	public Account getAccount(Long acctId) throws AccountNotFoundException;

	public List<Account> getAllAccounts();

	public Account transferAmountBetweenAccounts(Long sourceTxnId, Long destTxnId, Double transferAmount)
			throws AccountNotFoundException;

}
