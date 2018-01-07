package bank.app.ui.console.menu.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.domain.Bank;
import bank.app.domain.Transaction;
import bank.app.service.AccountService;
import bank.app.service.BankSession;
import bank.app.service.TransactionService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.bankservice.BankNewRequestMenu;

public class BankMenu extends AbstractMenu<String, Integer, String> {
    private static final int VIEW_BANK_DATA_OPTION = 3;
    private static final int SIMULATE_NEW_DAY_OPTION = 2;
    private static final int VIEW_NEW_REQUESTS_OPTION = 1;
    private static final Option<Integer, String> exitOption = Option.defaultBackOption();

    private @Autowired BankNewRequestMenu bankService;
    private @Autowired BankSession session;
    private @Autowired AccountService accountService;
    private @Autowired TransactionService transactionService;

    public BankMenu(ConsoleReader input) {
        super(input);
    }

    @Override
    public void interactUser() {
        interactUser(exitOption);

    }

    @Override
    protected void setDisplayedMessages() {
        setMenuHeader("Welcome to the bank!");
        setMenuFooter("What do you want to do?");
        addOption(exitOption);
        addOption(Option.optionFactory(VIEW_NEW_REQUESTS_OPTION, "View new requests"));
        addOption(Option.optionFactory(SIMULATE_NEW_DAY_OPTION, "Simulate new day"));
        addOption(Option.optionFactory(VIEW_BANK_DATA_OPTION, "View bank data"));
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
        switch (selection.getKey()) {
        case VIEW_NEW_REQUESTS_OPTION:
            bankService.interactUser();
            break;
        case SIMULATE_NEW_DAY_OPTION:
            simulateNewDay();
            break;
        case VIEW_BANK_DATA_OPTION:
            System.out.println(session.getActualBank().getBankInfo());
            break;
        }
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }

    // TODO refactor extract method
    private void simulateNewDay() {
        List<Account> accounts = accountService.getAllAccounts();
        double allInterest = 0;
        double allBankBalance = 0;
        for (Account account : accounts) {
            if (account.getBankBalance() < 0) {
                double interest = account.getBankBalance() * Bank.INTEREST_RATE * -1;
                allInterest += interest;
                Transaction transaction = Transaction.negativeInterestTransaction(account.getName(), interest);
                transactionService.sendTransaction(transaction);
            } else if (account.getBankBalance() > 0) {
                allBankBalance += account.getBankBalance();
            }
        }

        for (Account account : accounts) {
            if (account.getBankBalance() > 0) {
                double interest = allInterest / allBankBalance * account.getBankBalance();
                Transaction transaction = Transaction.positiveInterestTransaction(account.getName(), interest);
                transactionService.sendTransaction(transaction);
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Calculating interests is ready.").append(System.lineSeparator())//
                .append("Interests paid: ").append(allInterest);
        System.out.println(builder.toString());
    }
}
