package com.prototype.bank;

import java.util.Date;
/**
 * 
 * @author Dilip
 * The class that acts as a Logger which records every operation along with a time-stamp
 */
public class Transaction {

	private double amount;
	@SuppressWarnings("unused")
	private Date timeStamp;
	@SuppressWarnings("unused")
	private Account transactAccount;

	public double getAmount() {
		return amount;
	}

	/**
	 * The constructor for initialization
	 * @param amount - Amount that has to be withdrawn/deposited
	 * @param transactAccount - The account for transaction
	 */
	public Transaction(double amount, Account transactAccount) {

		this.amount = amount;
		this.transactAccount = transactAccount;
		this.timeStamp = new Date();

	}

}
