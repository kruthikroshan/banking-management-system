import java.time.LocalDateTime;
import java.util.*;

/* -------- Interface -------- */
interface BankOperation {
    void deposit(double amount);

    boolean withdraw(double amount);

    void checkBalance();
}

/* -------- Transaction Class -------- */
class Transaction {
    String type;
    double amount;
    double finalBalance;
    String time;

    Transaction(String type, double amount, double finalBalance) {
        this.type = type;
        this.amount = amount;
        this.finalBalance = finalBalance;
        this.time = LocalDateTime.now().toString();
    }

    @Override
    public String toString() {
        return time + " | " + type + " | ₹" + amount + " | Balance: ₹" + finalBalance;
    }
}

/* -------- Abstract Account Class -------- */
abstract class Account implements BankOperation {

    final int accountNumber;
    private String password;
    protected double balance;

    private int wrongAttempts = 0;
    private boolean locked = false;

    protected List<Transaction> transactions = new ArrayList<>();

    Account(int accountNumber, String password, double balance) {
        this.accountNumber = accountNumber;
        this.password = password;
        this.balance = balance;
    }

    int getAccountNumber() {
        return accountNumber;
    }

    boolean isLocked() {
        return locked;
    }

    boolean passwordCheck(String password) {
        if (locked) {
            System.out.println("Account Locked! Contact Admin.");
            return false;
        }

        if (this.password.equals(password)) {
            wrongAttempts = 0;
            return true;
        } else {
            wrongAttempts++;
            System.out.println("Incorrect Password! Attempts left: " + (3 - wrongAttempts));

            if (wrongAttempts >= 3) {
                locked = true;
                System.out.println("Account Locked due to 3 wrong attempts!");
            }
            return false;
        }
    }

    void passwordChange(String newPassword) {
        this.password = newPassword;
    }

    void addTransaction(String type, double amount) {
        transactions.add(new Transaction(type, amount, balance));
    }

    void showTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions available!");
            return;
        }
        System.out.println("\n----- Transaction History -----");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    @Override
    public void checkBalance() {
        System.out.println("Balance Amount: ₹" + balance);
    }
}

/* -------- Savings Account -------- */
class SavingsAccount extends Account {

    private static final double WITHDRAW_LIMIT = 10000;
    private static final double MIN_BALANCE = 500;
    private static final double YEARLY_INTEREST = 4.0; // 4% yearly

    SavingsAccount(int accountNumber, String password, double balance) {
        super(accountNumber, password, balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }
        balance += amount;
        addTransaction("Deposit", amount);
        System.out.println("Amount Deposited Successfully");
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return false;
        }

        if (amount > WITHDRAW_LIMIT) {
            System.out.println("Withdrawal limit exceeded (Max ₹" + WITHDRAW_LIMIT + ")");
            return false;
        }

        if (balance - amount < MIN_BALANCE) {
            System.out.println("Cannot withdraw! Minimum balance ₹" + MIN_BALANCE + " must be maintained.");
            return false;
        }

        if (amount <= balance) {
            balance -= amount;
            addTransaction("Withdraw", amount);
            System.out.println("Withdrawal Successful");
            return true;
        } else {
            System.out.println("Insufficient Balance");
            return false;
        }
    }

    void applyMonthlyInterest() {
        double monthlyRate = YEARLY_INTEREST / 12;
        double interest = (balance * monthlyRate) / 100;
        balance += interest;
        addTransaction("Interest Added", interest);
        System.out.println("Monthly Interest Applied: ₹" + interest);
    }
}

/* -------- Current Account -------- */
class CurrentAccount extends Account {

    CurrentAccount(int accountNumber, String password, double balance) {
        super(accountNumber, password, balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }
        balance += amount;
        addTransaction("Deposit", amount);
        System.out.println("Amount Deposited Successfully");
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return false;
        }

        if (amount <= balance) {
            balance -= amount;
            addTransaction("Withdraw", amount);
            System.out.println("Withdrawal Successful");
            return true;
        } else {
            System.out.println("Insufficient Balance");
            return false;
        }
    }
}

/* -------- Bank Utility Class -------- */
class Bank {

    static List<Account> accounts = new ArrayList<>();

    static Account findAccount(int accountNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber() == accountNumber)
                return acc;
        }
        return null;
    }

    static int generateCaptcha() {
        return 1000 + new Random().nextInt(9000);
    }
}

/* -------- Main Class -------- */
public class Main {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {

            while (true) {
                System.out.println("\n===== Welcome to Banking System =====");
                System.out.println("1. Existing User");
                System.out.println("2. New User");
                System.out.println("3. Exit");

                int choice = sc.nextInt();

                switch (choice) {
                    case 3 -> {
                        System.out.println("Thank you for using Banking System!");
                        return;
                    }
                    /* -------- New User -------- */
                    case 2 -> {
                        System.out.println("Enter Account Number:");
                        int accNo = sc.nextInt();

                        if (Bank.findAccount(accNo) != null) {
                            System.out.println("Account already exists!");
                            continue;
                        }

                        sc.nextLine();
                        System.out.println("Create Password:");
                        String password = sc.nextLine();

                        System.out.println("Enter Initial Amount:");
                        double amount = sc.nextDouble();

                        System.out.println("1. Current Account");
                        System.out.println("2. Savings Account");
                        int type = sc.nextInt();

                        if (type == 2)
                            Bank.accounts.add(new SavingsAccount(accNo, password, amount));
                        else
                            Bank.accounts.add(new CurrentAccount(accNo, password, amount));

                        System.out.println("Account Created Successfully!");
                    }

                    /* -------- Existing User -------- */
                    case 1 -> {

                        System.out.println("Enter Account Number:");
                        int accNo = sc.nextInt();

                        Account acc = Bank.findAccount(accNo);
                        if (acc == null) {
                            System.out.println("Account not found!");
                            continue;
                        }

                        if (acc.isLocked()) {
                            System.out.println("Account is locked!");
                            continue;
                        }

                        sc.nextLine();
                        System.out.println("Enter Password:");
                        String password = sc.nextLine();

                        if (!acc.passwordCheck(password)) {
                            continue;
                        }

                        int captcha = Bank.generateCaptcha();
                        System.out.println("Enter Captcha: " + captcha);
                        if (sc.nextInt() != captcha) {
                            System.out.println("Invalid Captcha!");
                            continue;
                        }

                        boolean loggedIn = true;
                        while (loggedIn) {
                            System.out.println("\n---- Banking Menu ----");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Check Balance");
                            System.out.println("4. Account Transfer");
                            System.out.println("5. Change Password");
                            System.out.println("6. Logout");
                            System.out.println("7. Transaction History");
                            System.out.println("8. Apply Monthly Interest (Savings)");

                            int op = sc.nextInt();

                            switch (op) {
                                case 1 -> {
                                    System.out.println("Enter Amount:");
                                    acc.deposit(sc.nextDouble());
                                }
                                case 2 -> {
                                    System.out.println("Enter Amount:");
                                    acc.withdraw(sc.nextDouble());
                                }
                                case 3 -> acc.checkBalance();
                                case 4 -> {
                                    System.out.println("Enter Target Account Number:");
                                    int targetNo = sc.nextInt();

                                    if (targetNo == acc.getAccountNumber()) {
                                        System.out.println("Cannot transfer to same account!");
                                        break;
                                    }

                                    Account target = Bank.findAccount(targetNo);

                                    if (target == null) {
                                        System.out.println("Target account not found!");
                                        break;
                                    }

                                    System.out.println("Enter Transfer Amount:");
                                    double amt = sc.nextDouble();

                                    if (acc.withdraw(amt)) {
                                        target.deposit(amt);

                                        acc.addTransaction("Transfer to " + targetNo, amt);
                                        target.addTransaction("Received from " + acc.getAccountNumber(), amt);

                                        System.out.println("Transfer Successful");
                                    }
                                }
                                case 5 -> {
                                    sc.nextLine();
                                    System.out.println("Enter New Password:");
                                    acc.passwordChange(sc.nextLine());
                                    System.out.println("Password Updated Successfully");
                                }
                                case 6 -> {
                                    loggedIn = false;
                                    System.out.println("Logged out successfully!");
                                }
                                case 7 -> acc.showTransactions();
                                case 8 -> {
                                    if (acc instanceof SavingsAccount savingsAcc) {
                                        savingsAcc.applyMonthlyInterest();
                                    } else {
                                        System.out.println("Interest only available for Savings Accounts!");
                                    }
                                }
                                default -> System.out.println("Invalid Option");
                            }
                        }
                    }
                    default -> System.out.println("Invalid choice!");
                }
            }
        }
    }
}
