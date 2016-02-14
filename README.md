# ATMProject
## Description and Workflow
----------------------------
The System is built with an objective of extensibility and using OOPS concepts. The ATMMainClass contains the main function which invokes a Command Line Interface for the interaction. The System was built based on assumptions that have been listed below. Unit testing was done for each class based on the methods used. The design consists of a Bank which can have multiple Users, each User in turn can have multiple Accounts. Each of the Banks can also have multiple ATM machines. ATMConsole is a vital class and it is used for dispensing cash.
Each user is identified using their secret PIN which can be used to login into the system menu. Once the user is authenticated, a home screen displays the various options available at the ATM. Show balance will display a summary of all the accounts and their current balance held by a user. The withdraw options helps in withdrawal of funds from the user’s selected account. The deposit option is similar to withdraw which allows user to select accounts to deposit the amount. The program requires certain default values like initialization of Bank, User, Account and ATMConsole objects in order to work efficiently. The code contains Sample data that was used for dry run.

**Libraries used:** JavaSE-1.8, Junit 4

## Assumptions
---------------
*	Each user will register using a unique PIN
*	Only withdrawals affect the ATM cash tray and deposits are wired to the account directly
*	All the transactions are made in a base currency (USD)
*	Concurrency control has not been considered for this implementation
*	Corner cases like having NULL values for PIN/last name/first name/Account type have not been considered
*	All the inputs for the initial values have not been validated assuming it will be entered manually and correctly as per the definitions
*	All the inputs made from Keyboard will be numeric since an ATM only allows for numeric entries using a Numpad, hence no validation is carried out for non-numeric values
*	Generic exceptions have been handled which can be logged, no user defined exceptions have been used
