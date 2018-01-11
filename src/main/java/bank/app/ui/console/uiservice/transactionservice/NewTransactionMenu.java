package bank.app.ui.console.uiservice.transactionservice;

import java.util.List;

import bank.app.domain.Account;
import bank.app.domain.Bank;
import bank.app.domain.Transaction;
import bank.app.service.AccountService;
import bank.app.service.BankSession;
import bank.app.service.TransactionService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.AccountGetterMenu;

public class NewTransactionMenu extends AbstractMenu<String, Integer, String> {
    private static final String WITHDRAW_INPUT_EXCEPTION_MESSAGE = "You cannot withdraw more money than you have in bank, or the bank's actual balance.";
    private static final String WITHDRAW_INPUT_MESSAGE = "How much money do you want to withdraw?";
    private static final String DEPOSIT_INPUT_EXCEPTION_MESSAGE = "You cannot deposit more money than you actually have.";
    private static final String DEPOSIT_INPUT_MESSAGE = "How much money do you want to deposit?";
    private static final String SEND_INPUT_EXCEPTION_MESSAGE = "You cannot send more money than you actually have.";
    private static final String SEND_INPUT_MESSAGE = "How much money do you want to send?";
    private static final String SPEND_INPUT_EXCEPTION_MESSAGE = "You cannot spend more money than you actually have.";
    private static final String SPEND_INPUT_MESSAGE = "How much money do you want to spend?";

    private static final Option<Integer, String> exitOption = Option.defaultBackOption();

    private static final int SEND_OPTION = 1;
    private static final int WITHDRAW_OPTION = 2;
    private static final int DEPOSIT_OPTION = 3;
    private static final int EARN_OPTION = 4;
    private static final int SPEND_OPTION = 5;

    private final TransactionService transactionService;
    private final AccountService accountService;
    private Account account;
    private final Bank bank;
    private final BankSession session;

    public NewTransactionMenu(ConsoleReader input, TransactionService transactionService, AccountService accountService, BankSession session) {
        super(input);
        this.transactionService = transactionService;
        this.accountService = accountService;
        this.account = session.getActualAccount();
        this.bank = session.getActualBank();
        this.session = session;
    }

    @Override
    public void interactUser() {
        interactUser(exitOption);
    }

    @Override
    protected void initMenu() {
        setMenuHeader("");
        setMenuFooter("Choose one transaction type!");
        addOption(exitOption);
        addOption(Option.optionFactory(SEND_OPTION, "Send money to other account"));
        addOption(Option.optionFactory(WITHDRAW_OPTION, "Withdraw money"));
        addOption(Option.optionFactory(DEPOSIT_OPTION, "Deposit money"));
        addOption(Option.optionFactory(EARN_OPTION, "Earn money"));
        addOption(Option.optionFactory(SPEND_OPTION, "Spend money"));
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
        switch (selection.getKey()) {
        case SEND_OPTION:
            sendMoney();
            break;
        case WITHDRAW_OPTION:
            withdrawMoney();
            break;
        case DEPOSIT_OPTION:
            depositMoney();
            break;
        case EARN_OPTION:
            earnMoney();
            break;
        case SPEND_OPTION:
            spendMoney();
            break;
        }
        account = session.getActualAccount();
    }

    public void depositMoney() {
        double money = getMoney(account.getPrivateBalance(), DEPOSIT_INPUT_MESSAGE, DEPOSIT_INPUT_EXCEPTION_MESSAGE);
        if (money > 0) {
            Transaction transaction = createDepositTransaction(money);
            transactionService.sendTransaction(transaction);
        }
    }

    private Transaction createDepositTransaction(double money) {
        Transaction transaction = Transaction.depositTransaction(account.getName(), money);
        System.out.println("Transaction sent.");
        System.out.println(transaction.getTransactionInfo());
        return transaction;
    }

    private void withdrawMoney() {
        double maxMoney = getWithdrawMaxMoney();
        double money = getMoney(maxMoney, WITHDRAW_INPUT_MESSAGE, WITHDRAW_INPUT_EXCEPTION_MESSAGE);
        if (money > 0) {
            Transaction transaction = createWithdrawTransaction(money);
            transactionService.sendTransaction(transaction);
        }
    }

    private double getWithdrawMaxMoney() {
        return (account.getBankBalance() > bank.getBalance()) ? bank.getBalance() : account.getBankBalance();
    }

    private Transaction createWithdrawTransaction(double money) {
        Transaction transaction = Transaction.withdrawTransaction(account.getName(), money);
        System.out.println("Transaction sent.");
        System.out.println(transaction.getTransactionInfo());
        return transaction;
    }

    private void earnMoney() {
        double money = getMoney("How much money did you earn?");
        if (money > 0) {
            Transaction transaction = createEarnTransaction(money);
            transactionService.sendTransaction(transaction);
        }
    }

    private Transaction createEarnTransaction(double money) {
        Transaction transaction = Transaction.earnTransaction(account.getName(), money);
        System.out.println("Transaction sent.");
        System.out.println(transaction.getTransactionInfo());
        return transaction;
    }

    private void spendMoney() {
        double money = getMoney(account.getPrivateBalance(), SPEND_INPUT_MESSAGE, SPEND_INPUT_EXCEPTION_MESSAGE);
        if (money > 0) {
            Transaction transaction = createSpendTransaction(money);
            transactionService.sendTransaction(transaction);
        }
    }

    private Transaction createSpendTransaction(double money) {
        Transaction transaction = Transaction.spendTransaction(account.getName(), money);
        System.out.println("Transaction sent.");
        System.out.println(transaction.getTransactionInfo());
        return transaction;
    }

    private void sendMoney() {
        List<Account> accounts = accountService.getAddressees(account.getId());
        AccountGetterMenu accountGetter = new AccountGetterMenu(input, accounts, AccountGetterMenu.BANK_EXCLUDED);
        int addresseeId = accountGetter.getSelectedId();
        if (addresseeId != AccountGetterMenu.EXIT_OPTION) {
            double money = getMoney(account.getPrivateBalance(), SEND_INPUT_MESSAGE, SEND_INPUT_EXCEPTION_MESSAGE);
            if (money > 0) {
                Transaction transaction = createSendTransaction(addresseeId, money);
                transactionService.sendTransaction(transaction);
            }
        }
    }

    private Transaction createSendTransaction(int addresseeId, double money) {
        String addresseeName = accountService.findById(addresseeId).getName();
        Transaction transaction = Transaction.sendTransaction(account.getName(), addresseeName, money);
        System.out.println("Transaction sent.");
        System.out.println(transaction.getTransactionInfo());
        return transaction;
    }

    private double getMoney(double maxMoney, String label, String exceptionMessage) {
        double result = 0;
        try {
            result = getMoney(label + " (Max: " + maxMoney + ")");
            if (result > maxMoney) {
                throw new IllegalArgumentException(exceptionMessage);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            result = getMoney(maxMoney, label, exceptionMessage);
        }
        return result;
    }

    private double getMoney(String message) {
        double result = 0;

        String userInput = input.getUserInput(message + " (Enter 0 to cancel)");
        result = convertInput(userInput, message);
        try {
            validateInput(result);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            result = getMoney(message);
        }

        return result;
    }

    protected double convertInput(String userInput, String message) {
        double result = 0.0;
        try {
            result = Double.valueOf(userInput);
        } catch (NumberFormatException e) {
            System.err.println("Enter a number!");
            result = getMoney(message);
        }
        return result;
    }

    protected void validateInput(double input) {
        if (input < 0) {
            throw new IllegalArgumentException("Number must not be negative!");
        }
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }
}
