package bank.app.ui.console.uiservice.requestservice;

import java.util.List;

import bank.app.domain.Account;
import bank.app.domain.Request;
import bank.app.service.RequestService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;

public class NewRequestsMenu extends AbstractMenu<String, Integer, Request> {
    private static final Request EXIT_REQUEST = new Request();
    static {
        EXIT_REQUEST.setId(0);
    }
    private static final Option<Integer, Request> exitOption = Option.optionFactory(0, EXIT_REQUEST);

    private final RequestService requestService;
    private final Account account;

    public NewRequestsMenu(ConsoleReader input, RequestService requestService, Account account) {
        super(input);
        this.requestService = requestService;
        this.account = account;
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
        addRequests(requestService.getNewRequests(account.getName()));
    }

    public void addRequests(List<Request> requests) {
        for (Request request : requests) {
            addOption(Option.optionFactory(request.getId(), request));
        }
    }

    @Override
    public void interactUser() {
        interactUser(exitOption);
    }

    @Override
    protected void enterMenu(Option<Integer, Request> selection) {
        RequestModifierMenu rmMenu = new RequestModifierMenu(input, requestService, selection.getValue());
        rmMenu.modify(account.getPrivateBalance());
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }

}
