package bank.app.ui.console.uiservice;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.ui.console.ConsoleReader;

public abstract class AbstractConsoleService {
    @Autowired
    protected ConsoleReader input;

    protected AbstractConsoleService() {
    }

    protected AbstractConsoleService(ConsoleReader input) {
        this.input = input;
    }
}
