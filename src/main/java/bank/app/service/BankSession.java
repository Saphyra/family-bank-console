package bank.app.service;

import bank.app.domain.Account;

public class BankSession {
    private Account actualAccount;

    public Account getActualAccount() {
        return actualAccount;
    }

    public void setActualAccount(Account actualAccount) {
        this.actualAccount = actualAccount;
    }

    public boolean isInSession() {
        return actualAccount != null;
    }
}
