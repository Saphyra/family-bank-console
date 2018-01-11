package bank.app.ui.console.uiservice.newaccountservice;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.NewAccountService;
import bank.app.ui.console.uiservice.AbstractConsoleService;

public class ConsoleNewAccountService extends AbstractConsoleService {
    private @Autowired NewAccountService service;

    public void createNewAccount() {
        NewAccountData accountData = new NewAccountData();
        setNewAccountData(accountData);
        Account newAccount = new Account(accountData);
        if (isAccountValid(newAccount)) {
            registrateNewAccount(newAccount);
        }
    }

    private void setNewAccountData(NewAccountData accountData) {
        setUsername(accountData);
        setPassword(accountData);
        setPrivateBalance(accountData);
    }

    private void setUsername(NewAccountData data) {
        String username = input.getUserInput("Username: (Empty string to cancel)");
        data.setUsername(username);
    }

    private void setPassword(NewAccountData data) {
        if (!data.getUsername().isEmpty()) {
            String password = input.getUserInput("Password: (Empty string to cancel)");
            data.setPassword(password);
        }
    }

    private void setPrivateBalance(NewAccountData data) {
        if (!data.getPassword().isEmpty()) {
            double privateBalance = getStartBalance(data);
            data.setPrivateBalance(privateBalance);
        }
    }

    private double getStartBalance(NewAccountData data) {
        String userInput = input.getUserInput("Staring balance: (Enter -1 to cancel)");
        double privateBalance = convertInput(data, userInput);
        try {
            validateInput(privateBalance);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            getStartBalance(data);
        }
        return privateBalance;
    }

    private double convertInput(NewAccountData data, String userInput) {
        double privateBalance = 0;
        try {
            privateBalance = Double.parseDouble(userInput);
        } catch (NumberFormatException e) {
            System.err.println("Invalid input. Please type a number!");
            getStartBalance(data);
        }
        return privateBalance;
    }

    private void validateInput(double privateBalance) {
        if (privateBalance < 0 && privateBalance != -1.0) {
            throw new IllegalArgumentException("Starting balance cannot be negative.");
        }
    }

    private boolean isAccountValid(Account newAccount) {
        boolean result = true;
        if (newAccount.getName().isEmpty()) {
            printLoginCancelledMessage();
            result = false;
        } else if (newAccount.getPassword().isEmpty()) {
            printLoginCancelledMessage();
            result = false;
        } else if (service.isUsernameRegistered(newAccount)) {
            System.err.println("Account is already registered with the given name");
            result = false;
        }
        return result;
    }

    private void printLoginCancelledMessage() {
        System.err.println("Login process cancelled.");
    }

    private void registrateNewAccount(Account newAccount) {
        if (isAccountValid(newAccount)) {
            service.save(newAccount);
            System.err.println("Account registered.");
        } else {
            System.err.println("Account registration failed.");
        }
    }
}
