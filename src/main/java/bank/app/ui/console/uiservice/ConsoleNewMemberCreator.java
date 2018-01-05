package bank.app.ui.console.uiservice;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.NewMemberService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.NewMemberCreator;

public class ConsoleNewMemberCreator implements NewMemberCreator {
    private @Autowired ConsoleReader input;
    private @Autowired NewMemberService service;

    @Override
    public void createNewMember() {
        Account newAccount = new Account();
        newAccount.setName(input.getUserInput("Username:"));
        newAccount.setPassword(input.getUserInput("Password:"));
        newAccount.setPrivateBalance(getPrivateBalance());

        if (isAccountValid(newAccount)) {
            service.save(newAccount);
            System.err.println("Account registered.");
        } else {
            System.err.println("Account registration failed.");
        }
    }

    private boolean isAccountValid(Account newAccount) {
        boolean result = true;
        if (newAccount.getName().isEmpty()) {
            System.err.println("Invalid account name.");
            result = false;
        }
        if (newAccount.getPassword().isEmpty()) {
            System.err.println("Invalid password.");
            result = false;
        }
        if (service.isUsernameRegistered(newAccount)) {
            System.err.println("Account is already registered with the given name");
            result = false;
        }
        return result;
    }

    private double getPrivateBalance() {
        double result = 0;
        String userInput = input.getUserInput("Staring balance:");
        try {
            result = Double.parseDouble(userInput);
        } catch (NumberFormatException e) {
            System.err.println("Invalid parameter. Please type a number!");
            result = getPrivateBalance();
        }
        return result;
    }
}
