package bank.app.ui.console.uiservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Account;
import bank.app.domain.Request;
import bank.app.service.AccountService;
import bank.app.service.BankSession;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.menu.impl.IdGetterMenu;

public class ConsoleRequestService extends AbstractConsoleService {
    private @Autowired BankSession session;
    private @Autowired AccountService accountService;

    // TODO remove SeppressWarnings after conditions set
    @SuppressWarnings("unused")
    public void createNewRequest() {
        Account account = session.getActualAccount();
        Request request = null;

        int toId = getToId(account.getId());
        if (toId != IdGetterMenu.EXIT_OPTION) {
            double money = getMoney(account.getPrivateBalance());
            // TODO validate user input
            if (false) {
                String requestMessage = getRequestMessage();
                // TODO validate user input
                if (false) {
                    request = new Request(account.getId(), toId, money);
                    request.setRequestMessage(requestMessage);
                }
            }
        }

        if (request != null) {
            // TODO send request
        }

    }

    private int getToId(int accountId) {
        IdGetterMenu idGetterMenu = new IdGetterMenu(input);
        idGetterMenu.addAddressees(getAddressees());
        idGetterMenu.interactUser();
        Option<Integer, String> selection = idGetterMenu.getSelection();
        return selection.getKey();
    }

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

    private double getMoney(double actualBalance) {
        return 0;
        // TODO ask for money
    }

    private String getRequestMessage() {
        return null;
        // TODO ask for request message
    }
}
