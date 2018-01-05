package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Menu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.ConsoleLoginService;
import bank.app.ui.console.uiservice.ConsoleNewMemberService;

public class MainMenu extends AbstractMenu<String, String, String> {
    private static final String NEW_MEMBER_OPTION = "1";
    private static final String LOGIN_OPTION = "2";
    private @Autowired ConsoleNewMemberService newMemberCreator;
    private @Autowired ConsoleLoginService login;
    private @Autowired Menu<String, String> accountMenu;

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
    protected void enterMenu(Option<String, String> selectedOption) {
        switch (selectedOption.getKey()) {
        case NEW_MEMBER_OPTION:
            newMemberCreator.createNewMember();
            break;
        case LOGIN_OPTION:
            login.login();
            try {
                accountMenu.interactUser(Option.defaultLogoutOption());
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
            }
            break;
        }
    }

    @Override
    protected String convertInputToKey(String userInput) {
        return userInput;
    }
}
