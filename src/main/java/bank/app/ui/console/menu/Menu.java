package bank.app.ui.console.menu;

public interface Menu {
    void interactUser();

    void interactUser(Option<String, String> exitOption);
}
