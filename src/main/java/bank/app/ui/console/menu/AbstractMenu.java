package bank.app.ui.console.menu;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import bank.app.ui.console.ConsoleReader;

public abstract class AbstractMenu implements Menu {
    protected final ConsoleReader input;

    private List<String> messages = new ArrayList<>();
    private Map<String, Option<String, String>> options = new HashMap<>();
    private String menuHeader = "Options:";
    private String menuFooter = "Choose your option: ";

    protected AbstractMenu() {
        this.input = new ConsoleReader(new InputStreamReader(System.in));
    }

    protected AbstractMenu(ConsoleReader input) {
        this.input = input;
    }

    protected abstract void setDisplayedMessages();

    protected void addMessage(String message) {
        messages.add(message);
    }

    protected void addOption(Option<String, String> content) {
        options.put(content.getKey(), content);
    }

    public String getMenuHeader() {
        return menuHeader;
    }

    public void setMenuHeader(String menuHeader) {
        this.menuHeader = menuHeader;
    }

    public String getMenuFooter() {
        return menuFooter;
    }

    public void setMenuFooter(String menuFooter) {
        this.menuFooter = menuFooter;
    }

    protected abstract void enterMenu(Option<String, String> selection);

    @Override
    public void interactUser() {
        interactUser(Option.defaultExitOption());
    }

    @Override
    public void interactUser(Option<String, String> exitOption) {
        setDisplayedMessages();
        Option<String, String> selection = null;
        do {
            selection = doInteract();
            if (!isExit(exitOption, selection)) {
                enterMenu(selection);
            }
        } while (!isExit(exitOption, selection));
    }

    protected Option<String, String> doInteract() {
        printMenu();
        Option<String, String> selection = null;
        do {
            try {
                selection = getUserSelection();
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
        } while (selection == null);
        return selection;
    }

    protected void printMenu() {
        printMessages();
        printOptions();
    }

    private void printMessages() {
        System.out.println("\n");
        for (String message : messages) {
            System.out.println(message.toString());
        }
        System.out.println("");
    }

    private void printOptions() {
        System.out.println(menuHeader);
        Set<String> keys = options.keySet();
        for (String key : keys) {
            Option<String, String> content = options.get(key);
            System.out.println("[" + key + "] - " + content.toString());
        }
        System.out.println();
    }

    protected Option<String, String> getUserSelection() throws NoSuchElementException {
        Option<String, String> result = null;

        String key = input.getUserInput(menuFooter);
        validateKey(key);
        result = options.get(key);

        System.err.println("Selected option is: " + result.toString());

        return result;
    }

    private void validateKey(String key) {
        if (!options.containsKey(key)) {
            throw new NoSuchElementException("Invalid user input.");
        }
    }

    private boolean isExit(Option<String, String> exitOption, Option<String, String> selection) {
        return selection.getKey().equals(exitOption.getKey());
    }
}
