package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.loginservice.ConsoleLoginService;
import bank.app.ui.console.uiservice.newaccountservice.ConsoleNewAccountService;

public class MainMenu extends AbstractMenu<String, Integer, String> {
    private static final Option<Integer, String> EXIT_OPTION = Option.defaultExitOption();
    private static final int NEW_MEMBER_OPTION_KEY = 1;
    private static final Option<Integer, String> NEW_MEMBER_OPTION = Option.optionFactory(NEW_MEMBER_OPTION_KEY, "New account");
    private static final int LOGIN_OPTION_KEY = 2;
    private static final Option<Integer, String> LOGIN_OPTION = Option.optionFactory(LOGIN_OPTION_KEY, "Login");
    private static final int BANK_ADMINISTRATION_OPTION_KEY = 3;
    private static final Option<Integer, String> BANK_ADMINISTRATION_OPTION = Option.optionFactory(BANK_ADMINISTRATION_OPTION_KEY, "Bank administration");

    private @Autowired ConsoleNewAccountService newAccountService;
    private @Autowired ConsoleLoginService login;
    private @Autowired AccountMenu accountMenu;
    private @Autowired BankMenu bankMenu;

    public MainMenu() {
        super();
    }

    public MainMenu(ConsoleReader input) {
        super(input);
    }

    @Override
    protected void initMenu() {
        addMessage("Welcome to family bank!");
        addOption(EXIT_OPTION);
        addOption(NEW_MEMBER_OPTION);
        addOption(LOGIN_OPTION);
        addOption(BANK_ADMINISTRATION_OPTION);
    }

    @Override
    protected void enterMenu(Option<Integer, String> selectedOption) {
        switch (selectedOption.getKey()) {
        case NEW_MEMBER_OPTION_KEY:
            newAccountService.createNewAccount();
            break;
        case LOGIN_OPTION_KEY:
            login.login();
            enterAccountMenu();
            break;
        case BANK_ADMINISTRATION_OPTION_KEY:
            bankMenu.interactUser();
            break;
        }
    }

    private void enterAccountMenu() {
        try {
            accountMenu.interactUser();
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected Integer convertInputToKey(String userInput) {
        return Integer.valueOf(userInput);
    }

    @Override
    public void interactUser() {
        interactUser(EXIT_OPTION);
    }
}
