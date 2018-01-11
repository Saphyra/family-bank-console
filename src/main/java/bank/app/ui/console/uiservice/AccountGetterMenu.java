package bank.app.ui.console.uiservice;

import java.util.List;

import bank.app.domain.Account;
import bank.app.ui.console.ConsoleReader;
import bank.app.ui.console.menu.AbstractMenu;
import bank.app.ui.console.menu.Option;

public class AccountGetterMenu extends AbstractMenu<String, Integer, String> {
    public static final int BANK_OPTION = 0;
    public static final int EXIT_OPTION = -1;
    public static final boolean BANK_INCLUDED = true;
    public static final boolean BANK_EXCLUDED = false;

    private static final Option<Integer, String> exitOption = Option.optionFactory(EXIT_OPTION, "Cancel");
    private static final Option<Integer, String> bankOption = Option.optionFactory(BANK_OPTION, "Bank");

    private final List<Account> accounts;
    private final boolean bankIncluded;

    public AccountGetterMenu(ConsoleReader input, List<Account> accounts, boolean bankIncluded) {
        super(input);
        this.accounts = accounts;
        this.bankIncluded = bankIncluded;
    }

    @Override
    protected void initMenu() {
        setMenuHeader("You can choose one of the following addressees:");
        setMenuFooter("Select the addressee:");
        addOption(exitOption);
        if (bankIncluded) {
            addOption(bankOption);
        }
        addAccounts();
    }

    private void addAccounts() {
        for (Account account : accounts) {
            addOption(Option.optionFactory(account.getId(), account.getName()));
        }
    }

    public int getSelectedId() {
        initMenu();
        Option<Integer, String> selection = doInteract();
        return selection.getKey();
    }

    @Override
    protected Integer convertInputToKey(String userInput) {
        return Integer.valueOf(userInput);
    }

    @Override
    public void interactUser() {
    }

    @Override
    protected void enterMenu(Option<Integer, String> selection) {
    }
}
