package bank.app.ui.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleReader extends BufferedReader {

    public ConsoleReader(InputStreamReader input) {
        super(input);
    }

    public String getUserInput(String label) {
        printLabel(label);
        return getUserInput();
    }

    private void printLabel(String label) {
        System.out.println(label);
    }

    public String getUserInput() {
        String result = "";
        try {
            result = readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
