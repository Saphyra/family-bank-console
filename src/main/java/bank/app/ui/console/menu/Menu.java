package bank.app.ui.console.menu;

public interface Menu<KeyType, OptionType> {
    void interactUser(Option<KeyType, OptionType> exitOption);
}
