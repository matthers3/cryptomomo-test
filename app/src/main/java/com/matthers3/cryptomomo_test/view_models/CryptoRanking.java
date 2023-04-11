package com.matthers3.cryptomomo_test.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.matthers3.cryptomomo_test.models.CryptoCurrency;
import com.matthers3.cryptomomo_test.utils.HttpRequest;
import com.matthers3.cryptomomo_test.utils.URIParameter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("ConstantConditions")
public class CryptoRanking extends ViewModel {
    final private MutableLiveData<CryptoCurrency[]> currencies;

    public CryptoRanking() {
        currencies = new MutableLiveData<>();
        setupRanking();
    }

    private void setupRanking()
    {
        CompletableFuture.runAsync(() -> {
            CryptoCurrency[] views = APICall();
            Arrays.sort(views);
            currencies.postValue(views);
        });
    }

    public LiveData<CryptoCurrency[]> getRanking() {
        return currencies;
    }

    public CryptoCurrency[] APICall() {
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
        List<URIParameter> params = new ArrayList<>();
        params.add(new URIParameter("limit", "10"));

        JSONObject response = HttpRequest.Request("GET", url, params);
        JSONArray arr = (JSONArray) Objects.requireNonNull(response).get("data");
        int size = arr != null ? arr.size() : 0;

        CryptoCurrency[] currencies = new CryptoCurrency[size];
        for (int i = 0; i < size; i++) {
            JSONObject jsonCurrency = (JSONObject) arr.get(i);
            String name = (String) jsonCurrency.get("name");
            JSONObject quoteData = (JSONObject) jsonCurrency.get("quote");
            JSONObject priceData = (JSONObject) quoteData.get("USD");
            float price = (float) ((double) priceData.get("price"));
            currencies[i] = new CryptoCurrency(name, price);
        }

        return currencies;
    }

}
