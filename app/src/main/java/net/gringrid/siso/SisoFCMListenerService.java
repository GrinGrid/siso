package net.gringrid.siso;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class SisoFCMListenerService extends FirebaseMessagingService{

    private static final String TAG = "jiho";

    public SisoFCMListenerService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        String from = remoteMessage.getFrom();
        Map data = remoteMessage.getData();
        Log.d(TAG, "onMessageReceived: from : "+from);
        if (remoteMessage.getData().size() > 0){
            Log.d(TAG, "onMessageReceived: getData size > 0 : "+remoteMessage.getData());
            Log.d(TAG, "onMessageReceived: message : "+data.get("message"));
        }

        if (remoteMessage.getNotification() != null){
            Log.d(TAG, "onMessageReceived: notifi body : "+remoteMessage.getNotification().getBody());
        }


        super.onMessageReceived(remoteMessage);
    }
}
