package grey.smarthouse.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import grey.smarthouse.R;
import grey.smarthouse.model.RelayList;
import grey.smarthouse.retrofit.Requests;
import grey.smarthouse.ui.activities.MainActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class NetService extends Service {

    private Requests mRequests;
    public NotificationManager nm;
    static List<String> mTemp;
    static List<Float> lastTemp = new ArrayList<Float>();
    Observable<Long> netRequest;
    static final int alarmTemp = 27;
    static final String CHANNEL_ID = "ch";
    static int notifCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        netRequest = Observable.interval(5, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        netRequest.subscribe(x -> nextHandler(),
                e -> e.printStackTrace(),
                () -> Log.d("RX", "onC: "),
                d -> Log.d("RX", "sub"));
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My channel",
                    nm.IMPORTANCE_HIGH);
            channel.setDescription("My channel description");
            nm.createNotificationChannel(channel);
        }
        nextHandler();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }







    public static List<String> getTemp() {
        return mTemp;
    }

    void sendNotif(int i) {

        Intent resultIntent = new Intent(this, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setTicker(getResources().getString(R.string.notificationTicker))
                        .setSmallIcon(R.drawable.ic_whatshot_black_24dp)
                        .setContentTitle(getResources().getString(R.string.notificationTicker))
                        .setContentText(String.format(getResources().getString(R.string.notificationTemp), i + 1, mTemp.get(i)))
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                        .setNumber(notifCount++);

        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(i, notification);
    }

    private void nextHandler() {
        mTemp = mRequests.ds18b20Request();
        mRequests.relayStateRequest();
        try {
            for (int i = 0; i < mTemp.size(); i++) {
                float t = Float.parseFloat(mTemp.get(i));
                if (lastTemp.size() < mTemp.size()) {
                    lastTemp.add(t);
                }
                if (t >= alarmTemp && lastTemp.get(i) < alarmTemp) {
                    sendNotif(i);
                }
                lastTemp.set(i, t);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }
}
