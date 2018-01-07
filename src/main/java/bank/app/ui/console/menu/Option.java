package bank.app.ui.console.menu;

public class Option<Key, Value> {
    public static final int EXIT_KEY = 0;
    public static final int BACK_KEY = 0;

    private final Key key;
    private final Value value;

    public Option(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public static Option<Integer, String> defaultExitOption() {
        return new Option<Integer, String>(EXIT_KEY, "Exit");
    }

    public static Option<Integer, String> defaultBackOption() {
        return new Option<Integer, String>(BACK_KEY, "Back");
    }

    public static Option<Integer, String> defaultCancelOption() {
        return new Option<Integer, String>(BACK_KEY, "Cancel");
    }

    public static Option<Integer, String> defaultLogoutOption() {
        return new Option<Integer, String>(BACK_KEY, "Logout");
    }

    public static <KeyType, OptionType> Option<KeyType, OptionType> optionFactory(KeyType key, OptionType value) {
        return new Option<KeyType, OptionType>(key, value);
    }

    public Key getKey() {
        return key;
    }

    public Value getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
