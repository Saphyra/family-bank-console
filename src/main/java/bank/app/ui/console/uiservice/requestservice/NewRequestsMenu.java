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

    protected final RequestService requestService;
    private Account account;

    public NewRequestsMenu(ConsoleReader input, RequestService requestService, Account account) {
        super(input);
        this.requestService = requestService;
        this.account = account;
    }

    protected NewRequestsMenu(ConsoleReader input, RequestService requestService) {
        super(input);
        this.requestService = requestService;
    }

    @Override
    protected void initMenu() {
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
        if (requests.isEmpty()) {
            addMessage("No new requests.");
        } else {
            for (Request request : requests) {
                addOption(Option.optionFactory(request.getId(), request));
            }
        }
    }

    @Override
    public void interactUser() {
        interactUser(exitOption);
    }

    @Override
    protected void enterMenu(Option<Integer, Request> selection) {
        RequestModifierMenu rmMenu = new RequestModifierMenu(input, requestService, selection.getValue(), account.getPrivateBalance());
        rmMenu.interactUser();
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }

}
