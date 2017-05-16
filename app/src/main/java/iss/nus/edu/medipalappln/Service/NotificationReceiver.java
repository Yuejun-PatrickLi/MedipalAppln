package iss.nus.edu.medipalappln.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

import iss.nus.edu.medipalappln.R;
import iss.nus.edu.medipalappln.activity.AddMedicineActivity;

public class NotificationReceiver extends BroadcastReceiver {
    public NotificationReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar c = Calendar.getInstance();
        int notificationID = intent.getExtras().getInt("ID");
        String time = intent.getExtras().getString("TIME");
        String message = intent.getExtras().getString("MESSAGE");

        Log.i("Receiver ", " >> " + notificationID);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_doctor_icon);
        mBuilder.setContentTitle("Medicine Reminder: " + time);
        mBuilder.setContentText(message);

        Intent notificationIntent = new Intent(context, AddMedicineActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(contentIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID, mBuilder.build());
        mBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;
    }
}