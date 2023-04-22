package com.example.c196;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
public class NotificationReceiver extends BroadcastReceiver {

    public static final String NOTIFICATION_FILE = "notificationFile";
    public static final String NOTIFICATION_ID = "nextNotificationID";

    @Override
    public void onReceive(Context context, Intent intent) {

        //Reception of the values sent by the previous intent
        Bundle extras = intent.getExtras();
        String type = extras.getString("type");
        if (type == null || type.isEmpty()) {
            type = "";
        }

        int id = extras.getInt("id", 0);
        String notificationTitle = extras.getString("title");
        String notificationText = extras.getString("text");
        int nextNotificationId = extras.getInt("nextNotificationId", getAndIncrementNextNotificationId(context));

        //Creating a notification builder with the Channel ID C196
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "C196")
                .setSmallIcon(R.drawable.bell)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText)
                .setAutoCancel(true);

        //Creating a notification manager that will use our notification builder to send the notifications
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(nextNotificationId, builder.build());


    }

    public static boolean scheduleNotification(Context context, int id, long date, String title, String text, String type, String notificationFile){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int nextNotificationId = getNextNotificationId(context);
        Intent notificationIntent = new Intent(context, NotificationReceiver.class);
        notificationIntent.putExtra("id", id);
        notificationIntent.putExtra("title", title);
        notificationIntent.putExtra("text", text);
        notificationIntent.putExtra("type", type);
        notificationIntent.putExtra("nextNotificationId", nextNotificationId);

        //Setting an alarm manager with the date parameter and the notification intent
        alarmManager.set(AlarmManager.RTC_WAKEUP, date, PendingIntent.getBroadcast(context, nextNotificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        //Updating the shared preferences value for the notification id
        SharedPreferences sharedPreferences = context.getSharedPreferences(notificationFile, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Integer.toString(id), nextNotificationId);
        editor.commit();
        incrementNextNotificationId(context);
        return true;
    }

    public static boolean scheduleCourseNotification(Context context, int courseId, long date, String notificationTitle, String text){
        Toast.makeText(context, "Course notification scheduled", Toast.LENGTH_LONG).show();
        return scheduleNotification(context, courseId, date, notificationTitle, text, "course", "courseNotifications");
    }

    public static boolean scheduleAssessmentNotification(Context context, int courseId, long date, String notificationTitle, String text){
        Toast.makeText(context, "Assessment notification scheduled", Toast.LENGTH_LONG).show();
        return scheduleNotification(context, courseId, date, notificationTitle, text, "assessment", "assessmentNotification");
    }

    private static int getNextNotificationId(Context context) {
        //Returns the notification id
        SharedPreferences notificationSharedPreferences;
        notificationSharedPreferences = context.getSharedPreferences(NOTIFICATION_FILE, Context.MODE_PRIVATE);
        int nextNotificationId = notificationSharedPreferences.getInt(NOTIFICATION_ID, 1);
        return nextNotificationId;
    }

    private static void incrementNextNotificationId(Context context) {
        //Increment the notification id
        SharedPreferences notificationSharedPreferences;
        notificationSharedPreferences = context.getSharedPreferences(NOTIFICATION_FILE, Context.MODE_PRIVATE);
        int nextNotificationId = notificationSharedPreferences.getInt(NOTIFICATION_ID, 1);
        SharedPreferences.Editor notificationEditor = notificationSharedPreferences.edit();
        notificationEditor.putInt(NOTIFICATION_ID, nextNotificationId + 1);
        notificationEditor.commit();
    }

    private static int getAndIncrementNextNotificationId(Context context) {
        //Increment the notification id and return it's value
        int nextNotificationId = getNextNotificationId(context);
        incrementNextNotificationId(context);
        return nextNotificationId;
    }
}
