package test.jun.touchtouch;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 15. 2. 15.
 */

public class TopService extends Service implements View.OnTouchListener{

    private TextView mPopupView;                            //항상 보이게 할 뷰
    private WindowManager mWindowManager;          //윈도우 매니저
    private int one=0;
    private int Two=0;
    private int Three=0;

    private String[] PK_N =  new String[]{"null","null","null","null","null","null"};


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        mPopupView = new TextView(this);                                         //뷰 생성
        mPopupView.setText("     ");                        //텍스트 설정
        mPopupView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60); //텍스트 크기 18sp
        mPopupView.setTextColor(Color.BLUE);                                  //글자 색상
        mPopupView.setBackgroundColor(Color.argb(127, 0, 255, 255)); //텍스트뷰 배경 색

       mPopupView.setOnTouchListener(this);              //팝업뷰에 터치 리스너 등록


        //최상위 윈도우에 넣기 위한 설정 TYPE_SYSTEM_ALERT
        WindowManager.LayoutParams mParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams. TYPE_PHONE ,   //항상 최 상위에 있게
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        |WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        |WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        |WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                PixelFormat.TRANSLUCENT);                                                          //투명
        mParams.gravity = Gravity.LEFT | Gravity.TOP;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);  //윈도우 매니저
        mWindowManager.addView(mPopupView, mParams);      //윈도우에 뷰 넣기. permission 필요.
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if(mPopupView != null){
            ((WindowManager) getSystemService(WINDOW_SERVICE)).removeView(mPopupView);
            mPopupView = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        PK_N = intent.getStringArrayExtra("info");
        return START_REDELIVER_INTENT;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // XML 코드로 설정한 뷰의 ID가 id_my_view 인 경우

        int action = event.getAction();

        float x = event.getX();
        float y = event.getY();

        if(event.getAction()==0){
            one=0;
            Two=0;
            Three=0;
        }

        if(event.getAction()!=2)
            Log.v("Sevice, View onTouchEvent", "action: " + action + ", x:" + x + ", y:" + y);
        if (event.getAction()==773)
            Three++;
        if (event.getAction()==517)
            Two++;
        if (event.getAction()==261)
            one++;

        if(event.getAction() == 1) {
            Log.v("Three Two ", Three + ", " + Two + ", " + one);
            enter_App();
        }

        return false;

    }

    public void enter_App(){

        String name;
        if(Three > 3 || Two > 3 || one > 3) return;

        if( Three > 1){
            name = PK_N[Three+2];
        }
        else if(Two > 1){
            name = PK_N[Two];
        }
        else if(one > 1){
            name = PK_N[one-2];
        }
        else{
            Intent main = new Intent(this, MainActivity.class);
            Toast.makeText(this, "한번만 클릭 하셨습니다.", Toast.LENGTH_LONG).show();
            startActivity(getPackageManager().getLaunchIntentForPackage("test.jun.touchtouch"));

            return;
        }

        if(name.equals("null")) {
            Toast.makeText(this, "설정이 되어있지 않습니다.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = getPackageManager().getLaunchIntentForPackage(name);
        startActivity(intent);
    }


}
