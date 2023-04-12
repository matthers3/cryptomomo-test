package com.matthers3.cryptomomo_test.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bitcoin_price")
public class BitcoinPrice {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "selected_price")
    public float selectedPrice;
    @ColumnInfo(name = "user_notified")
    public boolean userNotified;

    public BitcoinPrice() {
        id = 0;
        selectedPrice = 0;
        userNotified = false;
    }
}
