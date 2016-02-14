package com.prototype.unittests;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.prototype.bank.AccountType;
import com.prototype.bank.Bank;
import com.protoype.customer.User;

public class UserTest {
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
	public void PINShouldBeValid() {
		assertTrue("Failed to authenticate valid PIN", testUser.validatePin("1234"));
	}

	@Test
	public void PINShouldBeInvalid() {
		assertTrue("Failed to authenticate Invalid PIN", !testUser.validatePin("12"));
	}
}
