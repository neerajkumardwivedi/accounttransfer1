package com.project.accounttransfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.project.accounttransfer.entity.Account;
import com.project.accounttransfer.error.AccountNotFoundException;
import com.project.accounttransfer.repository.AccountRepository;
import com.project.accounttransfer.service.AccountServiceDao;

@SpringBootTest
class AccounttransferApplicationTests {

	@Autowired
	private AccountServiceDao accServiceDao;

	@MockBean
	private AccountRepository acctRepos;

	@BeforeEach
	void setup() {

	}

	@Test
	@DisplayName("Fetching All account")
	public void getAllAccountsTest() {
		long i = 12345678910L;
		long i2 = 12345678910L;
		when(acctRepos.findAll()).thenReturn(Stream
				.of(new Account(i, "345443543", "c@gmail.com", 200.0), new Account(i2, "34444", "d@gmail.com", 300.0))
				.collect(Collectors.toList()));
		assertEquals(2, accServiceDao.getAllAccounts().size());
	}

	@Test
	@DisplayName("Fetching Single account")
	public void getAccountTest() throws AccountNotFoundException {
		long i = 1;

		Account acct = new Account(i, "345443543", "c@gmail.com", 200.0);
		when(acctRepos.findById(i)).thenReturn(Optional.of(acct));
		assertEquals(acct, accServiceDao.getAccount(i));
	}

	@Test
	@DisplayName("Transfer between two accounts")
	public void transferAmountBetweenAccountsTest() throws AccountNotFoundException {
		long srcId = 1;
		long destId = 2;
		double amount = 200.0d;

		Account srcAccount = new Account(srcId, "123445", "d@gmail.com", 200);
		Account destAccount = new Account(destId, "5678843", "e@gmail.com", 100);
		when(acctRepos.findById(srcId)).thenReturn(Optional.of(srcAccount));
		when(acctRepos.findById(destId)).thenReturn(Optional.of(destAccount));

		assertEquals(300, accServiceDao.transferAmountBetweenAccounts(srcId, destId, amount).getBalance());
	}

	@Test
	void contextLoads() {
	}

}
