package bank.app.ui.console.uiservice.requestservice;

import java.util.ArrayList;
import java.util.List;

import bank.app.domain.Account;
import bank.app.domain.Request;
import bank.app.service.AccountService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.Option;

public class NewRequestCreator {
    private final ConsoleReader input;
    private final AccountService accountService;

    public NewRequestCreator(ConsoleReader input, AccountService accountService) {
        this.input = input;
        this.accountService = accountService;
    }

    public Request createRequest(Account account) {
        Request request = null;
        int addresseeId = getAddresseeId(account.getId());
        if (addresseeId != AccountGetterMenu.EXIT_OPTION) {
            double money = getMoney();
            if (money > 0) {
                String requestMessage = getRequestMessage();
                if (!requestMessage.isEmpty()) {
                    String addresseeName = getAddresseeName(addresseeId);
                    request = new Request(account.getName(), addresseeName, money);
                    request.setRequestMessage(requestMessage);
                }
            }
        }
        return request;
    }

    private int getAddresseeId(int accountId) {
        Option<Integer, String> selection = getSelection(accountId);
        return selection.getKey();
    }

    private Option<Integer, String> getSelection(int accountId) {
        AccountGetterMenu idGetterMenu = new AccountGetterMenu(input);
        initIdGetterMenu(idGetterMenu, accountId);
        Option<Integer, String> selection = idGetterMenu.getSelection();
        return selection;
    }

    private void initIdGetterMenu(AccountGetterMenu idGetterMenu, int accountId) {
        idGetterMenu.addAddressees(getAddressees(accountId));
        idGetterMenu.interactUser();
    }

    // TODO refactor: accounts from method parameter
    private List<Option<Integer, String>> getAddressees(int accountId) {
        List<Account> accounts = accountService.getAllAccounts();
        List<Option<Integer, String>> result = new ArrayList<>();

        for (Account account : accounts) {
            if (account.getId() != accountId) {
                Option<Integer, String> option = Option.optionFactory(account.getId(), account.getName());
                result.add(option);
            }
        }
        return result;
    }

    // TODO refactor: extract methods
    private double getMoney() {
        double money;
        String userInput = input.getUserInput("How much money do you want to request? (Enter 0 if you want to cancel the request.");
        try {
            money = Double.valueOf(userInput);
        } catch (NumberFormatException e) {
            System.err.println("Type a number!");
            money = getMoney();
        }

        try {
            if (money < 0) {
                throw new IllegalArgumentException("Type a number higher than 0 or 0 to cancell request!");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            money = getMoney();
        }

        return money;
    }

    private String getRequestMessage() {
        return input.getUserInput("Type the message of the request or empty string to cancel request!");
    }

    private String getAddresseeName(int addresseeId) {
        String result = null;
        if (addresseeId == AccountGetterMenu.BANK_OPTION) {
            result = "Bank";
        } else {
            Account addressee = accountService.findById(addresseeId);
            result = addressee.getName();
        }
        return result;
    }
}
