package bank.app.ui.console.uiservice.bankservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.domain.Bank;
import bank.app.domain.Request;
import bank.app.service.BankSession;
import bank.app.service.RequestService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.Option;
import bank.app.ui.console.uiservice.requestservice.NewRequestsMenu;
import bank.app.ui.console.uiservice.requestservice.RequestModifierMenu;

public class BankNewRequestMenu extends NewRequestsMenu {
    private static final Request EXIT_REQUEST = new Request();
    static {
        EXIT_REQUEST.setId(0);
    }
    private static final Option<Integer, Request> EXIT_OPTION = Option.optionFactory(0, EXIT_REQUEST);
    private @Autowired BankSession session;

    public BankNewRequestMenu(ConsoleReader input, RequestService requestService) {
        super(input, requestService);
    }

    @Override
    public void interactUser() {
        interactUser(EXIT_OPTION);

    }

    @Override
    protected void initMenu() {
        setMenuHeader("The following requests are waiting for your acceptance:");
        addOption(EXIT_OPTION);
    }

    @Override
    public void beforeSelection() {
        clearOptions();
        addOption(EXIT_OPTION);
        List<Request> newRequests = getNewRequests();
        addRequests(newRequests);
    }

    private List<Request> getNewRequests() {
        Bank actualBank = session.getActualBank();
        String bankName = actualBank.getName();
        return requestService.getNewRequests(bankName);
    }

    @Override
    protected void enterMenu(Option<Integer, Request> selection) {
        double balance = session.getActualBank().getBalance();
        Request request = selection.getValue();
        RequestModifierMenu rmMenu = new RequestModifierMenu(input, requestService, request, balance);
        rmMenu.interactUser();
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }
}
