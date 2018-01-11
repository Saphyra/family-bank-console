package bank.app.ui.console.uiservice.loginservice;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.AccountService;
import bank.app.service.BankSession;
import bank.app.ui.console.uiservice.AbstractConsoleService;

public class ConsoleLoginService extends AbstractConsoleService {
    private @Autowired AccountService loginService;
    private @Autowired BankSession session;

    public void login() {
        System.out.println("Logging in...");
        Account account = getAccount();
        session.setActualAccount(account);
        if (session.isInSession()) {
            System.err.println("Logged in to: " + account);
        }
    }

    private Account getAccount() {
        String username = getUsername();
        String password = getPassword(username);

        return getAccount(username, password);
    }

    private String getUsername() {
        return input.getUserInput("Username: (Empty string to cancel)");
    }

    private String getPassword(String username) {
        String password = "";

        if (!username.isEmpty()) {
            password = input.getUserInput("Password: (Empty string to cancel)");
        } else {
            printCancelMessage();
        }
        return password;
    }

    private Account getAccount(String username, String password) {
        Account account = null;
        if (!username.isEmpty() && !password.isEmpty()) {
            try {
                account = loginService.getAccount(username, password);
            } catch (NoSuchElementException e) {
                e.getMessage();
                account = getAccount();
            }
        } else {
            printCancelMessage();
        }
        return account;
    }

    private void printCancelMessage() {
        System.out.println("Login process cancelled.");
    }
}
