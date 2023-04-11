package com.matthers3.cryptomomo_test.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.matthers3.cryptomomo_test.R;
import com.matthers3.cryptomomo_test.models.CryptoCurrency;

public class RankingAdapter extends ListAdapter<CryptoCurrency> {

    public RankingAdapter(CryptoCurrency[] currencies, Context ctx) {
        super(currencies, ctx);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null)
        {
            view = inflater.inflate(R.layout.currency_list_view, viewGroup, false);
        }

        TextView name = (TextView) view.findViewById(R.id.coin_name);
        TextView price = (TextView) view.findViewById(R.id.coin_price);
        name.setText(Items[i].name);
        price.setText(Items[i].PriceString());
        return view;
    }
}
