package bank.app.ui.console.uiservice.requestservice;

import bank.app.domain.Request;
import bank.app.service.RequestService;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;

public class RequestModifierMenu extends AbstractMenu<String, Integer, String> {

    private static final int ACCEPT_OPTION = 2;
    private static final int DENY_OPTION = 1;
    private static final Option<Integer, String> cancelOption = Option.defaultCancelOption();
    private static final Option<Integer, String> denyOption = Option.optionFactory(DENY_OPTION, "Deny");
    private static final Option<Integer, String> acceptOption = Option.optionFactory(ACCEPT_OPTION, "Accept");

    private final RequestService requestService;
    private final Request request;
    private final double maxMoney;

    public RequestModifierMenu(ConsoleReader input, RequestService requestService, Request request, double maxMoney) {
        super(input);
        this.requestService = requestService;
        this.request = request;
        this.maxMoney = maxMoney;
    }

    @Override
    public void interactUser() {
        interactUser(cancelOption);
    }

    @Override
    protected void initMenu() {
        setOnlyOneRun(true);
        clearMessages();
        clearOptions();
        setMenuFooter("What do you want to do with this request?");
        addMessage(request.getRequestInfo());
        addOption(cancelOption);
        addOption(denyOption);
        if (request.getMoney() <= maxMoney) {
            addOption(acceptOption);
        }
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
        switch (selection.getKey()) {
        case DENY_OPTION:
            denyRequest();
            break;
        case ACCEPT_OPTION:
            acceptRequest();
            break;
        }
    }

    private void denyRequest() {
        String denyMessage = input.getUserInput("Why do you deny te request? (Empty string to cancel)");
        if (!denyMessage.isEmpty()) {
            requestService.deny(request, denyMessage);
        }
    }

    private void acceptRequest() {
        String acceptMessage = input.getUserInput("Why do you accept te request? (Empty string to cancel)");
        if (!acceptMessage.isEmpty()) {
            requestService.accept(request, acceptMessage);
        }
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }

}
