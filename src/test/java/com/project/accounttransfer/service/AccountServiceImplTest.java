package com.project.accounttransfer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.error.AccountNotFoundException;
import com.project.accounttransfer.repository.AccountRepository;

@SpringBootTest
class AccountServiceImplTest {

	@InjectMocks
	private AccountServiceImpl accServiceImplObj;

	@Mock
	private AccountRepository acctReposObj;

	@BeforeEach
	void setUp() throws Exception {

	}

	@Test
	@DisplayName("Saving Account")
	void testSaveAccount() {

		// give
		long accountId = 1;
		Account srcAccount = new Account(accountId, "123456", "sampleEmail@gmail.com", 200.0);

		// when //then
		when(acctReposObj.save(srcAccount)).thenReturn(srcAccount);

		// assert
		Account mockAcct = accServiceImplObj.saveAccount(srcAccount);
		assertEquals(srcAccount.getAcctNumber(), mockAcct.getAcctNumber());
	}

	@Test
	@DisplayName("Fetching Single account")
	void testGetAccount() throws AccountNotFoundException {

		// give
		long srcAccountId = 1;
		Account acct = new Account(srcAccountId, "123456", "sampleEmail@gmail.com", 200.0);
		Optional<Account> optionalAcc = Optional.of(acct);

		// when thenReturn
		when(acctReposObj.findById(srcAccountId)).thenReturn(optionalAcc);

		assertEquals(optionalAcc.get(), accServiceImplObj.getAccount(srcAccountId));
	}

	@Test
	@DisplayName("Fetching All account")
	void testGetAllAccounts() {

		// give
		long accountId = 1;
		long destAccountId = 2;
		Account srcAccount = new Account(accountId, "123456", "sampleEmail@gmail.com", 200.0);
		Account destAccount = new Account(destAccountId, "7890", "sampleTest@gmail.com", 300.0);
		List<Account> accountList = new ArrayList<Account>();
		accountList.add(srcAccount);
		accountList.add(destAccount);

		// when //then
		when(acctReposObj.findAll()).thenReturn(accountList);

		// assert
		List<Account> accList = accServiceImplObj.getAllAccounts();
		assertEquals(2, accList.size());

	}

	@Test
	@DisplayName("Transfer between two accounts")
	void testTransferAmountBetweenAccounts() throws AccountNotFoundException {

		// give
		long srcAccountId = 1;
		long destAccountId = 2;
		Account srcAccount = new Account(srcAccountId, "123456", "sampleEmail@gmail.com", 200.0);
		Account destAccount = new Account(destAccountId, "7890", "sampleTest@gmail.com", 300.0);
		Account destModifiedAccount = new Account(destAccountId, "7890", "sampleTest@gmail.com", 500.0);

		// when //then
		when(acctReposObj.findById(srcAccountId)).thenReturn(Optional.of(srcAccount));
		when(acctReposObj.findById(destAccountId)).thenReturn(Optional.of(destAccount));

		// assert
		Account accModified = accServiceImplObj.transferAmountBetweenAccounts(srcAccountId, destAccountId, 200.0);
		assertEquals(destModifiedAccount.getBalance(), accModified.getBalance());

	}

}
