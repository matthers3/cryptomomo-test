package com.matthers3.cryptomomo_test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.ListView;

import com.matthers3.cryptomomo_test.adapters.RankingAdapter;
import com.matthers3.cryptomomo_test.models.CryptoCurrency;
import com.matthers3.cryptomomo_test.view_models.CryptoRanking;

public class MainActivity extends AppCompatActivity {

    CryptoRanking ranking;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupObserver();
    }

    private void setupObserver()
    {
        ranking = new CryptoRanking();

        Observer<CryptoCurrency[]> observer = data -> {
           listView = (ListView) findViewById(R.id.rankingView);
           RankingAdapter adapter = new RankingAdapter(data,
                   getApplicationContext());
           listView.setAdapter(adapter);
        };

        ranking.getRanking().observe(this, observer);
    }

}
