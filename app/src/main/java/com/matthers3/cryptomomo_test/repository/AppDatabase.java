package com.matthers3.cryptomomo_test.repository;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.matthers3.cryptomomo_test.models.BitcoinPrice;

@Database(entities = {BitcoinPrice.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BitcoinPriceDao bitcoinPriceDao();
}
