package com.project.accounttransfer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.accounttransfer.entity.Account;

@Repository
public interface AccountRepository  extends JpaRepository<Account,Long> {

}
