package iss.nus.edu.medipalappln.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.Welcome;
import iss.nus.edu.medipalappln.adapter.AppointmentAdapter;

/**
 * Created by cherry on 2017/3/23.
 */
public class RemindService extends Service {

    static Timer timer = null;

    public static void cleanAllNotification(Context context) {
        //NotificationManager mn = (NotificationManager) Welcome
        //        .getContext().getSystemService(NOTIFICATION_SERVICE);
        NotificationManager mn = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        mn.cancelAll();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


    public static void addNotification(int delayTime, String tickerText,
                                       String contentTitle, String contentText) {
        Intent intent = new Intent(Welcome.getContext(),RemindService.class);
        intent.putExtra("delayTime", delayTime);
        intent.putExtra("tickerText", tickerText);
        intent.putExtra("contentTitle", contentTitle);
        intent.putExtra("contentText", contentText);
        Welcome.getContext().startService(intent);
    }

    public void onCreate() {
        Log.e("addNotification", "===========create=======");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(final Intent intent, int flags, int startId) {

        long period = 10 * 12 * 30 * 24 * 60 * 60 * 1000;
        int delay = intent.getIntExtra("delayTime", 0);
        if (null == timer) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                NotificationManager mn = (NotificationManager) RemindService.this
                        .getSystemService(NOTIFICATION_SERVICE);
                Notification.Builder builder = new Notification.Builder(
                        RemindService.this);

                Intent notificationIntent = new Intent(RemindService.this,
                        Welcome.class);
                PendingIntent contentIntent = PendingIntent.getActivity(
                        RemindService.this, 0, notificationIntent, 0);
                builder.setContentIntent(contentIntent);
                builder.setSmallIcon(R.drawable.ic_appremind);
                builder.setTicker(intent.getStringExtra("tickerText"));
                builder.setContentText(intent.getStringExtra("contentText"));
                builder.setContentTitle(intent.getStringExtra("contentTitle"));
                builder.setVibrate(new long[] { 0, 2000, 1000, 4000 });
                builder.setDefaults(Notification.DEFAULT_ALL);
                Notification notification = builder.build();
                notification.flags = Notification.FLAG_INSISTENT | Notification.FLAG_AUTO_CANCEL;
                mn.notify((int)System.currentTimeMillis(), notification);
            }
        }, delay, period);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.e("addNotification", "===========destroy=======");
        super.onDestroy();
    }
}