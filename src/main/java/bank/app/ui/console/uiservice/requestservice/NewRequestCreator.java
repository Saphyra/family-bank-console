package bank.app.ui.console.uiservice.requestservice;

import java.util.List;

import bank.app.domain.Account;
import bank.app.domain.Bank;
import bank.app.domain.Request;
import bank.app.service.AccountService;
import bank.app.service.BankSession;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.uiservice.AccountGetterMenu;

public class NewRequestCreator {
    private final ConsoleReader input;
    private final AccountService accountService;
    private final Account account;
    private final Bank bank;
    private Request newRequest;

    public NewRequestCreator(ConsoleReader input, AccountService accountService, BankSession session) {
        this.input = input;
        this.accountService = accountService;
        this.account = session.getActualAccount();
        this.bank = session.getActualBank();
    }

    public Request createRequest() {
        NewRequestData requestData = new NewRequestData(account.getName());
        setewRequestData(requestData);

        createNewRequest(requestData);

        return newRequest;
    }

    private void setewRequestData(NewRequestData requestData) {
        setAdresseeId(requestData);
        setMoney(requestData);
        setMessage(requestData);
    }

    private void setAdresseeId(NewRequestData requestData) {
        int addresseeId = getAddresseeId(account.getId());
        if (addresseeId != AccountGetterMenu.EXIT_OPTION) {
            requestData.setAddresseeName(getAddresseeName(addresseeId));
        }
    }

    private int getAddresseeId(int accountId) {
        List<Account> addressees = accountService.getAddressees(accountId);
        AccountGetterMenu accountGetterMenu = new AccountGetterMenu(input, addressees, AccountGetterMenu.BANK_INCLUDED);
        return accountGetterMenu.getSelectedId();
    }

    private String getAddresseeName(int addresseeId) {
        String result = null;
        if (addresseeId == AccountGetterMenu.BANK_OPTION) {
            result = bank.getName();
        } else {
            Account addressee = accountService.getAccount(addresseeId);
            result = addressee.getName();
        }
        return result;
    }

    private void setMoney(NewRequestData requestData) {
        if (!requestData.getAddresseeName().isEmpty()) {
            double money = getMoney();
            requestData.setMoney(money);
        }
    }

    private double getMoney() {
        double money;
        String userInput = input.getUserInput("How much money do you want to request? (Enter 0 if you want to cancel the request.");
        money = parseInput(userInput);
        money = validateInput(money);

        return money;
    }

    private double parseInput(String userInput) {
        double money;
        try {
            money = Double.valueOf(userInput);
        } catch (NumberFormatException e) {
            System.err.println("Type a number!");
            money = getMoney();
        }
        return money;
    }

    private double validateInput(double money) {
        try {
            if (money < 0) {
                throw new IllegalArgumentException("Type a number higher than 0 or 0 to cancell request!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            money = getMoney();
        }
        return money;
    }

    private void setMessage(NewRequestData requestData) {
        if (requestData.getMoney() > 0.0) {
            requestData.setMessage(getRequestMessage());
        }
    }

    private String getRequestMessage() {
        return input.getUserInput("Type the message of the request or empty string to cancel request!");
    }

    private void createNewRequest(NewRequestData requestData) {
        if (!requestData.getMessage().isEmpty()) {
            newRequest = new Request(requestData);
        }
    }
}
