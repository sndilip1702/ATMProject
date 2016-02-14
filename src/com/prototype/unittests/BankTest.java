package com.prototype.unittests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.prototype.atm.ATMConsole;
import com.prototype.bank.AccountType;
import com.prototype.bank.Bank;
import com.prototype.customer.User;

public class BankTest {
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
	public void ATMConsoleShouldBeNull() {
		assertTrue("Failed to return Null", (myBank.getConsole(0) == null));
	}
	
	@Test
	public void ATMConsoleShouldBeNotNull() {
		ATMConsole newConsole = new ATMConsole("New_Console", 10, "Tempe", myBank);
		myBank.addConsole(newConsole);
		assertTrue("Failed to return ATMConsole Object", (myBank.getConsole(0) != null));
	}
	
	@Test
	public void userShouldBeValid() {
		assertTrue("Failed to find valid user", (myBank.userLogin("1234") != null));
	}
	
	@Test
	public void userShouldBeNull() {
		assertTrue("Failed to return Null for invalid user", (myBank.userLogin("12") == null));
	}

}
