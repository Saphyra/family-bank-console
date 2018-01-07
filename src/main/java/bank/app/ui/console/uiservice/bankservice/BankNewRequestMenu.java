package bank.app.ui.console.uiservice.bankservice;

import org.springframework.beans.factory.annotation.Autowired;

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
    private static final Option<Integer, Request> exitOption = Option.optionFactory(0, EXIT_REQUEST);
    private @Autowired BankSession session;

    public BankNewRequestMenu(ConsoleReader input, RequestService requestService) {
        super(input, requestService);
    }

    @Override
    public void interactUser() {
        interactUser(exitOption);

    }

    @Override
    protected void setDisplayedMessages() {
        setMenuHeader("The following requests are waiting for your acceptance:");
        addOption(exitOption);
    }

    @Override
    public void beforeSelection() {
        clearOptions();
        addOption(exitOption);
        addRequests(requestService.getNewRequests(session.getActualBank().getName()));
    }

    @Override
    protected void enterMenu(Option<Integer, Request> selection) {
        RequestModifierMenu rmMenu = new RequestModifierMenu(input, requestService, selection.getValue());
        rmMenu.modify(session.getActualBank().getBalance());
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }
}
