package com.prototype.atm;

import com.prototype.bank.Bank;
/**
 * 
 * @author Dilip
 * This class contains details about the ATM console/Terminal
 */
public class ATMConsole {
	@SuppressWarnings("unused")
	private String name;
	private double balance;
	@SuppressWarnings("unused")
	private String location;
	private String uuid;

	/**
	 * 
	 * @param name - A name for the ATM Machine
	 * @param balance - The balance available in the cashtray of the  machine
	 * @param location - Location of the machine
	 * @param bank - The bank with which machine is associated with
	 */
	public ATMConsole(String name, double balance, String location, Bank bank) {
		this.name = name;
		this.balance = balance;
		this.location = location;
		this.uuid = bank.generateNewATMID();
		System.out.printf("New console for %s with ID %s created.\n", bank.getName(), uuid);
	}

	public String getUuid() {
		return uuid;
	}

	public boolean isSufficientBalance(double amount) {
		return (amount < balance);
	}

	public void dispense(double amount) {
		balance -= amount;
	}
}
