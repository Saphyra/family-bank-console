package bank.app.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private int id;
    private String name;
    private String password;
    private double privateBalance;
    private double bankBalance;

    @OneToMany
    private List<Transaction> transactions;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getPrivateBalance() {
        return privateBalance;
    }

    public void setPrivateBalance(double privateBalance) {
        this.privateBalance = privateBalance;
    }

    public void addPrivateBalance(double money) {
        privateBalance = privateBalance + money;
    }

    public void deductPrivateBalance(double money) {
        privateBalance = privateBalance - money;
    }

    public double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(double bankBalance) {
        this.bankBalance = bankBalance;
    }

    public void addBankBalance(double money) {
        bankBalance = bankBalance + money;
    }

    public void deductBankBalance(double money) {
        bankBalance = bankBalance - money;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "Id: " + id + " - Name: " + name + " - Private Balance: " + privateBalance + " - Bank Balance: " + bankBalance;
    }

    public String getAccountInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("Account name: ").append(name).append(System.lineSeparator())//
                .append("Private balance: ").append(privateBalance).append(System.lineSeparator())//
                .append("Bank balance: ").append(bankBalance);
        return builder.toString();
    }
}
