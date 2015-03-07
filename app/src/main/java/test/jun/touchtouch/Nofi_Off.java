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

//서비스를 종
public class Nofi_Off extends Service {
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

        //서비스 사용을 멈춥니다.
        //Toast.makeText(this, "서비스 사용중지", Toast.LENGTH_LONG).show();
        stopService(new Intent(this, TopService.class));

        Set_Noti_On();


        return START_REDELIVER_INTENT;
    }

    //서비스를 다시 킬수있게하는 알림
    private void Set_Noti_On(){
        //알림 객체 이것을 통해 서비스를 on off 할수있다.
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        PendingIntent pending_Intent = PendingIntent.getService(this, 0, new Intent(this, Nofi_On.class), PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_off);
        mBuilder.setTicker("서비스를 중지합니다.");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setContentTitle("서비스가 정지 하였습니다.");
        mBuilder.setContentText("서비스를 다시 사용할려면 터치!");
        mBuilder.setDefaults(Notification.FLAG_NO_CLEAR);
        mBuilder.setContentIntent(pending_Intent);
        mBuilder.setOngoing(true);
        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        nm.notify(1, mBuilder.build());
    }
}
