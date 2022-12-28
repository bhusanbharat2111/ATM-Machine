package com.example.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.AccountDao;
import com.example.demo.model.Account;
import com.example.demo.model.CreateAccountDetails;
import com.example.demo.model.Login;

@Service
public class AccountService {

	Account account;
	Optional<Account> optionalAccount;

	@Autowired
	AccountDao accountDao;

	public String validate(Login login) {
		optionalAccount = accountDao.findById(login.getAccountNumber());
		if (optionalAccount.isEmpty()) {
			optionalAccount = null;
			return "Entered wrong customer Id";
		} else {
			account = optionalAccount.get();
			if (account.getPin() != login.getPin()) {
				finallyBlock();
				return "Entered pin is incorrect";
			}
		}
		return AccountService.options();

	}

	public static String options() {
		return "...........Login successfull............\n  < This is Bharat Bhusan's ATM Service >  "
				+ "\n\nNow you can process >>>>>>>>> \n1.Deposit\n2.Withdrow\n3.Check Balance\n"
				+ "4.Create Account\n5.Pin Update\n6.Phone Number Update ";
	}

	public String deposit(int dAmount) {
		if (this.optionalAccount == null) {
			return "Please verify your account first";
		}
		if (dAmount % 100 != 0) {
			return "Enter multiple of 100, 200, 500, 2000";
		}
		account.setAmount(account.getAmount() + dAmount);
		accountDao.save(account);
		finallyBlock();
		return (dAmount + " /- deposited successfully");
	}

	public String withdraw(int wAmount) {
		if (this.optionalAccount == null) {
			return "Please verify your account first";
		}
		if (wAmount % 100 != 0) {
			return "Enter multiple of 100, 200, 500, 2000";
		}
		if (wAmount > account.getAmount()) {
			return "Insufficient balance";
		}
		account.setAmount(account.getAmount() - wAmount);
		accountDao.save(account);
		finallyBlock();
		return (wAmount + " /- withdrew successfully");
	}

	public String checkBalance() {
		if (this.optionalAccount == null) {
			return "Please verify your account first";
		}
		double balance = account.getAmount();
		finallyBlock();
		return (balance + "");
	}

	public Account createAccount(CreateAccountDetails createAccountDetails) {
		Account account = new Account();
		account.setUsername(createAccountDetails.getName());
		account.setMobile(createAccountDetails.getPhonenumber());
		account.setPin(createAccountDetails.getPin());
		account.setAmount(00.00);
		return accountDao.save(account);
	}

	public String pinUpdate(int newPin) {
		if (this.optionalAccount == null) {
			return "Please verify your account first";
		}
		account.setPin(newPin);
		accountDao.save(account);
		finallyBlock();
		return "New Pin updated successfully";
	}

	public String phoneNumberUpdate(long newPhoneNumber) {
		if (this.optionalAccount == null) {
			return "Please verify your account first";
		}
		account.setMobile(newPhoneNumber);
		accountDao.save(account);
		finallyBlock();
		return "Mobile Number updated successfully";
	}

	public String searchAccount(long cid) {
		optionalAccount = accountDao.findById(cid);
		if (optionalAccount.isEmpty()) {
			return "Entered wrong customer Id";
		}
		return "Enter the four digit otp that is sent to your registered mobile number";
	}

	public String enterOTP(int otp) {
		if (otp > 999 && otp < 10000) {
			account = optionalAccount.get();
			return "Create a new pin";
		}
		optionalAccount = null;
		return "Incorrect otp entered";
	}
	
	private void finallyBlock() {
		optionalAccount = null;
		account = null;
	}

}
