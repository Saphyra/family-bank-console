package bank.app.ui.console.uiservice;

import javax.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.BankSession;
import bank.app.service.LoginService;
import bank.app.ui.console.ConsoleReader;

public class ConsoleLoginService {
    private @Autowired ConsoleReader input;
    private @Autowired LoginService loginService;
    private @Autowired BankSession session;

    public void login() {
        Account account = getAccount();
        session.setActualAccount(account);
        if (session.isInSession()) {
            System.err.println("Logged in to: " + account);
        }
    }

    private Account getAccount() {
        Account result = null;
        System.out.println("Logging in... Send empty string to cancel process.");
        String username = input.getUserInput("Username:");
        String password = "";
        if (!username.isEmpty()) {
            password = input.getUserInput("Password:");
            if (!password.isEmpty()) {
                try {
                    result = loginService.getAccount(username, password);
                } catch (NoResultException e) {
                    System.err.println("No registrated user with the given username and password.");
                    result = getAccount();
                }
            } else {
                System.out.println("Login process cancelled.");
            }
        } else {
            System.out.println("Login process cancelled.");
        }

        return result;
    }
}
