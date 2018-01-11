package bank.app.service;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.domain.Bank;

public class BankSession {
    private @Autowired AccountService accountService;
    private @Autowired BankService bankService;
    private int actualAccountId = 0;
    private String actualBankName;

    public Account getActualAccount() {
        return accountService.getAccount(actualAccountId);
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
        return bankService.getBank(actualBankName);
    }

    public void setActualBank(Bank actualBank) {
        this.actualBankName = actualBank.getName();
    }

    public String getActualBankName() {
        return actualBankName;
    }
}
