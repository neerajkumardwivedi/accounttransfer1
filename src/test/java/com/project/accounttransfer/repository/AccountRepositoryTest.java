package com.project.accounttransfer.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.project.accounttransfer.entity.Account;

@DataJpaTest
class AccountRepositoryTest {

	@Autowired
	private AccountRepository acctReposObj;

	@Test
	@DisplayName("Saving_Account_Details")
	public void saveAccount_thenReturnAccount() {

		// give
		long accountId = 1;
		Account srcAccount = new Account(accountId, "123456", "sampleEmail@gmail.com", 200.0);

		// assert
		Account mockAcct = acctReposObj.save(srcAccount);
		assertNotNull(mockAcct);
	}

	@Test
	@DisplayName("Fetching_Account_Details_Through_Id")
	public void findbyId_thenReturnAccount() {

		// give
		long accountId = 1;
		Account srcAccount = new Account(accountId, "123456", "sampleEmail@gmail.com", 200.0);
		acctReposObj.save(srcAccount);
		// assert
		Optional<Account> mockAcct = acctReposObj.findById(accountId);
		assertNotNull(mockAcct.get());
	}

	@Test
	@DisplayName("Fetching_All_Account")
	public void findAll_thenReturnAccount() {

		// give
		long accountId = 1;
		Account srcAccount = new Account(accountId, "123456", "sampleEmail@gmail.com", 200.0);
		acctReposObj.save(srcAccount);
		// assert
		List<Account> mockAcct = acctReposObj.findAll();
		assertEquals(1, mockAcct.size());
	}

}
