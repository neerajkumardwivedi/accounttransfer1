/**
 * 
 */
package com.project.accounttransfer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.repository.AccountRepository;
import com.project.accounttransfer.service.AccountServiceImpl;

/**
 * @author apple
 *
 */
@SpringBootTest
class AccountRestControllerTest {

	@InjectMocks
	private AccountRestController accRestContollerObj;

	@Mock
	private AccountServiceImpl accServiceImplObj;

	@Mock
	private AccountRepository acctReposObj;

	/**
	 * Test method for
	 * {@link com.project.accounttransfer.controller.AccountRestController#saveAccount(com.project.accounttransfer.entity.Account)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("Saving_Account_Details")
	void testSaveAccount() throws Exception {

		// give
		long accountId = 1;
		Account srcAccount = new Account(accountId, "123456", "sampleEmail@gmail.com", 200.0);

		// when //then
		when(accServiceImplObj.saveAccount(srcAccount)).thenReturn(srcAccount);

		// assert
		ResponseEntity<Account> mockAcct = accRestContollerObj.saveAccount(srcAccount);
		// assertEquals(HttpStatus.CREATED,mockAcct.getStatusCode().value());
		assertNotNull(mockAcct.getStatusCode().value());

	}

	/**
	 * Test method for
	 * {@link com.project.accounttransfer.controller.AccountRestController#getAllAccounts()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("Fetching_All_Account_Details")
	void testGetAllAccounts() throws Exception {

		// give
		long accountId = 1;
		long destAccountId = 2;
		Account srcAccount = new Account(accountId, "123456", "sampleEmail@gmail.com", 200.0);
		Account destAccount = new Account(destAccountId, "7890", "sampleTest@gmail.com", 300.0);
		List<Account> accountList = new ArrayList<Account>();
		accountList.add(srcAccount);
		accountList.add(destAccount);

		// when //then
		when(accServiceImplObj.getAllAccounts()).thenReturn(accountList);

		// assert
		ResponseEntity<List<Account>> accList = accRestContollerObj.getAllAccounts();
		assertEquals(2, accList.getBody().size());
	}

	/**
	 * Test method for
	 * {@link com.project.accounttransfer.controller.AccountRestController#getAccount(java.lang.Long)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("Fetching_Single_Account_Details")
	void testGetAccount() throws Exception {

		// give
		long accountId = 1;
		Account srcAccount = new Account(accountId, "123456", "sampleEmail@gmail.com", 200.0);

		// when //then
		when(accServiceImplObj.getAccount(accountId)).thenReturn(srcAccount);

		// assert
		ResponseEntity<Account> mockAcct = accRestContollerObj.getAccount(accountId);
		assertEquals(srcAccount.getAcctNumber(), mockAcct.getBody().getAcctNumber());

	}

	/**
	 * Test method for
	 * {@link com.project.accounttransfer.controller.AccountRestController#transferAmountBetweenAccounts(java.lang.Long, java.lang.Long, java.lang.Double)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@DisplayName("Transfer_Between_Two_Accounts")
	void testTransferAmountBetweenAccounts() throws Exception {

		// give
		long srcAccountId = 1;
		long destAccountId = 2;
		Account srcAccount = new Account(srcAccountId, "123456", "sampleEmail@gmail.com", 200.0);
		Account destAccount = new Account(destAccountId, "7890", "sampleTest@gmail.com", 300.0);
		Account destModifiedAccount = new Account(destAccountId, "7890", "sampleTest@gmail.com", 500.0);

		// when //then
		when(acctReposObj.findById(srcAccountId)).thenReturn(Optional.of(srcAccount));
		when(acctReposObj.findById(destAccountId)).thenReturn(Optional.of(destAccount));
		when(accServiceImplObj.transferAmountBetweenAccounts(srcAccountId, destAccountId, 200.0))
				.thenReturn(destModifiedAccount);

		// assert
		ResponseEntity<Account> resultAcc = accRestContollerObj.transferAmountBetweenAccounts(srcAccountId,
				destAccountId, 200.0);
		assertEquals(destModifiedAccount.getBalance(), resultAcc.getBody().getBalance());

	}

}
