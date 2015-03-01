package test.jun.touchtouch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by user on 15. 3. 1.
 */
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

        Toast.makeText(this, "비스 가동3", Toast.LENGTH_LONG).show();
        stopService(new Intent(this, TopService.class));

        return START_REDELIVER_INTENT;
    }
}
