package com.prototype.unittests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.prototype.bank.AccountType;
import com.prototype.bank.Bank;
import com.protoype.customer.User;

public class AccountTest {

	Bank myBank;
	User testUser;
	
	@Before
	public void setUp() throws Exception {
		myBank = new Bank("BofA");
		testUser = myBank.addUser("Test", "User", "1234", AccountType.Savings, 100.00);
	}

	@After
	public void tearDown() throws Exception {
		myBank = null;
		testUser = null;
	}
	
	@Test
	public void accountShouldBeOne() {
		assertTrue("Failed to create account", (testUser.numAccounts() == 1));
	}

	@Test
	public void balanceShouldBeSame() {
		assertTrue("Failed initial balance check", (testUser.getAccountBalance(0) == 100.00));
	}
	
	@Test
	public void balanceShouldDecrease(){
		testUser.addAcctTransaction(0, -10);
		assertTrue("Failed withdraw balance check", (testUser.getAccountBalance(0) == 90.00));
	}
	
	@Test
	public void balanceShouldIncrease(){
		testUser.addAcctTransaction(0, 100);
		assertTrue("Failed deposit balance check", (testUser.getAccountBalance(0) == 200.00));
	}
	
	@Test
	public void balanceShouldChange(){
		testUser.addAcctTransaction(0, 100);
		testUser.addAcctTransaction(0, -20);
		assertTrue("Failed multiple balance update", (testUser.getAccountBalance(0) == 180.00));
	}
	
}
