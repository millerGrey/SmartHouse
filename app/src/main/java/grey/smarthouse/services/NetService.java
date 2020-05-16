
package grey.smarthouse.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import grey.smarthouse.R;
import grey.smarthouse.model.App;
import grey.smarthouse.model.SensorList;

import grey.smarthouse.retrofit.Requests;
import grey.smarthouse.retrofit.SmartHouseApi;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;



public class NetService extends Service {
    private static Boolean isNotif = false;
    private static int notifTemp = 0;
    public NotificationManager nm;
    static List<Float> lastTemp = new ArrayList<Float>();
    Observable<Long> netRequest;
    static final String CHANNEL_ID = "ch";
    static int notifCount = 0;
    static private SmartHouseApi smartHouseApi;
    static private SensorList mTemp = new SensorList();

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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("SH", "Service startCommand");
//        try{
        isNotif = App.Companion.getApp().getMIsNotifOn();
        notifTemp = App.Companion.getApp().getMNotifTemp();
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        Log.d("SH","notif temp " + notifTemp);
        Requests.INSTANCE.retrofitInit(App.Companion.getApp().getMDeviceURL());
        smartHouseApi = Requests.INSTANCE.getApi();
        Log.d("SH","smartHouse " + smartHouseApi.toString());
        nextHandler();
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static List<String> getTemp() {
        return mTemp.getList();
    }

    void sendNotif(int i) {
        Intent resultIntent = new Intent(this, NetService.class);
        PendingIntent resultPendingIntent = PendingIntent.getService(this, 0, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setTicker(getResources().getString(R.string.notificationTicker))
                        .setSmallIcon(R.drawable.ic_whatshot_black_24dp)
                        .setContentTitle(getResources().getString(R.string.notificationTicker))
                        .setContentText(String.format(getResources().getString(R.string.notificationTemp), i + 1, mTemp.getList().get(i)))
                        .setContentIntent(resultPendingIntent)
                        .setAutoCancel(true)
                        .setNumber(notifCount++);

        Notification notification = builder.build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(i, notification);
    }

    private void nextHandler() {
        Requests.INSTANCE.ds18b20Request(mTemp);
        Requests.INSTANCE.relayStateRequest();
        List<String> list = mTemp.getList();
        if(isNotif==true) {
            try{
                for (int i = 0; i < list.size(); i++) {
                    float t = Float.parseFloat(list.get(i));
                    if (lastTemp.size() < list.size()) {
                        lastTemp.add(t);
                    }
                    if (t >= notifTemp && lastTemp.get(i) < notifTemp) {
                        sendNotif(i);
                    }
                    lastTemp.set(i, t);
                }
            }catch (NumberFormatException e) {
                e.printStackTrace();
            }catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i("SH", "Service: onTaskRemoved");
    }
}