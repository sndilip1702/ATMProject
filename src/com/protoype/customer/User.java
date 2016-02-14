package com.protoype.customer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.prototype.bank.Account;
import com.prototype.bank.Bank;

/**
 * 
 * @author Dilip
 * The User class that contains details about the user and the accounts associated
 */
public class User {
	private String firstName;
	private String lastName;
	private String uuid;
	private byte hashedPin[];
	private ArrayList<Account> accounts;

	/**
	 * Function to Create a New User
	 * 
	 * @param firstName
	 *            - User's first name
	 * @param lastName
	 *            - User's last name
	 * @param pin
	 *            - User's PIN for the new account
	 * @param bank
	 *            - Name of the bank user is associated with
	 */
	public User(String firstName, String lastName, String pin, Bank bank) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.uuid = bank.generateNewUserID();
		this.accounts = new ArrayList<Account>();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			this.hashedPin = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			// Error can be logged after hashing to make it secure
			System.exit(1);
		}
		System.out.printf("New user %s %s with ID %s created\n", firstName, lastName, this.uuid);
	}

	public String getLastName() {
		return lastName;
	}

	public String getUuid() {
		return uuid;
	}

	public void addAccount(Account act) {
		this.accounts.add(act);
	}

	/**
	 * The function used to validate the PIN for authentication
	 * @param pin - The secret PIN of the user
	 * @return boolean for valid/invalid
	 */
	public boolean validatePin(String pin) {
		boolean returnValue = false;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			returnValue = MessageDigest.isEqual(hashedPin, md.digest(pin.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			// logging done on console for simplicity
			// This exception can not occur since MD5 is available in JavaSE
			e.printStackTrace();
			return returnValue;
		}
		return returnValue;
	}

	public double getAccountBalance(int index) {
		return this.accounts.get(index).getBalance();
	}

	public String getAccountUID(int index) {
		return this.accounts.get(index).getUuid();
	}

	public void addAcctTransaction(int index, double amount) {
		this.accounts.get(index).addTransaction(amount);
	}

	/**
	 * Function to print the Account summary for each of the account
	 */
	public void printAccountsSummary() {

		System.out.printf("\n\n%s's accounts summary\n", this.firstName);
		for (int a = 0; a < this.accounts.size(); a++) {
			System.out.printf("%d) %s\n", a + 1, this.accounts.get(a).getSummary());
		}
		System.out.println();

	}

	public int numAccounts() {
		return this.accounts.size();
	}

}
