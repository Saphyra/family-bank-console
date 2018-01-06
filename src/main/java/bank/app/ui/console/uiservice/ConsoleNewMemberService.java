package bank.app.ui.console.uiservice;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.NewAccountService;

public class ConsoleNewMemberService extends AbstractConsoleService {
    private @Autowired NewAccountService service;

    // TODO refactor: extract methods
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
        } else if (newAccount.getPassword().isEmpty()) {
            System.err.println("Invalid password.");
            result = false;
        } else if (service.isUsernameRegistered(newAccount)) {
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
