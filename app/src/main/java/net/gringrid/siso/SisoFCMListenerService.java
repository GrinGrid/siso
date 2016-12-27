package net.gringrid.siso;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import net.gringrid.siso.fragments.PushListFragment;
import net.gringrid.siso.fragments.SitterDetailFragment;
import net.gringrid.siso.models.User;
import net.gringrid.siso.util.SharedData;

import java.util.Map;

/**
    송신자	      수신자	      발송시점	      받은 후 이동화면
 부모 / 시터	부모 / 시터	    연락처 요청시     연락처 요청 받은 리스트 or 송신자 상세화면으로 이동
 부모 / 시터	부모 / 시터	    연락처 수락시	  연락처 요청 한 리스트 or 송신자 상세화면으로 이동
 부모 / 시터	부모 / 시터	    연락처 거부시	  연락처 요청 한 리스트 or 송신자 상세화면으로 이동
 관리자         시터	        승인완료	      승인 안내 화면
 관리자         시터	        승인거부	      거부 안내 화면
 시터           관리자	        승인요청	      N/A
 부모 / 시터    부모 / 시터	    관심등록	      필요여부판단

    SENDER|#$%|PUSH_TYPE|#$%|CONTENT
    ex) nisclan@hotmail.com|#$%|10|#$%|안녕하세요 홍길순 시터 입니다 블라블라
 */
public class SisoFCMListenerService extends FirebaseMessagingService{

    private static final String TAG = "jiho";
    private final String DELIMITER = "|#$%|";

    private final int MSG_INDEX_SENDER = 0;
    private final int MSG_INDEX_PUSH_TYPE = 1;
    private final int MSG_INDEX_CONTENT = 2;

    private User mUser;
    private String mUserType;

    public SisoFCMListenerService() {
        mUser = SharedData.getInstance(this).getUserData();
        mUserType = mUser.personalInfo.user_type;
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO: 16. 12. 9. NOTIFICATION
        // move push list
        String from = remoteMessage.getFrom();
        Map data = remoteMessage.getData();
        String dataStr = (String)data.get("message");
        String dataAray[] = dataStr.split(DELIMITER);
        String sender = dataAray[MSG_INDEX_SENDER];
        String content = dataAray[MSG_INDEX_CONTENT];

        Log.d(TAG, "====================================================");
        Log.d(TAG, "====================================================");
        Log.d(TAG, "====================================================");
        Log.d(TAG, "====================================================");
        Log.d(TAG, "====================================================");
        Log.d(TAG, "onMessageReceived: from : "+from);
        if (remoteMessage.getData().size() > 0){
            Log.d(TAG, "onMessageReceived: getData size > 0 : "+remoteMessage.getData());
            Log.d(TAG, "onMessageReceived: message : "+data.get("message"));
            executePushNotification(remoteMessage.getData().toString());

        }

        if (remoteMessage.getNotification() != null){
            Log.d(TAG, "onMessageReceived: notifi body : "+remoteMessage.getNotification().getBody());
        }

        // TODO PUSH 터치 했을 경우 push list 화면으로 이동
        //movePushList();
        super.onMessageReceived(remoteMessage);
    }


    private void executePushNotification(String msg){
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_join_add)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setContentTitle("SISO PUSH")
                .setSound(defaultSoundUri)
                .setContentText(msg);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

    private void movePushList() {
        PushListFragment fragment = new PushListFragment();
        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra(BaseActivity.MENU, BaseActivity.MENU_PUSH_LIST);
        startActivity(intent);
    }
}
