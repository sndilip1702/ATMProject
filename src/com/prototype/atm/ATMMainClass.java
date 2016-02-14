package com.prototype.atm;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.prototype.bank.Account;
import com.prototype.bank.AccountType;
import com.prototype.bank.Bank;
import com.prototype.customer.User;

/**
 * @author Dilip 
 * This is the main class which initializes values and runs the
 *         script for the Command Line Interface
 */
public class ATMMainClass {

	public static void main(String[] args) {
		// Keyboard Input
		Scanner sc = new Scanner(System.in);

		// initialize bank and test users
		Bank bank = new Bank("Bank of Tempe");
		ATMConsole newConsole = new ATMConsole("New_Console", 1000, "Tempe", bank);
		bank.addConsole(newConsole);
		User testUser = bank.addUser("Tony", "Stark", "1234", AccountType.Savings, 100.00);
		Account newAccount = new Account(AccountType.Checking, testUser, bank, 100.00);
		testUser.addAccount(newAccount);
		bank.addAccount(newAccount);

		User activeUser;
		// defaulting active console to 1 for simplicity
		ATMConsole activeConsole = bank.getConsole(0);
		while (true) {
			// stay in login prompt until successful login
			activeUser = mainMenuPrompt(bank, sc);
			// stay in main menu until user quits
			boolean exitValue = printUserMenu(activeUser, activeConsole, sc);
			if(!exitValue){
				break;
			}
		}
	}

	/**
	 * The function that displays first screen and takes user credentials as
	 * input
	 * 
	 * @param myBank
	 *            - the Bank object to which the ATM is associated with
	 * @param sc
	 *            - Scanner for Keyboard input
	 * @return
	 */
	private static User mainMenuPrompt(Bank myBank, Scanner sc) {
		// temporary variables
		String pin;
		User authorizedUser;

		// prompt for userID and PIN and loop until login success
		do {
			System.out.printf("\n\nWelcome to %s\n", myBank.getName());
			System.out.print("Please Enter pin: ");
			pin = sc.nextLine();
			// user authentication
			authorizedUser = myBank.userLogin(pin);
			if (authorizedUser == null) {
				System.out.println("Incorrect user ID/pin combination.Please try again");
			}

		} while (authorizedUser == null);

		return authorizedUser;
	}
	
	/**
	 * Prompt to accept user selection
	 * @param sc - Scanner for Keyboard input
	 * @return the selection integer from the menu
	 */
	private static int printUserMenuUtil(Scanner sc){
		int selection = 0;
		try {
			do {
				System.out.println("Please select an option :::::");
				System.out.println("  1) Show account balance");
				System.out.println("  2) Withdraw");
				System.out.println("  3) Deposit");
				System.out.println("  4) Quit");
				System.out.println();
				System.out.print("Enter choice: ");
				selection = sc.nextInt();
				if (selection < 1 || selection > 4) {
					System.out.println("Invalid selection!!!! Please choose between 1-4.");
				}
			} while (selection < 1 || selection > 4);
			
	}
		catch (InputMismatchException e) {
			System.out.println("Invalid Entry!!! Use Integers only!!");
		}
		catch (NoSuchElementException e) {
			System.out.println("Invalid Entry!!! Use Integers only!!");
			
		}
		catch (Exception e) {
			System.out.println("Invalid Entry!!! Use Integers only!!");
		}
		return selection;
	}
	
	/**
	 * The function that prints Main Menu after user authentication
	 * 
	 * @param user
	 *            - The user object of the authenticated user
	 * @param activeConsole
	 *            - The ATMConsole object for the terminal the user is at
	 * @param sc
	 *            - Scanner for Keyboard input
	 */
	private static boolean printUserMenu(User user, ATMConsole activeConsole, Scanner sc) {
		// temporary variables
		int selection;
		boolean doNotExit = true;
		
		do {
			// create a new function
			selection = printUserMenuUtil(sc);
			switch (selection) {
			case 1:
				user.printAccountsSummary();
				break;
			case 2:
				user.printAccountsSummary();
				withdraw(user, activeConsole, sc);
				break;
			case 3:
				user.printAccountsSummary();
				deposit(user, sc);
				break;
			case 4:
				System.out.println("Thank you.");
				doNotExit = false;
			}
			if (selection != 4) {
				doNotExit = true;
			} 
		} while (doNotExit);
		
		return doNotExit;
		
	}

	/**
	 * The module responsible for withdrawing funds from the account
	 * 
	 * @param user
	 *            - The user object of the authenticated user
	 * @param activeConsole
	 *            - The ATMConsole object for the terminal the user is at
	 * @param sc
	 *            - Scanner for Keyboard input
	 */
	private static void withdraw(User user, ATMConsole activeConsole, Scanner sc) {
		// temporary variables
		int accSerial; // Serial number of the Account for an individual user
		double amount;
		double balance;

		// get account to withdraw from if multiple accounts present
		do {
			if (user.numAccounts() > 1) {
				System.out.printf("Enter the serial number (1-%d) of the account to withdraw from: ",
						user.numAccounts());
			} else {
				System.out.printf("Enter the serial number (1) of the account to withdraw from: ", user.numAccounts());
			}
			accSerial = sc.nextInt() - 1;
			if (accSerial < 0 || accSerial >= user.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (accSerial < 0 || accSerial >= user.numAccounts());

		balance = user.getAccountBalance(accSerial);
		// get amount to transfer
		do {
			System.out.printf("Enter the amount to withdraw (max available $%.02f): $", balance);
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero.");
			} else if (amount > balance) {
				System.out.printf("Amount must not be greater than balance of $%.02f.\n", balance);
			}
		} while (amount < 0 || amount > balance);

		// Read all other inputs
		sc.nextLine();

		if (activeConsole.isSufficientBalance(amount)) {
			// Perform transaction
			user.addAcctTransaction(accSerial, -1 * amount);
			activeConsole.dispense(amount);
			System.out.println("Please collect money and card. Thank you.");
		} else {
			System.out.printf("Sufficient funds not available, try entering a smaller amount!!");
		}
	}

	/**
	 * The module responsible for depositing funds to the account
	 * 
	 * @param user
	 *            - The user object of the authenticated user
	 * @param sc
	 *            - Scanner for Keyboard input
	 */
	private static void deposit(User user, Scanner sc) {
		// Temporary variables
		int accSerial; // Serial number of the Account for an individual user
		double amount;

		// get account to deposit
		do {
			if (user.numAccounts() > 1) {
				System.out.printf("Enter the serial number (1-%d) of the account to withdraw from: ",
						user.numAccounts());
			} else {
				System.out.printf("Enter the serial number (1) of the account to withdraw from: ", user.numAccounts());
			}
			accSerial = sc.nextInt() - 1;
			if (accSerial < 0 || accSerial >= user.numAccounts()) {
				System.out.println("Invalid account. Please try again.");
			}
		} while (accSerial < 0 || accSerial >= user.numAccounts());

		// get amount to deposit
		do {
			System.out.printf("Enter the amount to deposit: $");
			amount = sc.nextDouble();
			if (amount < 0) {
				System.out.println("Amount must be greater than zero!!!");
			}
		} while (amount < 0);

		// Read all other values
		sc.nextLine();

		// Perform transaction
		user.addAcctTransaction(accSerial, amount);
	}

}
