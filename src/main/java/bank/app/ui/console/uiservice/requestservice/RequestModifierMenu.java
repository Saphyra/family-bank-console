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

    public RequestModifierMenu(ConsoleReader input, RequestService requestService, Request request) {
        super(input);
        this.requestService = requestService;
        this.request = request;
    }

    public void modify(double maxMoney) {
        setOnlyOneRun(true);
        clearMessages();
        clearOptions();
        setDisplayedMessages();
        addMessage(request.getRequestInfo());
        addOption(cancelOption);
        addOption(denyOption);
        if (request.getMoney() <= maxMoney) {
            addOption(acceptOption);
        }
        interactUser();
    }

    @Override
    public void interactUser() {
        interactUser(cancelOption);
    }

    @Override
    protected void setDisplayedMessages() {
        setMenuFooter("What do you want to do with this request?");
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
        switch (selection.getKey()) {
        case DENY_OPTION:
            String denyMessage = input.getUserInput("Why do you deny te request? (Empty string to cancel)");
            if (!denyMessage.isEmpty()) {
                requestService.deny(request, denyMessage);
            }
            break;
        case ACCEPT_OPTION:
            String acceptMessage = input.getUserInput("Why do you accept te request? (Empty string to cancel)");
            if (!acceptMessage.isEmpty()) {
                requestService.accept(request, acceptMessage);
            }
            break;
        }
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }

}
