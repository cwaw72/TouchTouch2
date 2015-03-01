package test.jun.touchtouch;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.widget.Toast;

/**
 * Created by user on 15. 3. 1.
 */
public class On_Noti_Activity extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "비스 가동3", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "비스 가동3", Toast.LENGTH_LONG).show();


    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Toast.makeText(this, "비스 가동3", Toast.LENGTH_LONG).show();
        stopService(new Intent(this, TopService.class));

        return START_REDELIVER_INTENT;
    }
}
