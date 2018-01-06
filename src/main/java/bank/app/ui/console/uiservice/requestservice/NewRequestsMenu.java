package bank.app.ui.console.uiservice.requestservice;

import java.util.List;

import bank.app.domain.Request;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;

public class NewRequestsMenu extends AbstractMenu<String, Integer, String> {
    private final Option<Integer, String> exitOption = Option.optionFactory(0, "Back");

    public NewRequestsMenu(ConsoleReader input) {
        super(input);
    }

    @Override
    protected void setDisplayedMessages() {
        setMenuHeader("The following requests are waiting for your acceptance:");
        addOption(exitOption);
    }

    public void addRequests(List<Request> requests) {
        for (Request request : requests) {
            addOption(Option.optionFactory(request.getId(), request.getRequestInfo()));
        }
    }

    public void interactUser() {
        interactUser(exitOption);
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
        // TODO modify requests
    }

    @Override
    protected Integer convertInputToKey(String userInput) throws Exception {
        return Integer.valueOf(userInput);
    }

}
