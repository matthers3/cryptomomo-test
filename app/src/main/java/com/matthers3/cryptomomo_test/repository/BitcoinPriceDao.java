package com.matthers3.cryptomomo_test.repository;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.matthers3.cryptomomo_test.models.BitcoinPrice;

@Dao
public interface BitcoinPriceDao {
    @Query("SELECT * FROM bitcoin_price WHERE bitcoin_price.id = 0")
    BitcoinPrice getPrice();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPrice(BitcoinPrice price);
}
