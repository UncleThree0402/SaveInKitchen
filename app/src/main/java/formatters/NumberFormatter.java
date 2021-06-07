package formatters;

import java.text.DecimalFormat;

public class NumberFormatter {

    public static String moneyFormatter(double value){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.##");
        return "NT$ " + decimalFormat.format(value);
    }

    public static String quantityFormatter(double value){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,##0.##");
        return decimalFormat.format(value);
    }
}
