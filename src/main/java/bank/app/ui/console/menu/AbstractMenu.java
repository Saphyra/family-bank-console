package bank.app.ui.console.menu;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import bank.app.ui.console.ConsoleReader;

/**
 * This class is the supertype for Console type menus. It implements the basic interact with the user inculding the input validation.
 * 
 */
public abstract class AbstractMenu<MessageType, KeyType extends Comparable<KeyType>, OptionType> implements Menu<KeyType, OptionType> {
    protected final ConsoleReader input;

    private List<MessageType> messages = new ArrayList<>();
    private Map<KeyType, Option<KeyType, OptionType>> options = new TreeMap<>();
    private String menuHeader = "Options:";
    private String menuFooter = "Choose your option: ";
    private boolean onlyOneRun = false;

    /**
     * Default consturctor.
     * 
     * @return a Menu object with a new instance of {@link ConsoleReader} as user input.
     */
    protected AbstractMenu() {
        this.input = new ConsoleReader(new InputStreamReader(System.in));
    }

    /**
     * A new Menu with the given {@link ConsoleReader}
     * 
     * @param input
     *            an instance of {@link ConsoleReader} to use as user input
     * @return a Menu objext with the given {@link ConsoleReader} as user input
     */
    protected AbstractMenu(ConsoleReader input) {
        this.input = input;
    }

    /**
     * Starts the interaction with the user.
     * 
     * @param exitOption
     *            The {@link Option} that indicates the menu exits running when selected
     */
    protected void interactUser(Option<KeyType, OptionType> exitOption) {
        beforeStart();
        initMenu();
        runMenuSequences(exitOption);
        afterExit();
    }

    /**
     * Runs first when the interaction with user starts.
     */
    protected void beforeStart() {
    }

    /**
     * Configures the menu's welcome messages, menuHeader, menuFooter, and the displayed options the user can select.
     */
    protected abstract void initMenu();

    /**
     * Runs menuSequence until the user selects the exitOption, or onlyOneRun changes true.
     * 
     * @param exitOption
     *            The {@link Option} that indicates the menu exits running when selected
     */
    protected final void runMenuSequences(Option<KeyType, OptionType> exitOption) {
        Option<KeyType, OptionType> selection = null;
        do {
            selection = runMenuSequence(exitOption);
        } while (!onlyOneRun && !isExit(exitOption, selection));
    }

    /**
     * Run a menu sequence what contains printing the menu messages and options, asking input from the user, and run the command the selected option indicates
     * 
     * @param exitOption
     *            The {@link Option} that indicates the menu exits running when selected
     * @return The selected {@link Option}
     */
    protected final Option<KeyType, OptionType> runMenuSequence(Option<KeyType, OptionType> exitOption) {
        beforeSelection();
        Option<KeyType, OptionType> selection = doInteract();
        afterSelection();
        if (!isExit(exitOption, selection)) {
            runMenu(selection);
        }
        return selection;
    }

    /**
     * Runs before the selection phase.
     */
    protected void beforeSelection() {
    }

    /**
     * Starts interacting the user.
     * <p>
     * Printing the menu messages and options, asking input from the user, and getting the selected option.
     * 
     * @return The selected {@link Option}
     */
    protected Option<KeyType, OptionType> doInteract() {
        printUI();
        Option<KeyType, OptionType> selection = selectOption();
        return selection;
    }

    /**
     * Prints the Menu. (Messages, menu header, options, and menu footer.)
     */
    protected void printUI() {
        printMessages();
        printMenu();
    }

    /**
     * Prints the welcome messages.
     */
    protected void printMessages() {
        printLineSeparator();
        for (MessageType message : messages) {
            printMessage(message);
        }
        printLineSeparator();
    }

    /**
     * Prints a line separator
     */
    protected final void printLineSeparator() {
        System.out.println(System.lineSeparator());
    }

    /**
     * Prints a single message, invoking the MessageType object's toString() method.
     */
    protected final void printMessage(MessageType message) {
        System.out.println(message.toString());
    }

    /**
     * Prints the menu's header and options.
     */
    protected void printMenu() {
        printMenuHeader();
        printMenuOptions();
        printLineSeparator();
    }

    /**
     * Prints the menu header.
     */
    protected final void printMenuHeader() {
        System.out.println(menuHeader);
    }

    /**
     * Prints the options.
     */
    protected final void printMenuOptions() {
        Set<KeyType> keys = options.keySet();
        for (KeyType key : keys) {
            printMenuItem(options.get(key));
        }
    }

    /**
     * Prints an option as menu item.
     * <p>
     * A menu item contains its key's toString(), and the option's toString()
     * 
     * @param option
     *            The {@link Option} to print
     */
    protected void printMenuItem(Option<KeyType, OptionType> option) {
        System.out.println("[" + option.getKey().toString() + "] - " + option.toString());
    }

    /**
     * Gets the selected {@link Option}
     * 
     * @return The selected {@link Option}
     */
    protected final Option<KeyType, OptionType> selectOption() {
        Option<KeyType, OptionType> selection = null;
        do {
            selection = getSelection();
        } while (selection == null);
        return selection;
    }

    /**
     * Asks the user to select an {@link Option}
     * 
     * @return The selected {@link Option} if the {@link Option} is valid
     * @return null otherwise
     */
    protected final Option<KeyType, OptionType> getSelection() {
        Option<KeyType, OptionType> selection = null;
        try {
            selection = getUserSelection();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return selection;
    }

    /**
     * Asks the user to select an {@link Option}.
     * 
     * @return the selected {@link Option}
     * @throws NoSuchElementException
     *             if the Map of options does not contains the selected key
     */
    protected Option<KeyType, OptionType> getUserSelection() throws NoSuchElementException {
        KeyType key = getKey();
        validateKey(key);
        Option<KeyType, OptionType> result = options.get(key);

        printSelectedOption(result);

        return result;
    }

    /**
     * Asks the user for entering the key of the {@link Option} he wants to select.
     * <p>
     * If the entered key is not valid, asks again until it get valid key.
     * 
     * @return the selected key.
     */
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

    /**
     * Parses the input String to KeyType.
     * 
     * @param userInput
     *            The String the user entered
     * @return KeyType object parsed from userInput
     * @throws Exception
     *             If an Exception thrown during the parsing process
     */
    protected abstract KeyType convertInputToKey(String userInput) throws Exception;

    /**
     * Validates the key the user entered.
     * 
     * @param key
     *            The key to validate
     * @throws NoSuchElementException
     *             If no {@link Option} int the Map with the entered key.
     */
    protected final void validateKey(KeyType key) throws NoSuchElementException {
        if (!options.containsKey(key)) {
            throw new NoSuchElementException("Invalid user input.");
        }
    }

    /**
     * Prints the selected {@link Option}.
     * 
     * @param result
     *            The {@link Option} to print
     */
    protected void printSelectedOption(Option<KeyType, OptionType> result) {
        System.err.println("Selected option is: " + result.toString());
    }

    /**
     * Runs after the selection phase ended.
     */
    protected void afterSelection() {
    }

    /**
     * Determinates if the selected option is the exitOption
     * 
     * @param exitOption
     *            The {@link Option} that indicates the menu to exit.
     * @param selection
     *            The selected {@link Option}
     * @return true is selection equals exitOption
     * @return false otherwise
     */
    protected final boolean isExit(Option<KeyType, OptionType> exitOption, Option<KeyType, OptionType> selection) {
        return selection.getKey().equals(exitOption.getKey());
    }

    /**
     * Enters the processing phase
     * 
     * @param selection
     */
    protected final void runMenu(Option<KeyType, OptionType> selection) {
        beforeEnterMenu();
        enterMenu(selection);
        afterEnterMenu();
    }

    /**
     * Runs first after entering processing phase
     */
    protected void beforeEnterMenu() {
    }

    /**
     * Processing the selected task.
     * 
     * @param selection
     *            The selected {@link Option}
     */
    protected abstract void enterMenu(Option<KeyType, OptionType> selection);

    /**
     * Runs after finishing processing phase
     */
    protected void afterEnterMenu() {
    }

    /**
     * Runs after the menuSequence(s) exits.
     */
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
