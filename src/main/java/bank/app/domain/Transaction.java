package bank.app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import bank.app.util.DateFormat;

@Entity
public class Transaction {

    @Id
    @GeneratedValue
    private int id;
    private String fromName;
    private String addresseeName;
    private int money;
    private @Enumerated(EnumType.STRING) TransactionType type;
    private @Temporal(TemporalType.TIMESTAMP) Date date;

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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public TransactionType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTransactionInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("From: ").append(fromName)//
                .append(" - To: ").append(addresseeName)//
                .append(" - Money: ").append(money)//
                .append(" - Type: ").append(type)//
                .append(" - Time: ").append(DateFormat.formatDate(date));
        return builder.toString();
    }
}
