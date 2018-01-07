package bank.app.service;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.domain.Bank;

public class BankSession {
    private @Autowired AccountService accountService;
    private int actualAccountId = 0;
    private Bank actualBank;

    public Account getActualAccount() {
        return accountService.findById(actualAccountId);
    }

    public void setActualAccount(Account actualAccount) {
        if (actualAccount == null) {
            this.actualAccountId = 0;
        } else {
            this.actualAccountId = actualAccount.getId();
        }
    }

    public boolean isInSession() {
        return actualAccountId != 0;
    }

    public Bank getActualBank() {
        return actualBank;
    }

    public void setActualBank(Bank actualBank) {
        this.actualBank = actualBank;
    }
}
