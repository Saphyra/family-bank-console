package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.service.BankSession;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.requestservice.ConsoleRequestService;
import bank.app.ui.console.uiservice.transactionservice.ConsoleTransactionService;

public class AccountMenu extends AbstractMenu<String, String, String> {
    private static final String VIEW_ACCOUNT_DATA_OPTION = "1";
    private static final String VIEW_NEW_REQUESTS_OPTION = "2";
    private static final String NEW_REQUEST_OPTION = "3";
    private static final String VIEW_PENDING_REQUESTS_OPTION = "4";
    private static final String VIEW_REQUESTS_OPTION = "5";
    private static final String NEW_TRANSACTION_OPTION = "6";
    private static final String VIEW_TRANSACTIONS_OPTION = "7";

    private @Autowired BankSession session;
    private @Autowired ConsoleRequestService request;
    private @Autowired ConsoleTransactionService transaction;

    private Account account;

    @Override
    protected void setDisplayedMessages() {
        addMessage("Welcome, " + account.getName() + "!");
        setMenuHeader("What do you want to do?");
        addOption(Option.defaultLogoutOption());
        addOption(Option.optionFactory(VIEW_ACCOUNT_DATA_OPTION, "Wiew account data"));
        addOption(Option.optionFactory(VIEW_NEW_REQUESTS_OPTION, "View new requests"));
        addOption(Option.optionFactory(NEW_REQUEST_OPTION, "New requests"));
        addOption(Option.optionFactory(VIEW_PENDING_REQUESTS_OPTION, "View pending requests"));
        addOption(Option.optionFactory(VIEW_REQUESTS_OPTION, "Wiew all requests"));
        addOption(Option.optionFactory(NEW_TRANSACTION_OPTION, "New transactions"));
        addOption(Option.optionFactory(VIEW_TRANSACTIONS_OPTION, "Wiew transactions"));

        setMenuFooter("Choose an option:");
    }

    @Override
    protected void enterMenu(Option<String, String> selection) {
        switch (selection.getKey()) {
        case VIEW_ACCOUNT_DATA_OPTION:
            System.out.println(account.getAccountInfo());
            break;
        case VIEW_NEW_REQUESTS_OPTION:
            request.viewNewRequests();
            break;
        case NEW_REQUEST_OPTION:
            request.createNewRequest();
            break;
        case VIEW_PENDING_REQUESTS_OPTION:
            request.viewPendingRequests();
            break;
        case VIEW_REQUESTS_OPTION:
            request.viewAllRequests();
            break;
        case NEW_TRANSACTION_OPTION:
            transaction.newTransaction();
            break;
        case VIEW_TRANSACTIONS_OPTION:
            transaction.viewTransactions();
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

    @Override
    protected String convertInputToKey(String userInput) {
        return userInput;
    }
}
