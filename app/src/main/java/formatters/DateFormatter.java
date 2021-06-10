package formatters;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static final SimpleDateFormat dayMonthYearFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat dayMonthFormat = new SimpleDateFormat("dd/MM");
    public static final SimpleDateFormat dayMonthYearWordFormat = new SimpleDateFormat("d MMMM yyyy");


    public static String dayMonthYearFormatter(long dateTime){
        if(dateTime > 0){
            return dayMonthYearFormat.format(dateTime);
        }else
            return "NTH";
    }

    public static String dayMonthFormatter(Date dateTime){
        if(dateTime != null){
            return dayMonthFormat.format(dateTime);
        }else
            return "NTH";
    }

    public static long objectToLong (Object dateTime) throws ParseException {
        if(dateTime != null){
            Date date = dayMonthYearFormat.parse(dayMonthYearFormat.format(dateTime));
            return date.getTime();
        }else
            return 0;
    }

    public static String objectToString (Object dateTime){
        if(dateTime != null){
            return dayMonthYearWordFormat.format(dateTime);
        }else
            return "NTH";
    }

}
