package formatters;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    public static final SimpleDateFormat dayMonthYearFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat dayMonthFormat = new SimpleDateFormat("dd/MM");

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

}
