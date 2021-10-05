package com.project.accounttransfer.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.error.AccountNotFoundException;
import com.project.accounttransfer.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountServiceDao {

	private final Logger logger = LoggerFactory.getLogger(Account.class);

	@Autowired
	private AccountRepository accountRepository;

	// @Autowired
	// private NotificationService notificationService;

	@Override
	public Account saveAccount(Account acct) {
		return accountRepository.save(acct);
	}

	@Override
	public Account getAccount(Long accountId) throws AccountNotFoundException {
		Optional<Account> acct = accountRepository.findById(accountId);
		if (!acct.isPresent()) {
			throw new AccountNotFoundException("Account not found exception");

		}

		return acct.get();
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account transferAmountBetweenAccounts(Long sourceTxnId, Long destTxnId, Double transferAmount)
			throws AccountNotFoundException {

		// synchronized block so that only one thread can execute the operation of
		// withdrawing the amount from source account and depositing in destination
		// account is transferring process

		synchronized (this) {

			logger.info("Inside synchronized block");

			// Checking sourceAccount is valid or not & checking withdrawal amount
			Optional<Account> chkSourceAccount = accountRepository.findById(sourceTxnId);
			Optional<Account> chkDestAccount = accountRepository.findById(destTxnId);

			if (!chkSourceAccount.isPresent()) {

				throw new AccountNotFoundException("Source Account not found");

			} else if (!chkDestAccount.isPresent()) {

				throw new AccountNotFoundException("Destination Account not found");

			} else {

				Account sourceAccount = accountRepository.findById(sourceTxnId).get();
				Account destAccount = accountRepository.findById(destTxnId).get();
				// if (Objects.nonNull(srcAccount.getAccHolderName()) &&
				// Objects.nonNull(destAccount.getAccHolderName())) {

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
					System.out.println("Sending Email...");
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

	public void sendSimpleMail(String sentFrom, String toEmail, String body) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(sentFrom);
		message.setTo(toEmail);
		message.setText(body);
		message.setSubject("Transferred Amount");
		// mailSender.send(message);

	}

}