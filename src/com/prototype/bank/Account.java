package com.prototype.bank;

import java.util.ArrayList;

import com.prototype.customer.User;
/**
 * 
 * @author Dilip
 * The Account class contains details about the account.
 * The transactions List contains a Log of all the activities in the Account. 
 */
public class Account {
	private AccountType type;
	private double balance;
	private String uuid;
	private User accHolder;
	private ArrayList<Transaction> transactions;

	/**
	 * The constructor for Account
	 * @param name - depicts the type of the Account following the ENUM values
	 * @param accHolder - The User object to which the Account is connected with
	 * @param bank - The Bank object where the account was created
	 * @param startingBalance - An optional starting balance
	 */
	public Account(AccountType name, User accHolder, Bank bank, double startingBalance) {
		this.type = name;
		this.accHolder = accHolder;
		this.uuid = bank.generateNewAccountID();
		this.transactions = new ArrayList<Transaction>();
		addTransaction(startingBalance);
		System.out.printf("New %s account created for %s with ID %s\n", name, accHolder.getLastName(), this.uuid);
	}

	public AccountType getName() {
		return type;
	}

	public double getBalance() {
		return balance;
	}

	public String getUuid() {
		return uuid;
	}

	public User getAccHolder() {
		return accHolder;
	}

	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}

	/**
	 * Add a new transaction to the Log
	 * @param amount - The amount to be transferred
	 * All the deposits are positive and withdrawals are negative
	 */
	public void addTransaction(double amount) {

		// create new transaction and add it to our list
		Transaction newTrans = new Transaction(amount, this);
		this.transactions.add(newTrans);
		this.updateBalance();
	}

	/**
	 * Refreshes the balance for every transaction made.
	 * A possible endpoint for Synchronization.
	 */
	public void updateBalance() {
		double refreshedBalance = 0;
		for (Transaction t : this.transactions) {
			 refreshedBalance += t.getAmount();
		}
		this.balance = refreshedBalance;
	}

	/**
	 * Fetch account Summary, displays list of Accounts along with the balance for a user.
	 * @return String of Account Summary
	 */
	public String getSummary() {
		double balance = this.getBalance();
		// format summary line depending on whether balance is negative
		if (balance >= 0) {
			return String.format("%s : $%.02f : %s", this.uuid, balance, this.type);
		} else {
			return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.type);
		}
	}

}
