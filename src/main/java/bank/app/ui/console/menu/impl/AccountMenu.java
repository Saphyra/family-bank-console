package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.BankSession;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;

public class AccountMenu extends AbstractMenu {
    private @Autowired BankSession session;
    private Account account;

    @Override
    protected void setDisplayedMessages() {
        addMessage("Welcome, " + account.getName() + "!");
        setMenuHeader("What do you want to do?");
        addOption(Option.defaultLogoutOption());
        addOption(Option.optionFactory("1", "Wiew requests"));
        addOption(Option.optionFactory("2", "New requests"));
        addOption(Option.optionFactory("3", "Wiew transactions"));
        addOption(Option.optionFactory("4", "New transactions"));
        addOption(Option.optionFactory("5", "Wiew account data"));
        setMenuFooter("Choose an option:");
    }

    @Override
    protected void enterMenu(Option<String, String> selection) {
        // TODO Account menu points
    }

    @Override
    protected void beforeStart() {
        if (!session.isInSession()) {
            throw new IllegalStateException("No account logged in.");
        }
        account = session.getActualAccount();
    }

    @Override
    protected void afterExit() {
        session.setActualAccount(null);
        account = null;
    }

}
