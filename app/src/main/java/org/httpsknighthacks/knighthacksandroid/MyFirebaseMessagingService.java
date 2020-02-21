package org.httpsknighthacks.knighthacksandroid;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static android.app.Notification.DEFAULT_SOUND;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "KnightHacksIsTheBest";
    public static final String TOKEN_BROADCAST = "GOKnights";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent fullScreenIntent = new Intent(this, MainActivity.class);
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(this, 0,
                fullScreenIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        super.onMessageReceived(remoteMessage);

        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, "GENERAL")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.rover)
                .setFullScreenIntent(fullScreenPendingIntent, true)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(1, notification.build());

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    @Override
    public void onNewToken(String token)
    {
        Log.d("myFirebaseId", "Refreshed token: " + token);

        getApplicationContext().sendBroadcast(new Intent(TOKEN_BROADCAST));

        // sendRegistrationToServer(token);
    }
}