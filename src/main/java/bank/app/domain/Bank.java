package bank.app.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Bank {
    public static final String DEFAULT_BANK_NAME = "Bank";
    public static final double INTEREST_RATE = 0.002;
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private double balance;

    @OneToMany
    private List<Transaction> transactions;

    public static Bank defaultBank() {
        Bank bank = new Bank();
        bank.setName(DEFAULT_BANK_NAME);
        bank.setBalance(0);
        return bank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void addBalance(double money) {
        this.balance = this.balance + money;
    }

    public void deductBalance(double money) {
        this.balance = this.balance - money;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getBankInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Bank name: ").append(name).append(System.lineSeparator())//
                .append("Available balance: ").append(balance);
        return builder.toString();
    }
}
