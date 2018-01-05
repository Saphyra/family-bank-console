package bank.app.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Request {
    private static final String DEFAULT_MESSAGE = "No message";
    @Id
    @GeneratedValue
    private int id;
    private int fromId;
    private int toId;
    private double money;
    private @Enumerated(EnumType.STRING) RequestStatus status;
    private String requestMessage = DEFAULT_MESSAGE;
    private String answerMessage = DEFAULT_MESSAGE;
    private @Temporal(TemporalType.DATE) Date sendDate;
    private @Temporal(TemporalType.DATE) Date acceptDate;

    public Request() {

    }

    public Request(int fromId, int toId, double money) {
        this.fromId = fromId;
        this.toId = toId;
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

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
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

    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }
}
