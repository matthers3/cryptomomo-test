package com.matthers3.cryptomomo_test.view_models;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.matthers3.cryptomomo_test.MainActivity;
import com.matthers3.cryptomomo_test.models.BitcoinPrice;
import com.matthers3.cryptomomo_test.repository.AppDatabase;
import com.matthers3.cryptomomo_test.repository.BitcoinPriceDao;

import java.util.concurrent.CompletableFuture;

public class BitcoinPriceViewModel extends ViewModel {

    AppDatabase db;
    String dbName = "app-database";
    BitcoinPrice bitcoinPrice;
    MutableLiveData<Float> currentPriceSelection;

    public BitcoinPriceViewModel(Context ctx) {
        currentPriceSelection = new MutableLiveData<>();
        loadDB(ctx);
    }

    public LiveData<Float> getPrice() {
        return currentPriceSelection;
    }

    private void loadDB(Context ctx) {
        CompletableFuture.runAsync(() -> {
            db = Room.databaseBuilder(ctx, AppDatabase.class, dbName).build();
            BitcoinPriceDao bitcoinPriceDao = db.bitcoinPriceDao();
            BitcoinPrice storedPrice = bitcoinPriceDao.getPrice();
            if (storedPrice == null) {
                BitcoinPrice newPrice = new BitcoinPrice();
                bitcoinPriceDao.insertPrice(newPrice);
                bitcoinPrice = newPrice;
            } else {
                bitcoinPrice = storedPrice;
            }
            currentPriceSelection.postValue(bitcoinPrice.selectedPrice);
        });
    }

    public void updatePrice(String price) {
        CompletableFuture.runAsync(() -> {
            try {
                float numericPrice = Float.parseFloat(price.replace(",", "."));
                bitcoinPrice.selectedPrice = numericPrice;
                if (currentPriceSelection.getValue() != null &&
                        currentPriceSelection.getValue() != numericPrice) {
                    currentPriceSelection.postValue(bitcoinPrice.selectedPrice);
                    BitcoinPriceDao bitcoinPriceDao = db.bitcoinPriceDao();
                    bitcoinPriceDao.insertPrice(bitcoinPrice);
                }
            } catch (Exception e) {
                Log.e("[Error]", e.getMessage());
            }
        });
    }
}
