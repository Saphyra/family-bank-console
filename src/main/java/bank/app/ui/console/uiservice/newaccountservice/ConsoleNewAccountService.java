package bank.app.ui.console.uiservice.newaccountservice;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.NewAccountService;
import bank.app.ui.console.uiservice.AbstractConsoleService;

public class ConsoleNewAccountService extends AbstractConsoleService {
    private @Autowired NewAccountService service;

    public void createNewAccount() {
        String username = input.getUserInput("Username: (Empty string to cancel)");
        if (!username.isEmpty()) {
            String password = input.getUserInput("Password: (Empty string to cancel)");
            if (!password.isEmpty()) {
                double privateBalance = getPrivateBalance();
                if (privateBalance >= 0) {
                    Account newAccount = new Account();
                    newAccount.setName(username);
                    newAccount.setPassword(password);
                    newAccount.setPrivateBalance(privateBalance);
                    registrateNewAccount(newAccount);
                }
            }
        }
    }

    private void registrateNewAccount(Account newAccount) {
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
        String userInput = input.getUserInput("Staring balance: (Enter -1 to cancel)");
        try {
            result = Double.parseDouble(userInput);
            if (result < 0 && result != -1.0) {
                throw new IllegalArgumentException("Starting balance cannot be negative.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid parameter. Please type a number!");
            result = getPrivateBalance();
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            result = getPrivateBalance();
        }
        return result;
    }
}
