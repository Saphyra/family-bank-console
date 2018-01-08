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
    private boolean onlyOneRun = false;

    protected AbstractMenu() {
        this.input = new ConsoleReader(new InputStreamReader(System.in));
    }

    protected AbstractMenu(ConsoleReader input) {
        this.input = input;
    }

    protected abstract void setDisplayedMessages();

    protected void interactUser(Option<KeyType, OptionType> exitOption) {
        beforeStart();
        setDisplayedMessages();
        runMenuSequences(exitOption);
        afterExit();
    }

    protected void beforeStart() {
    }

    protected final void runMenuSequences(Option<KeyType, OptionType> exitOption) {
        Option<KeyType, OptionType> selection = null;
        do {
            selection = runMenuSequence(exitOption);
        } while (!onlyOneRun && !isExit(exitOption, selection));
    }

    protected final Option<KeyType, OptionType> runMenuSequence(Option<KeyType, OptionType> exitOption) {
        beforeSelection();
        Option<KeyType, OptionType> selection = doInteract();
        afterSelection();
        if (!isExit(exitOption, selection)) {
            runMenu(selection);
        }
        return selection;
    }

    protected void beforeSelection() {
    }

    protected Option<KeyType, OptionType> doInteract() {
        printUI();
        Option<KeyType, OptionType> selection = selectOption();
        return selection;
    }

    protected void printUI() {
        printMessages();
        printMenu();
    }

    protected void printMessages() {
        printLineSeparator();
        for (MessageType message : messages) {
            printMessage(message);
        }
        printLineSeparator();
    }

    protected final void printLineSeparator() {
        System.out.println(System.lineSeparator());
    }

    protected final void printMessage(MessageType message) {
        System.out.println(message.toString());
    }

    protected void printMenu() {
        printMenuHeader();
        printMenuOptions();
        printLineSeparator();
    }

    protected final void printMenuHeader() {
        System.out.println(menuHeader);
    }

    protected final void printMenuOptions() {
        Set<KeyType> keys = options.keySet();
        for (KeyType key : keys) {
            printMenuItem(key, options.get(key));
        }
    }

    protected void printMenuItem(KeyType key, Option<KeyType, OptionType> content) {
        System.out.println("[" + key + "] - " + content.toString());
    }

    protected final Option<KeyType, OptionType> selectOption() {
        Option<KeyType, OptionType> selection = null;
        do {
            selection = getSelection();
        } while (selection == null);
        return selection;
    }

    protected final Option<KeyType, OptionType> getSelection() {
        Option<KeyType, OptionType> selection = null;
        try {
            selection = getUserSelection();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return selection;
    }

    protected Option<KeyType, OptionType> getUserSelection() throws NoSuchElementException {
        Option<KeyType, OptionType> result = null;
        KeyType key = getKey();
        validateKey(key);
        result = options.get(key);

        printSelectedOption(result);

        return result;
    }

    protected final KeyType getKey() {
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

    protected final void validateKey(KeyType key) {
        if (!options.containsKey(key)) {
            throw new NoSuchElementException("Invalid user input.");
        }
    }

    protected void printSelectedOption(Option<KeyType, OptionType> result) {
        System.err.println("Selected option is: " + result.toString());
    }

    protected void afterSelection() {
    }

    protected final boolean isExit(Option<KeyType, OptionType> exitOption, Option<KeyType, OptionType> selection) {
        return selection.getKey().equals(exitOption.getKey());
    }

    protected final void runMenu(Option<KeyType, OptionType> selection) {
        beforeEnterMenu();
        enterMenu(selection);
        afterEnterMenu();
    }

    protected void beforeEnterMenu() {
    }

    protected abstract void enterMenu(Option<KeyType, OptionType> selection);

    protected void afterEnterMenu() {
    }

    protected void afterExit() {
    }

    ///////
    protected final void addMessage(MessageType message) {
        messages.add(message);
    }

    protected final void clearMessages() {
        messages.clear();
    }

    protected final void addOption(Option<KeyType, OptionType> content) {
        options.put(content.getKey(), content);
    }

    protected final void clearOptions() {
        options.clear();
    }

    protected final String getMenuHeader() {
        return menuHeader;
    }

    protected final void setMenuHeader(String menuHeader) {
        this.menuHeader = menuHeader;
    }

    protected final String getMenuFooter() {
        return menuFooter;
    }

    protected final void setMenuFooter(String menuFooter) {
        this.menuFooter = menuFooter;
    }

    protected final boolean isOnlyOneRun() {
        return onlyOneRun;
    }

    protected final void setOnlyOneRun(boolean onlyOneRun) {
        this.onlyOneRun = onlyOneRun;
    }
}
