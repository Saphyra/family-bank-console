package bank.app.ui.console.menu;

public class Option<Key, Value> {
    public static final String EXIT_KEY = "0";
    public static final String BACK_KEY = "0";

    private final Key key;
    private final Value value;

    public Option(Key key, Value value) {
        this.key = key;
        this.value = value;
    }

    public static Option<String, String> defaultExitOption() {
        return new Option<String, String>(EXIT_KEY, "Exit");
    }

    public static Option<String, String> defaultBackOption() {
        return new Option<String, String>(BACK_KEY, "Back");
    }

    public static Option<String, String> defaultCancelOption() {
        return new Option<String, String>(BACK_KEY, "Cancel");
    }

    public static Option<String, String> defaultLogoutOption() {
        return new Option<String, String>(BACK_KEY, "Logout");
    }

    public static Option<String, String> optionFactory(String key, String value) {
        return new Option<String, String>(key, value);
    }

    public static Option<String, String> optionFactory(int key, String value) {
        return optionFactory(Integer.toString(key), value);
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
