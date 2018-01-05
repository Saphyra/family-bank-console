package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.BankSession;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.ConsoleRequestService;

public class AccountMenu extends AbstractMenu<String, String, String> {
    private static final String VIEW_ACCOUNT_DATA_OPTION = "5";
    private static final String NEW_TRANSACTION_OPTION = "4";
    private static final String VIEW_TRANSACTIONS_OPTION = "3";
    private static final String NEW_REQUEST_OPTION = "2";
    private static final String VIEW_REQUEST_OPTION = "1";
    private @Autowired BankSession session;
    private @Autowired ConsoleRequestService request;
    private Account account;

    @Override
    protected void setDisplayedMessages() {
        addMessage("Welcome, " + account.getName() + "!");
        setMenuHeader("What do you want to do?");
        addOption(Option.defaultLogoutOption());
        addOption(Option.optionFactory(VIEW_REQUEST_OPTION, "Wiew requests"));
        addOption(Option.optionFactory(NEW_REQUEST_OPTION, "New requests"));
        addOption(Option.optionFactory(VIEW_TRANSACTIONS_OPTION, "Wiew transactions"));
        addOption(Option.optionFactory(NEW_TRANSACTION_OPTION, "New transactions"));
        addOption(Option.optionFactory(VIEW_ACCOUNT_DATA_OPTION, "Wiew account data"));
        setMenuFooter("Choose an option:");
    }

    @Override
    protected void enterMenu(Option<String, String> selection) {
        switch (selection.getKey()) {
        case NEW_REQUEST_OPTION:
            request.createNewRequest();
            break;
        case VIEW_ACCOUNT_DATA_OPTION:
            printAccountData();
            break;
        }
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

    private void printAccountData() {
        StringBuilder builder = new StringBuilder();
        builder.append("Account name: ").append(account.getName()).append(System.lineSeparator())//
                .append("Private balance: ").append(account.getPrivateBalance()).append(System.lineSeparator())//
                .append("Bank balance: ").append(account.getBankBalance());
        System.out.println(builder.toString());
    }

    @Override
    protected String convertInputToKey(String userInput) {
        return userInput;
    }
}
