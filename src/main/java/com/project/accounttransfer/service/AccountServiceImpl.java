package com.project.accounttransfer.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.error.AccountNotFoundException;
import com.project.accounttransfer.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

	private final Logger logger = LoggerFactory.getLogger(Account.class);

	@Autowired
	private AccountRepository accountRepository;

	/*
	 * @Description: saving account details into database
	 */
	@Override
	public Account saveAccount(Account acct) {
		return accountRepository.save(acct);
	}

	/*
	 * @Description: get detail of particular account.Accountid is mandatory
	 */
	@Override
	public Account getAccount(Long accountId) throws AccountNotFoundException {
		Optional<Account> acct = accountRepository.findById(accountId);
		if (!acct.isPresent()) {
			throw new AccountNotFoundException("Account not found exception");

		}
		return acct.get();
	}

	/*
	 * @Description: get total accounts present.
	 */
	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	/*
	 * @Description: Transferring the amount between source and transaction
	 * account.It involves withdrawal from source account and deposit in target
	 * account "acctNumber":"345443543","email":"a@gmail.com","balance":"400"
	 */
	@Override
	public Account transferAmountBetweenAccounts(Long sourceTxnId, Long destTxnId, Double transferAmount)
			throws AccountNotFoundException {

		/*
		 * Synchronized block so that only one thread can execute the operation of
		 * withdrawing the amount from source account and depositing in destination
		 * account is transferring process
		 */
		synchronized (this) {

			logger.info("Inside synchronized block");

			// Checking sourceAccount is valid or not & checking withdrawal amount
			Optional<Account> chkSourceAccount = accountRepository.findById(sourceTxnId);
			Optional<Account> chkDestAccount = accountRepository.findById(destTxnId);

			if (!chkSourceAccount.isPresent()) {
				throw new AccountNotFoundException("Source Account not found");
			}
			// if destination account is not found it will throw exception of account not
			// found
			else if (!chkDestAccount.isPresent()) {
				throw new AccountNotFoundException("Destination Account not found");
			} else {
				Account sourceAccount = chkSourceAccount.get();
				Account destAccount = chkDestAccount.get();

				double updatedBalance = sourceAccount.withdraw(transferAmount, sourceAccount);
				sourceAccount.setBalance(updatedBalance);

				// updated withdrawal amount in source account
				logger.info("before withdrawal from source account: " + sourceAccount.getBalance());
				accountRepository.save(sourceAccount);
				logger.info("after withdrawal from source account: " + sourceAccount.getBalance());

				// updating the balance of Destination account by depositing
				logger.info("before deposit in destination account: " + destAccount.getBalance());
				destAccount.deposit(transferAmount);
				accountRepository.save(destAccount);
				logger.info("after deposit in destination account: " + destAccount.getBalance());

				/* For notification to account Holder through mail */
				// String body = "The amount" + transferAmount + "is credited to +" +
				// destAccount.getAcctNumber()
				// + " from " + sourceAccount.getAcctNumber();

				NotificationService notificationService = (toEmail, fromEmail, body, subject) -> {
					logger.info("email sent to destination account");
				};

				// Sending mail through smtp client for sending notification
				// sendSimpleMail(sourceAccount.getEmail(),destAccount.getEmail(),body);

				// we can also use sendNotification method
				// notificationService.sendNotification(sourceAccount.getEmail(),destAccount.getEmail(),body,"Transfer
				// amount");
				return destAccount;

			}

		}
	}
}