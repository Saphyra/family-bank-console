package bank.app.ui.console.uiservice.requestservice;

import java.util.List;

import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;

public class AccountGetterMenu extends AbstractMenu<String, Integer, String> {
    public static final int BANK_OPTION = 0;
    public static final int EXIT_OPTION = -1;
    private Option<Integer, String> exitOption = Option.optionFactory(EXIT_OPTION, "Cancel");
    private Option<Integer, String> bankOption = Option.optionFactory(BANK_OPTION, "Bank");
    private Option<Integer, String> selection;

    public AccountGetterMenu(ConsoleReader reader) {
        super(reader);
    }

    @Override
    protected void setDisplayedMessages() {
        setMenuHeader("You can choose one of the following addressees:");
        setMenuFooter("Select the addressee:");
        addOption(exitOption);
        addOption(bankOption);
    }

    public void addAddressees(List<Option<Integer, String>> addressees) {
        for (Option<Integer, String> addresse : addressees) {
            addOption(addresse);
        }
    }

    public void interactUser() {
        interactUser(exitOption);
    }

    @Override
    public void interactUser(Option<Integer, String> exitOption) {
        setDisplayedMessages();
        selection = doInteract();
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
        this.selection = selection;
    }

    public Option<Integer, String> getSelection() {
        return selection;
    }

    @Override
    protected Integer convertInputToKey(String userInput) {
        return Integer.valueOf(userInput);
    }

}
