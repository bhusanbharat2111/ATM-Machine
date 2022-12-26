package com.example.demo.dao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Account;
import com.example.demo.repo.AccountRepo;

@Service
public class AccountDao {

	@Autowired
	AccountRepo accountRepo;

	public Optional<Account> findById(Long id) {
		return accountRepo.findById(id);
	}

	public Account save(Account account) {
		return accountRepo.save(account);
	}

}
