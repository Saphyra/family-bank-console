package bank.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy hh:mm:ss");

    public static String formatDate(Date date) {
        return formatter.format(date);
    }
}
