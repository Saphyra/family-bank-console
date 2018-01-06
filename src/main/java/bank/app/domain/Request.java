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
public class Request {
    private static final String DEFAULT_MESSAGE = "No message";
    @Id
    @GeneratedValue
    private int id;
    private String fromName;
    private String addresseeName;
    private double money;
    private @Enumerated(EnumType.STRING) RequestStatus status;
    private String requestMessage = DEFAULT_MESSAGE;
    private String answerMessage = DEFAULT_MESSAGE;
    private @Temporal(TemporalType.TIMESTAMP) Date sendDate;
    private @Temporal(TemporalType.TIMESTAMP) Date answerDate;

    public Request() {

    }

    public Request(String from, String addressee, double money) {
        this.fromName = from;
        this.addresseeName = addressee;
        this.money = money;
        status = RequestStatus.SENT;
        sendDate = new Date();
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

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        if (requestMessage.isEmpty()) {
            this.requestMessage = DEFAULT_MESSAGE;
        } else {
            this.requestMessage = requestMessage;
        }
    }

    public String getAnswerMessage() {
        return answerMessage;
    }

    public void setAnswerMessage(String answerMessage) {
        if (answerMessage.isEmpty()) {
            this.answerMessage = DEFAULT_MESSAGE;
        } else {
            this.answerMessage = answerMessage;
        }
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getAnswerDate() {
        return answerDate;
    }

    public void setAnswerDate(Date acceptDate) {
        this.answerDate = acceptDate;
    }

    public String getRequestInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("From: ").append(fromName)//
                .append(" - To: ").append(addresseeName)//
                .append(" - Money: ").append(money)//
                .append(" - Request message: ").append(requestMessage)//
                .append(" - Sent: ").append(DateFormat.formatDate(sendDate))//
                .append(" - Status: ").append(status);
        if (status != RequestStatus.SENT && status != RequestStatus.PENDING && status != RequestStatus.CANCELLED) {
            builder.append(" - Answer message: ").append(answerMessage)//
                    .append(" - Answered: ").append(DateFormat.formatDate(answerDate));
        }
        return builder.toString();
    }
}
