package com.matthers3.cryptomomo_test.models;

import java.util.Locale;

public class CryptoCurrency implements Comparable<CryptoCurrency> {
    public String name;
    public float price;

    public CryptoCurrency(String Name, float Price) {
        this.name = Name;
        this.price = Price;
    }

    public String PriceString()
    {
        return String.format(Locale.getDefault(), "$%.1f", price);
    }

    @Override
    public int compareTo(CryptoCurrency other) {
        float difference = other.price - price;
        return difference != 0 ? (int) (difference / Math.abs(difference)) : 0;
    }
}
