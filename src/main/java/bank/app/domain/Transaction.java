package bank.app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import bank.app.util.DateFormat;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private int id;
    private String fromName;
    private String addresseeName;
    private double money;
    private @Enumerated(EnumType.STRING) TransactionType type;
    private long date;

    public static Transaction depositTransaction(String fromName, double money) {
        Transaction transaction = new Transaction();
        transaction.setFromName(fromName);
        transaction.setAddresseeName("Bank");
        transaction.setMoney(money);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setDate(new Date());
        return transaction;
    }

    public static Transaction withdrawTransaction(String addresseeName, double money) {
        Transaction transaction = new Transaction();
        transaction.setFromName(Bank.DEFAULT_BANK_NAME);
        transaction.setAddresseeName(addresseeName);
        transaction.setMoney(money);
        transaction.setType(TransactionType.WITHDRAW);
        transaction.setDate(new Date());
        return transaction;
    }

    public static Transaction earnTransaction(String addresseeName, double money) {
        Transaction transaction = new Transaction();
        transaction.setFromName("Unknown");
        transaction.setAddresseeName(addresseeName);
        transaction.setMoney(money);
        transaction.setType(TransactionType.EARN);
        transaction.setDate(new Date());
        return transaction;
    }

    public static Transaction spendTransaction(String fromName, double money) {
        Transaction transaction = new Transaction();
        transaction.setFromName(fromName);
        transaction.setAddresseeName("Unknown");
        transaction.setMoney(money);
        transaction.setType(TransactionType.SPEND);
        transaction.setDate(new Date());
        return transaction;
    }

    public static Transaction sendTransaction(String fromName, String addresseeName, double money) {
        Transaction transaction = new Transaction();
        transaction.setFromName(fromName);
        transaction.setAddresseeName(addresseeName);
        transaction.setMoney(money);
        transaction.setType(TransactionType.SEND);
        transaction.setDate(new Date());
        return transaction;
    }

    public static Transaction requestTransaction(Request request) {
        Transaction transaction = new Transaction();
        transaction.setFromName(request.getAddresseeName());
        transaction.setAddresseeName(request.getFromName());
        transaction.setMoney(request.getMoney());
        transaction.setType(TransactionType.REQUEST);
        transaction.setDate(new Date());
        return transaction;
    }

    public static Transaction negativeInterestTransaction(String accountName, double money) {
        Transaction transaction = new Transaction();
        transaction.setAddresseeName(Bank.DEFAULT_BANK_NAME);
        transaction.setFromName(accountName);
        transaction.setMoney(money);
        transaction.setType(TransactionType.INTEREST);
        transaction.setDate(new Date());
        return transaction;
    }

    public static Transaction positiveInterestTransaction(String accountName, double money) {
        Transaction transaction = new Transaction();
        transaction.setFromName(Bank.DEFAULT_BANK_NAME);
        transaction.setAddresseeName(accountName);
        transaction.setMoney(money);
        transaction.setType(TransactionType.INTEREST);
        transaction.setDate(new Date());
        return transaction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getAddresseeName() {
        return addresseeName;
    }

    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public TransactionType getType() {
        return type;
    }

    public Date getDate() {
        return new Date(date);
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date.getTime();
    }

    public String getTransactionInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("From: ").append(fromName)//
                .append(" - To: ").append(addresseeName)//
                .append(" - Money: ").append(money)//
                .append(" - Type: ").append(type)//
                .append(" - Time: ").append(DateFormat.formatDate(getDate()));
        return builder.toString();
    }
}
