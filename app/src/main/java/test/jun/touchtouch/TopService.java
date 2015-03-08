package test.jun.touchtouch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by user on 15. 2. 15.
 */

public class TopService extends Service implements View.OnTouchListener{

    private TextView mPopupView;                            //항상 보이게 할 뷰
    private WindowManager mWindowManager;          //윈도우 매니저
    private WindowManager.LayoutParams mParams;
    private int one=0;
    private int Two=0;
    private int Three=0;

    private String dirPath;

    private String currentColor = "검정";
    private String moveBoolean = "고정";

    private String[] PK_N = new String[]{"null", "null", "null", "null", "null", "null", "null", "null", "null"};

    //뷰를 움직이게 하는 기능
    private float START_X, START_Y;
    private int PREV_X, PREV_Y;
    private int MAX_X = -1, MAX_Y = -1;

    //뷰를 움직이게 하는 기능
    private void setMaxPosition() {
        DisplayMetrics matrix = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(matrix);

        MAX_X = matrix.widthPixels - mPopupView.getWidth();
        MAX_Y = matrix.heightPixels - mPopupView.getHeight();
    }

    //뷰를 움직이게 하는 기능
    private void optimizePosition() {
        if(mParams.x > MAX_X) mParams.x = MAX_X;
        if(mParams.y > MAX_Y) mParams.y = MAX_Y;
        if(mParams.x < 0) mParams.x = 0;
        if(mParams.y < 0) mParams.y = 0;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        //색을 읽어야 되기 때문에
        dirPath = getFilesDir().getAbsolutePath();
        read_Option();

        color_gruop color = new color_gruop();
        color.setRgb(currentColor);

        mPopupView = new TextView(this);                                         //뷰 생성
        mPopupView.setText("     ");                        //텍스트 설정
        mPopupView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60); //텍스트 크기 18sp
        mPopupView.setTextColor(Color.BLUE);                                  //글자 색상
        mPopupView.setBackgroundColor(Color.argb(50, color.getR(), color.getG(), color.getB())); //텍스트뷰 배경 색

//        mPopupView.setBackgroundColor(Color.argb(50, 30, 30, 30)); //텍스트뷰 배경 색

        mPopupView.setOnTouchListener(this);              //팝업뷰에 터치 리스너 등록


        //최상위 윈도우에 넣기 위한 설정 TYPE_SYSTEM_ALERT
        mParams = new WindowManager.LayoutParams(
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
        //mPopupView.setOnTouchListener(mViewTouchListener);

        Toast.makeText(this, "서비스 가동", Toast.LENGTH_LONG).show();

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

        dirPath = getFilesDir().getAbsolutePath();
        read_Option();

        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
//                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
//                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        return START_REDELIVER_INTENT;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // XML 코드로 설정한 뷰의 ID가 id_my_view 인 경우

        int action = event.getAction();

        //뷰를 움직이게 하는 기능
        if(moveBoolean.equals("이동")) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (MAX_X == -1)
                        setMaxPosition();
                    START_X = event.getRawX();
                    START_Y = event.getRawY();
                    PREV_X = mParams.x;
                    PREV_Y = mParams.y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int x = (int) (event.getRawX() - START_X);
                    int y = (int) (event.getRawY() - START_Y);

                    mParams.x = PREV_X + x;
                    mParams.y = PREV_Y + y;

                    optimizePosition();
                    mWindowManager.updateViewLayout(mPopupView, mParams);
                    break;
            }
        }
       //////////////////////////////////////////////////////////////


        if(event.getAction()==0){
            one=0;
            Two=0;
            Three=0;
        }

        if(event.getAction()!=2)
            //Log.i("Sevice, View onTouchEvent", "action: " + action + ", x:" + x + ", y:" + y);
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


        return true;

    }

    public void enter_App(){

        String name;
        if(Three > 3 || Two > 3 || one > 3) {
                        Toast.makeText(this, "4번 이상 클릭 하셨습니다.", Toast.LENGTH_LONG).show();
            return;
        }

        //one 0,1,2 two 3,4,5 three 6,7,8
        if( Three >= 1){
            name = PK_N[Three+5];
        }
        else if(Two >= 1){
            name = PK_N[Two+2];
        }
        else if(one >= 1){
            name = PK_N[one-1];
        }
        else{
//            Toast.makeText(this, "한번만 클릭 하셨습니다.", Toast.LENGTH_LONG).show();
//            startActivity(getPackageManager().getLaunchIntentForPackage("test.jun.touchtouch"));

            return;
        }

        if(name.equals("null")) {
            Toast.makeText(this, "설정이 되어있지 않습니다.", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = getPackageManager().getLaunchIntentForPackage(name);
        startActivity(intent);
    }

    //데이터 읽어오기
    private void read_Option() {

        // 일치하는 폴더가 없으면 생성

        File file = new File(dirPath);

        if (!file.exists()) {
            file.mkdirs();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }

        // 파일이 1개 이상이면 파일 이름 출력
        if (file.listFiles().length > 0)
            for (File f : file.listFiles()) {
                String str = f.getName();
                Log.e(null, "fileName : " + str);

                // 파일 내용 읽어오기
                String loadPath = dirPath + "/" + str;
                try {
                    FileInputStream fis = new FileInputStream(loadPath);
                    BufferedReader bufferReader = new BufferedReader(new InputStreamReader(fis));

                    String content = "", temp = "";
                    if(str.equals("PK_N.txt")){
                        for(int i = 0 ; i < PK_N.length; i++) {
                            temp = bufferReader.readLine();
                            PK_N[i] = temp;
                        }
                    }
                    if (str.equals("colorName.txt")){
                        currentColor = bufferReader.readLine();
                    }
                    if (str.equals("moveBoolean.txt")){
                        moveBoolean = bufferReader.readLine();
                    }

                    Log.e(null, "" + content);

                    fis.close();
                    bufferReader.close();

                } catch (Exception e) {
                }
            }
    }

    class color_gruop{
        private int R,G,B;

        //빨강 노랑 민트 흰색 검정
        void setRgb(String color){
            switch (color){
                case "빨강":
                    R = 200;
                    G = 0;
                    B = 0;
                    break;
                case "노랑":
                    R = 250;
                    G = 250;
                    B = 30;
                    break;
                case "민트":
                    R = 0;
                    G = 230;
                    B = 230;
                    break;
                case "흰색":
                    R = 255;
                    G = 255;
                    B = 255;
                    break;
                case "검정":
                    R = 0;
                    G = 0;
                    B = 0;
                    break;
            }
        }

        public int getB() {
            return B;
        }

        public int getG() {
            return G;
        }

        public int getR() {
            return R;
        }

    };



}
