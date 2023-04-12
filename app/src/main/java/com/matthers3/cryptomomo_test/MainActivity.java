package com.matthers3.cryptomomo_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.matthers3.cryptomomo_test.adapters.RankingAdapter;
import com.matthers3.cryptomomo_test.models.CryptoCurrency;
import com.matthers3.cryptomomo_test.services.NotificationService;
import com.matthers3.cryptomomo_test.view_models.BitcoinPriceViewModel;
import com.matthers3.cryptomomo_test.view_models.CryptoRanking;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    CryptoRanking ranking;
    BitcoinPriceViewModel priceViewModel;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setNotifications();
        setupObserver();
        setupListeners();
    }

    private void setNotifications() {
        NotificationService.setupNotifications(this);
    }

    private void setupObserver()
    {
        ranking = new CryptoRanking();
        priceViewModel = new BitcoinPriceViewModel(getApplicationContext());

        Observer<CryptoCurrency[]> observer = data -> {
           listView = findViewById(R.id.rankingView);
           RankingAdapter adapter = new RankingAdapter(data,
                   getApplicationContext());
           listView.setAdapter(adapter);
        };

        Observer<Float> priceObserver = data -> {
            EditText textInput = findViewById(R.id.bitcoin_price_number);
            textInput.setText(String.format(Locale.getDefault(), "%.1f", data));
        };

        ranking.getRanking().observe(this, observer);
        priceViewModel.getPrice().observe(this, priceObserver);
    }

    private void setupListeners() {
        Button button = findViewById(R.id.refresh_button);
        button.setOnClickListener(v -> ranking.setupRanking());
        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            EditText textInput = findViewById(R.id.bitcoin_price_number);
            priceViewModel.updatePrice(textInput.getText().toString());
            textInput.clearFocus();
        });
    }

}
