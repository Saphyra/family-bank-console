package bank.app.ui.console.uiservice.requestservice;

import java.util.List;

import bank.app.domain.Request;
import bank.app.service.RequestService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;

public class PendingRequestMenu extends AbstractMenu<String, Integer, String> {
    public static final int CANCEL_OPTION = 0;
    private Option<Integer, String> exitOption = Option.optionFactory(CANCEL_OPTION, "Cancel");
    private final RequestService requestService;
    private final String accountName;

    public PendingRequestMenu(ConsoleReader input, RequestService requestService, String accountName) {
        super(input);
        this.requestService = requestService;
        this.accountName = accountName;
    }

    public void interactUser() {
        interactUser(exitOption);
    }

    @Override
    protected void setDisplayedMessages() {
        setMenuHeader("The following requests are waiting for answer:");
        setMenuFooter("Choose a request to cancel");
        addOption(exitOption);
        addRequests();
    }

    public void addRequests() {
        List<Request> requests = requestService.getPendingRequestsByFromName(accountName);
        if (requests.isEmpty()) {
            addMessage("No pending requests.");
        } else {
            for (Request request : requests) {
                addOption(Option.optionFactory(request.getId(), request.getRequestInfo()));
            }
        }
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
        if (selection.getKey() != CANCEL_OPTION) {
            requestService.cancelRequest(selection.getKey());
        }
    }

    @Override
    protected void afterEnterMenu() {
        clearOptions();
        clearMessages();
        setDisplayedMessages();
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }
}
