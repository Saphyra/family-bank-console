package bank.app.service;

import bank.app.domain.Account;
import bank.app.domain.Bank;

public class BankSession {
    private Account actualAccount;
    private Bank actualBank;

    public Account getActualAccount() {
        return actualAccount;
    }

    public void setActualAccount(Account actualAccount) {
        this.actualAccount = actualAccount;
    }

    public boolean isInSession() {
        return actualAccount != null;
    }

    public Bank getActualBank() {
        return actualBank;
    }

    public void setActualBank(Bank actualBank) {
        this.actualBank = actualBank;
    }
}
