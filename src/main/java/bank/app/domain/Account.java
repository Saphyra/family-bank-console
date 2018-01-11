package bank.app.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import bank.app.ui.console.uiservice.newaccountservice.NewAccountData;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String password;
    private Double privateBalance;
    private Double bankBalance;

    public Account() {
    }

    public Account(NewAccountData accountData) {
        this.name = accountData.getUsername();
        this.password = accountData.getPassword();
        this.privateBalance = accountData.getPrivateBalance();
        this.bankBalance = 0.0;
    }

    @OneToMany
    private List<Transaction> transactions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Double getPrivateBalance() {
        return privateBalance;
    }

    public void setPrivateBalance(Double privateBalance) {
        this.privateBalance = privateBalance;
    }

    public void addPrivateBalance(double money) {
        privateBalance = privateBalance + money;
    }

    public void deductPrivateBalance(double money) {
        privateBalance = privateBalance - money;
    }

    public Double getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(Double bankBalance) {
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
