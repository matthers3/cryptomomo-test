package com.matthers3.cryptomomo_test.view_models;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.matthers3.cryptomomo_test.models.CryptoCurrency;

import java.util.Arrays;

public class CryptoRanking extends ViewModel {
    final private MutableLiveData<CryptoCurrency[]> currencies;

    public CryptoRanking() {
        currencies = new MutableLiveData<>();
        setupRanking();
    }

    private void setupRanking()
    {
        CryptoCurrency[] views = new CryptoCurrency[3];
        views[0] = new CryptoCurrency("BitCoin", 200f);
        views[1] = new CryptoCurrency("Ethereum", 100f);
        views[2] = new CryptoCurrency("DogeCoin", 300f);
        Arrays.sort(views);
        currencies.setValue(views);
    }

    public LiveData<CryptoCurrency[]> getRanking() {
        return currencies;
    }

}
