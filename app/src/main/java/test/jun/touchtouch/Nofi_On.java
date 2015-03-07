package test.jun.touchtouch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by user on 15. 3. 1.
 */

//서비스를 다시 사용
public class Nofi_On extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        //서비스를 다시사용
        //Toast.makeText(this, "서비스 사용", Toast.LENGTH_LONG).show();
        startService(new Intent(this, TopService.class));

        Set_Noti_Off();

        return START_REDELIVER_INTENT;
    }

    //서비스를 끌수있는 알림
    private void Set_Noti_Off(){
        //알림 객체 이것을 통해 서비스를 on off 할수있다.
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // PendingIntent pending_Intent = PendingIntent.getService(this, 0 , new Intent(this, On_Noti_Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pending_Intent = PendingIntent.getService(this, 0, new Intent(this, Nofi_Off.class), PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_on);
        mBuilder.setTicker("서비스를 사용합니다.");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setContentTitle("서비스 작동중");
        mBuilder.setContentText("서비스를 잠시 중시하려면 터치!");
        //mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pending_Intent);
        mBuilder.setOngoing(true);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        nm.notify(1, mBuilder.build());
    }
}