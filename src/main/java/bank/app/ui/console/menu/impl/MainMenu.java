package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Menu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.loginservice.ConsoleLoginService;
import bank.app.ui.console.uiservice.newaccountservice.ConsoleNewAccountService;

public class MainMenu extends AbstractMenu<String, Integer, String> {
    private static final Option<Integer, String> exitOption = Option.defaultExitOption();
    private static final int NEW_MEMBER_OPTION = 1;
    private static final int LOGIN_OPTION = 2;
    private @Autowired ConsoleNewAccountService newAccountService;
    private @Autowired ConsoleLoginService login;
    private @Autowired Menu<Integer, String> accountMenu;

    public MainMenu() {
        super();
    }

    public MainMenu(ConsoleReader input) {
        super(input);
    }

    @Override
    protected void setDisplayedMessages() {
        addMessage("Welcome to family bank!");
        addOption(Option.defaultExitOption());
        addOption(Option.optionFactory(NEW_MEMBER_OPTION, "New account"));
        addOption(Option.optionFactory(LOGIN_OPTION, "Login"));
    }

    @Override
    protected void enterMenu(Option<Integer, String> selectedOption) {
        switch (selectedOption.getKey()) {
        case NEW_MEMBER_OPTION:
            newAccountService.createNewAccount();
            break;
        case LOGIN_OPTION:
            login.login();
            try {
                accountMenu.interactUser();
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
            break;
        }
    }

    @Override
    protected Integer convertInputToKey(String userInput) {
        return Integer.valueOf(userInput);
    }

    @Override
    public void interactUser() {
        interactUser(exitOption);
    }
}
