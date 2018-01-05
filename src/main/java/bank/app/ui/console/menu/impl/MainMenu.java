package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.NewMemberCreator;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;

public class MainMenu extends AbstractMenu {
    private static final String NEW_MEMBER_OPTION = "1";
    private @Autowired NewMemberCreator newMemberCreator;

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
    }

    @Override
    protected void enterMenu(Option<String, String> selectedOption) {
        switch (selectedOption.getKey()) {
        case NEW_MEMBER_OPTION:
            newMemberCreator.createNewMember();
            break;
        }
    }
}
