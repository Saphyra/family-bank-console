package bank.app.ui.console.uiservice;

import org.springframework.beans.factory.annotation.Autowired;

import bank.app.ui.console.ConsoleReader;

public class AbstractConsoleService {
    @Autowired
    protected ConsoleReader input;
}
