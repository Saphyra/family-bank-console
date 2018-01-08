package bank.app.ui.console;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.ui.UserInterface;
import bank.app.ui.console.menu.Menu;

public class ConsoleUserInterface implements UserInterface {
    private @Autowired Menu<Integer, String> mainMenu;

    @Override
    public void run() {
        mainMenu.interactUser();
    }
}
