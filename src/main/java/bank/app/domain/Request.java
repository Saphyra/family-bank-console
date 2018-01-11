package bank.app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import bank.app.ui.console.uiservice.requestservice.NewRequestData;
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
    private long sendDate;
    private long answerDate;

    public Request() {
    }

    public Request(String from, String addressee, double money, String requestMessage) {
        this.fromName = from;
        this.addresseeName = addressee;
        this.money = money;
        this.requestMessage = requestMessage;
        status = RequestStatus.SENT;
    }

    public Request(NewRequestData requestData) {
        this(requestData.getFromName(), requestData.getAddresseeName(), requestData.getMoney(), requestData.getMessage());
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
        return new Date(sendDate);
    }

    public void setSendDate(Date sendDate) {
        setSendDate(sendDate.getTime());
    }

    public void setSendDate(long timestamp) {
        this.sendDate = timestamp;
    }

    public Date getAnswerDate() {
        return new Date(answerDate);
    }

    public void setAnswerDate(Date acceptDate) {
        setAnswerDate(acceptDate.getTime());
    }

    public void setAnswerDate(long timestamp) {
        this.answerDate = timestamp;
    }

    public String getRequestInfo() {
        StringBuilder builder = new StringBuilder();
        builder.append("From: ").append(fromName)//
                .append(" - To: ").append(addresseeName)//
                .append(" - Money: ").append(money)//
                .append(" - Request message: ").append(requestMessage)//
                .append(" - Sent: ").append(DateFormat.formatDate(getSendDate()))//
                .append(" - Status: ").append(status);
        if (status != RequestStatus.SENT && status != RequestStatus.PENDING && status != RequestStatus.CANCELLED) {
            builder.append(" - Answer message: ").append(answerMessage)//
                    .append(" - Answered: ").append(DateFormat.formatDate(getAnswerDate()));
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        if (id == 0) {
            return "Cancel";
        }
        return getRequestInfo();
    }
}
