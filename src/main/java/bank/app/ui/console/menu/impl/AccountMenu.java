package bank.app.ui.console.menu.impl;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.service.BankSession;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.requestservice.ConsoleRequestService;
import bank.app.ui.console.uiservice.transactionservice.ConsoleTransactionService;

public class AccountMenu extends AbstractMenu<String, Integer, String> {
    private static final Option<Integer, String> EXIT_OPTION = Option.defaultLogoutOption();
    private static final int VIEW_ACCOUNT_DATA_OPTION_KEY = 1;
    private static final Option<Integer, String> VIEW_ACCOUNT_DATA_OPTION = Option.optionFactory(VIEW_ACCOUNT_DATA_OPTION_KEY, "Wiew account data");
    private static final int VIEW_NEW_REQUESTS_OPTION_KEY = 2;
    private static final Option<Integer, String> VIEW_NEW_REQUESTS_OPTION = Option.optionFactory(VIEW_NEW_REQUESTS_OPTION_KEY, "View new requests");
    private static final int NEW_REQUEST_OPTION_KEY = 3;
    private static final Option<Integer, String> NEW_REQUEST_OPTION = Option.optionFactory(NEW_REQUEST_OPTION_KEY, "Create new request");
    private static final int VIEW_PENDING_REQUESTS_OPTION_KEY = 4;
    private static final Option<Integer, String> VIEW_PENDING_REQUESTS_OPTION = Option.optionFactory(VIEW_PENDING_REQUESTS_OPTION_KEY, "View pending requests");
    private static final int VIEW_REQUESTS_OPTION_KEY = 5;
    private static final Option<Integer, String> VIEW_REQUESTS_OPTION = Option.optionFactory(VIEW_REQUESTS_OPTION_KEY, "Wiew all requests");
    private static final int NEW_TRANSACTION_OPTION_KEY = 6;
    private static final Option<Integer, String> NEW_TRANSACTION_OPTION = Option.optionFactory(NEW_TRANSACTION_OPTION_KEY, "New transaction");
    private static final int VIEW_TRANSACTIONS_OPTION_KEY = 7;
    private static final Option<Integer, String> VIEW_TRANSACTIONS_OPTION = Option.optionFactory(VIEW_TRANSACTIONS_OPTION_KEY, "Wiew transactions");

    private @Autowired BankSession session;
    private @Autowired ConsoleRequestService request;
    private @Autowired ConsoleTransactionService transaction;

    @Override
    protected void initMenu() {
        clearMessages();
        addMessage("Welcome, " + session.getActualAccount().getName() + "!");
        setMenuHeader("What do you want to do?");
        addOption(EXIT_OPTION);
        addOption(VIEW_ACCOUNT_DATA_OPTION);
        addOption(VIEW_NEW_REQUESTS_OPTION);
        addOption(NEW_REQUEST_OPTION);
        addOption(VIEW_PENDING_REQUESTS_OPTION);
        addOption(VIEW_REQUESTS_OPTION);
        addOption(NEW_TRANSACTION_OPTION);
        addOption(VIEW_TRANSACTIONS_OPTION);

        setMenuFooter("Choose an option:");
    }

    public AccountMenu(ConsoleReader input) {
        super(input);
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
        switch (selection.getKey()) {
        case VIEW_ACCOUNT_DATA_OPTION_KEY:
            System.out.println(session.getActualAccount().getAccountInfo());
            break;
        case VIEW_NEW_REQUESTS_OPTION_KEY:
            request.viewNewRequests();
            break;
        case NEW_REQUEST_OPTION_KEY:
            request.createNewRequest();
            break;
        case VIEW_PENDING_REQUESTS_OPTION_KEY:
            request.viewPendingRequests();
            break;
        case VIEW_REQUESTS_OPTION_KEY:
            request.viewAllRequests();
            break;
        case NEW_TRANSACTION_OPTION_KEY:
            transaction.newTransaction();
            break;
        case VIEW_TRANSACTIONS_OPTION_KEY:
            transaction.viewTransactions();
            break;
        }
    }

    @Override
    protected void beforeStart() {
        if (!session.isInSession()) {
            throw new IllegalStateException("No account logged in.");
        }
    }

    @Override
    protected void afterExit() {
        session.setActualAccount(null);
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
