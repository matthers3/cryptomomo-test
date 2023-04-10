package com.matthers3.cryptomomo_test.models;

import java.util.Locale;

public class CryptoCurrency implements Comparable<CryptoCurrency> {
    public String Name;
    public float Price;

    public CryptoCurrency(String name, float price) {
        Name = name;
        Price = price;
    }

    public String PriceString()
    {
        return String.format(Locale.getDefault(), "$%.1f", Price);
    }

    @Override
    public int compareTo(CryptoCurrency other) {
        return (int) (other.Price - Price);
    }
}
