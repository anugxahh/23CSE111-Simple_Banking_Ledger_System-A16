# Amrita Bank – Banking Ledger System (Console + JavaFX GUI)

## Project Overview

The **Amrita Bank – Banking Ledger System** is a Java-based mini project developed using **Object-Oriented Programming (OOP)** concepts, **File Handling**, and **JavaFX GUI Development**.

This project simulates real-world banking operations and allows users to manage customer accounts through both:

* Console-Based Application
* JavaFX Desktop GUI Application

The system helps manage:

* Bank Customers
* Customer Accounts
* Deposits
* Withdrawals
* Transaction History
* File-Based Data Storage
* GUI-Based Banking Dashboard

It is designed for academic submission, viva exams, GitHub portfolio, and LinkedIn project showcasing.

---

## Key Features

### 1. Customer Dashboard

Displays all customers registered in the bank along with:

* Customer ID
* Name
* Phone Number
* Account Number
* Current Balance

Available in both Console and JavaFX GUI versions.

---

### 2. Add New Customer

Allows adding a new customer with:

* Customer ID
* Full Name
* Phone Number
* Account Number
* Initial Balance

The JavaFX version provides a clean desktop form for registration.

---

### 3. Deposit Money

Allows depositing money into a customer’s account with:

* Validation for positive deposit amount
* Automatic balance update
* Transaction log generation
* File storage of transaction details

---

### 4. Withdraw Money

Allows withdrawing money after checking:

* Valid withdrawal amount
* Sufficient account balance

Prevents invalid transactions and updates records safely.

---

### 5. View Transactions (In-App)

Displays all transactions performed during the current execution of the program.

Useful for quick monitoring inside the application.

---

### 6. View Transactions (From File)

Reads saved transaction history from:

```text
data/transactions.txt
```

and displays complete transaction logs.

---

### 7. JavaFX Desktop GUI

Includes a modern banking interface with:

* Dashboard Tab
* New Customer Tab
* Transaction Tab
* Logs Tab

Designed with a professional dark-theme desktop layout.

---

### 8. File-Based Data Persistence

Customer and transaction data are stored permanently using:

* `customers.txt`
* `transactions.txt`

This ensures data remains available even after restarting the application.

---

## Technologies Used

* Java
* JavaFX
* Eclipse IDE
* Object-Oriented Programming (OOP)
* File Handling
* Collections Framework
* ArrayList
* Exception Handling
* GitHub

---

## OOP Concepts Used

### Encapsulation

Private variables with public getter and setter methods protect data security.

Example:

```java
private double balance;
public double getBalance()
```

---

### Abstraction

Methods like:

* `deposit()`
* `withdraw()`
* `createAccount()`

hide internal banking logic from the user.

---

### Polymorphism

Used through:

```java
@Override
public String toString()
```

inside `Transaction.java`.

This improves flexibility and object behavior customization.

---

### Aggregation

A `Bank` object manages multiple `Customer` objects.

```text
Bank → Customers
```

---

### Composition

Each `Customer` contains an `Account`.

```text
Customer → Account
```

If customer is removed, account relation ends.

---

### Association

All project classes are logically connected:

```text
Main → Bank → Customer → Account → Transaction → FileManager
```

This creates a complete banking workflow.

---

## Project Structure

```text
AmritaBankLedgerSystem
│
├── src
│   └── banking
│       ├── Main.java
│       ├── Bank.java
│       ├── Customer.java
│       ├── Account.java
│       ├── Transaction.java
│       ├── FileManager.java
│       └── BankingGUI.java
│
├── data
│   ├── customers.txt
│   └── transactions.txt
│
├── screenshots
│   ├── dashboard.png
│   ├── new_customer.png
│   ├── transactions.png
│   └── logs.png
│
└── README.md
```

---

## Class Description

### Main.java

Controls the complete project flow using a menu-driven system.

Handles:

* User input
* Banking operations
* Menu navigation

---

### Bank.java

Stores bank details and manages all customers.

Functions:

* Add customer
* Search customer
* View all customers

---

### Customer.java

Stores customer information and links the customer with an account.

Includes:

* ID
* Name
* Phone
* Account creation

---

### Account.java

Handles:

* Deposits
* Withdrawals
* Balance management
* Transaction creation
* File transaction saving

This is the core financial logic class.

---

### Transaction.java

Stores:

* Transaction ID
* Amount
* Transaction Type

Also processes and displays transaction details.

---

### FileManager.java

Handles:

* Saving transaction data
* Reading transaction logs
* Reading customer files

Responsible for permanent storage.

---

### BankingGUI.java

JavaFX-based desktop application interface.

Includes:

* Dashboard UI
* Customer registration form
* Transaction interface
* Transaction logs display

Provides real-world project presentation quality.

---

## How to Run

### Console Version

### Step 1

Open the project in Eclipse IDE.

### Step 2

Locate:

```text
Main.java
```

### Step 3

Right Click → Run As → Java Application

### Step 4

Use the banking menu options.

---

## JavaFX GUI Version

### Step 1

Locate:

```text
BankingGUI.java
```

### Step 2

Right Click → Run As → JavaFX Application

### Step 3

Use the Desktop Banking Interface

---

## Sample Console Output

```text
==================================
        AMRITA BANK LEDGER
==================================

--- MAIN MENU ---
1. View All Customers
2. Deposit Money
3. Withdraw Money
4. View Transactions (In-App)
5. View Transactions (From File)
6. Add New Customer
0. Exit

Enter your choice:
```

---

## Future Improvements

Possible future enhancements include:

* Admin Login System
* Role-Based Access Control
* Interest Calculation
* Loan Management
* ATM Simulation
* JavaFX Advanced UI Enhancements
* Database Integration using MySQL
* Online Banking Features
* PDF Statement Generation
* Email Notification System

---

## Learning Outcomes

Through this project, I improved my understanding of:

* Object-Oriented Programming
* JavaFX GUI Development
* File Handling
* Exception Handling
* Collections Framework
* Real-world System Design
* Software Project Structuring
* GitHub Project Management

---

## Conclusion

The **Amrita Bank – Banking Ledger System** is a strong academic and portfolio project that demonstrates:

* Real-world banking operations
* Proper OOP architecture
* File handling and persistence
* Transaction management
* Console + GUI application development
* Practical software engineering concepts

This project is highly suitable for:

* College mini project submission
* Lab evaluations
* Viva examinations
* Internship applications
* LinkedIn project showcase
* GitHub portfolio building

---

## Author

**Anugrah M Shibu**

B.Tech – Computer Science and Engineering
Amrita Vishwa Vidyapeetham
Amritapuri Campus
