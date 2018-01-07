package bank.app.ui.console.uiservice.requestservice;

import java.util.List;

import bank.app.domain.Account;
import bank.app.domain.Request;
import bank.app.service.AccountService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.uiservice.AccountGetterMenu;

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
        List<Account> addressees = accountService.getAddressees(accountId);
        AccountGetterMenu accountGetterMenu = new AccountGetterMenu(input, addressees, AccountGetterMenu.BANK_INCLUDED);
        return accountGetterMenu.getSelectedId();
    }

    private double getMoney() {
        double money;
        String userInput = input.getUserInput("How much money do you want to request? (Enter 0 if you want to cancel the request.");
        try {
            money = Double.valueOf(userInput);
            if (money < 0) {
                throw new IllegalArgumentException("Type a number higher than 0 or 0 to cancell request!");
            }
        } catch (NumberFormatException e) {
            System.err.println("Type a number!");
            money = getMoney();
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
