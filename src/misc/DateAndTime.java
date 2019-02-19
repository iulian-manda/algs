package misc;

import javax.swing.text.NumberFormatter;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

public class DateAndTime {
    public static String findDay(int month, int day, int year) {
        String[] weekdays = DateFormatSymbols.getInstance().getWeekdays();
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DATE, day);
        return weekdays[c.get(Calendar.DAY_OF_WEEK)].toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(findDay(8, 5, 2018));
    }
}
