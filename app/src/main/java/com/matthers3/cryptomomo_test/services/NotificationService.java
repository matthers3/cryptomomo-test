package com.matthers3.cryptomomo_test.services;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.matthers3.cryptomomo_test.R;
import com.matthers3.cryptomomo_test.models.BitcoinPrice;
import com.matthers3.cryptomomo_test.repository.AppDatabase;
import com.matthers3.cryptomomo_test.repository.BitcoinPriceDao;
import com.matthers3.cryptomomo_test.utils.HttpRequest;
import com.matthers3.cryptomomo_test.utils.URIParameter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class NotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CompletableFuture.runAsync(() -> {
            String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest";
            List<URIParameter> params = new ArrayList<>();
            params.add(new URIParameter("limit", "1"));
            JSONObject response = HttpRequest.Request("GET", url, params);
            JSONArray data = (JSONArray) response.get("data");
            JSONObject bitcoin = (JSONObject) data.get(0);
            JSONObject quote = (JSONObject) bitcoin.get("quote");
            JSONObject usd = (JSONObject) quote.get("USD");
            float price = (float) ((double) usd.get("price"));

            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "app-database").build();
            BitcoinPriceDao bitcoinPriceDao = db.bitcoinPriceDao();
            BitcoinPrice storedPrice = bitcoinPriceDao.getPrice();

            if (storedPrice == null) {
                return;
            }

            if (storedPrice.selectedPrice > price)
            {
                return;
            }

            String newString = String.format(Locale.getDefault(), "%.1f", price);
            String storedString = String.format(Locale.getDefault(), "%.1f", storedPrice.selectedPrice);

            try {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(
                        getApplicationContext(), "1")
                        .setSmallIcon(R.drawable.panda)
                        .setContentTitle("My notification")
                        .setContentText("La BitCoin esta en: " + newString + ", superando los: "
                        + storedString + ".")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    CharSequence name = "CryptoMomo";
                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                    NotificationChannel channel = new NotificationChannel("1", name, importance);
                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                    notificationManagerCompat.notify(1, builder.build());
                }
            } catch (Exception e) {
                Log.e("ERROR: ", e.getMessage());
            }

        });
        return START_NOT_STICKY;
    }

    public static void setupNotifications(Activity activity) {
        Intent intent = new Intent(activity, NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                activity, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) activity.getSystemService(ALARM_SERVICE);
        int interval = 5 * 60 * 1000;
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + interval, interval, pendingIntent);
    }

}
