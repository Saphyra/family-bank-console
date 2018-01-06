package bank.app.ui.console.uiservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.domain.Request;
import bank.app.service.AccountService;
import bank.app.service.BankSession;
import bank.app.service.RequestService;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.menu.impl.IdGetterMenu;

public class ConsoleRequestService extends AbstractConsoleService {
    private @Autowired BankSession session;
    private @Autowired AccountService accountService;
    private @Autowired RequestService requestService;

    // TODO refactor: extract methods
    public void createNewRequest() {
        Account account = session.getActualAccount();
        Request request = null;

        int toId = getToId(account.getId());
        if (toId != IdGetterMenu.EXIT_OPTION) {
            double money = getMoney();
            if (money > 0) {
                String requestMessage = getRequestMessage();
                if (!requestMessage.isEmpty()) {
                    request = new Request(account.getId(), toId, money);
                    request.setRequestMessage(requestMessage);
                }
            }
        }

        if (request != null) {
            requestService.saveRequest(request);
            // TODO print request data
            System.out.println("Request sent.");
        }

    }

    // TODO refactor: extract methods
    private int getToId(int accountId) {
        IdGetterMenu idGetterMenu = new IdGetterMenu(input);
        idGetterMenu.addAddressees(getAddressees());
        idGetterMenu.interactUser();
        Option<Integer, String> selection = idGetterMenu.getSelection();
        return selection.getKey();
    }

    // TODO refactor: extract methods
    private List<Option<Integer, String>> getAddressees() {
        List<Account> accounts = accountService.getAllAccounts();
        List<Option<Integer, String>> result = new ArrayList<>();
        Account actualAccount = session.getActualAccount();

        for (Account account : accounts) {
            if (account.getId() != actualAccount.getId()) {
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
}
