package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.NewMemberCreator;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Menu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.ConsoleLoginService;

public class MainMenu extends AbstractMenu {
    private static final String NEW_MEMBER_OPTION = "1";
    private static final String LOGIN_OPTION = "2";
    private @Autowired NewMemberCreator newMemberCreator;
    private @Autowired ConsoleLoginService login;
    private @Autowired Menu accountMenu;

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
}
