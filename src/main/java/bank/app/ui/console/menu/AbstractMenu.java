package bank.app.ui.console.menu;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import bank.app.ui.console.ConsoleReader;

public abstract class AbstractMenu<MessageType, KeyType extends Comparable<KeyType>, OptionType> implements Menu<KeyType, OptionType> {
    protected final ConsoleReader input;

    private List<MessageType> messages = new ArrayList<>();
    private Map<KeyType, Option<KeyType, OptionType>> options = new TreeMap<>();
    private String menuHeader = "Options:";
    private String menuFooter = "Choose your option: ";

    protected AbstractMenu() {
        this.input = new ConsoleReader(new InputStreamReader(System.in));
    }

    protected AbstractMenu(ConsoleReader input) {
        this.input = input;
    }

    protected abstract void setDisplayedMessages();

    protected void addMessage(MessageType message) {
        messages.add(message);
    }

    protected void addOption(Option<KeyType, OptionType> content) {
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

    protected abstract void enterMenu(Option<KeyType, OptionType> selection);

    @Override
    public void interactUser(Option<KeyType, OptionType> exitOption) {
        beforeStart();
        setDisplayedMessages();
        Option<KeyType, OptionType> selection = null;
        do {
            selection = doInteract();
            if (!isExit(exitOption, selection)) {
                enterMenu(selection);
            }
        } while (!isExit(exitOption, selection));
        afterExit();
    }

    protected void beforeStart() {
    }

    protected Option<KeyType, OptionType> doInteract() {
        printMenu();
        Option<KeyType, OptionType> selection = null;
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
        for (MessageType message : messages) {
            System.out.println(message.toString());
        }
        System.out.println("");
    }

    private void printOptions() {
        System.out.println(menuHeader);
        Set<KeyType> keys = options.keySet();
        for (KeyType key : keys) {
            Option<KeyType, OptionType> content = options.get(key);
            System.out.println("[" + key + "] - " + content.toString());
        }
        System.out.println();
    }

    protected Option<KeyType, OptionType> getUserSelection() throws NoSuchElementException {
        Option<KeyType, OptionType> result = null;

        KeyType key = getKey();
        validateKey(key);
        result = options.get(key);

        System.err.println("Selected option is: " + result.toString());

        return result;
    }

    private KeyType getKey() {
        KeyType key = null;
        try {
            String userInput = input.getUserInput(menuFooter);
            key = convertInputToKey(userInput);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            key = getKey();
        }
        return key;
    }

    protected abstract KeyType convertInputToKey(String userInput) throws Exception;

    private void validateKey(KeyType key) {

        if (!options.containsKey(key)) {
            throw new NoSuchElementException("Invalid user input.");
        }
    }

    private boolean isExit(Option<KeyType, OptionType> exitOption, Option<KeyType, OptionType> selection) {
        return selection.getKey().equals(exitOption.getKey());
    }

    protected void afterExit() {
    }
}
