package com.example.parsa.self3.Helper;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 * Created by parsa on 2015-01-25.
 */
public class StringHelper {


    public static String commaSeparator(String number){

        Double d = Double.parseDouble(number);

        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator('،');

        DecimalFormat formatter = new DecimalFormat("###,###", symbols);

        String str = formatter.format(d);

        return str;
    }
}
