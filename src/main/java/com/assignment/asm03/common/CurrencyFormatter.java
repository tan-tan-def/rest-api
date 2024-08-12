package com.assignment.asm03.common;

import java.text.DecimalFormat;

public class CurrencyFormatter {
    public static String formatCurrency(int amount, String currencySymbol) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        String formattedAmount = decimalFormat.format(amount);
        return formattedAmount + " " + currencySymbol;
    }
}
