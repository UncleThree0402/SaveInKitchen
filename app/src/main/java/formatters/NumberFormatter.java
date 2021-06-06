package formatters;

import java.text.DecimalFormat;

public class NumberFormatter {

    public static String moneyFormatter(double value){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###.00");
        return "NT$ " + decimalFormat.format(value);
    }
}
