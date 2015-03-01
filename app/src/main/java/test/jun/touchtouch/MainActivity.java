package test.jun.touchtouch;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends Activity implements View.OnTouchListener {

    static List<PackageInfo> packs;
    private String[] info_PI;
    private int[] position = {0,0,0,0};

    private HashMap<String, String> Pk_Info = new HashMap<>();

    private int select_index;

    // 버튼 옆의 화면 (실행 시킬 앱 정)
    private TextView text_1_2;
    private TextView text_1_3;
    private TextView text_2_2;
    private TextView text_2_3;
    private TextView text_3_2;
    private TextView text_3_3;

    //셋팅 파일 위치
    String dirPath;


    private String[] PK_N = new String[]{"com.kakao.talk", "kr.co.vcnc.android.couple", "vStudio.Android.Camera360", "null", "null", "null"};
    private TextView[] text_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        stopService(new Intent(this, TopService.class));

        setContentView(R.layout.sub_main);


        //다시 실행 할때(1번 클릭했을때) 탑 서비스 지우기.

        //------------------------------------------------------------------------------------------

        //설정 저장 장소
        dirPath = getFilesDir().getAbsolutePath();

        // 어플 리스트 정보 받아오기.
        set_P();

        //------------------------------------------------------------------------------------------


        setContentView(R.layout.activity_main);

        text_1_2 = (TextView) findViewById(R.id.text_1_2);
        text_1_3 = (TextView) findViewById(R.id.text_1_3);
        text_2_2 = (TextView) findViewById(R.id.text_2_2);
        text_2_3 = (TextView) findViewById(R.id.text_2_3);
        text_3_3 = (TextView) findViewById(R.id.text_3_3);
        text_3_2 = (TextView) findViewById(R.id.text_3_2);

        text_List = new TextView[]{text_1_2, text_1_3, text_2_2, text_2_3, text_3_2, text_3_3};

//        position =

        //데이터 읽어오
        read_Option();

    }

    public boolean onTouch(View view, MotionEvent event) {

        // XML 코드로 설정한 뷰의 ID가 id_my_view 인 경우
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 사용자가 액티비티 영역을 눌렀을 때 처리할 루틴을 정의한다.
            return false; // 이 이벤트에 대한 처리를 완료하였다.
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // 사용자가 액티비티 영역에서 움직였을 때 처리할 루틴을 정의한다.
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // 사용자가 액티비티 영역에서 눌렀던 것을 떼었을 때 처리할 루틴을 정의한다.
            return true;
        }
        // 누르고, 움직이고, 떼는 행위 외의 이벤트는 이 핸들러에서 처리하지 않았음을 알린다.
        return false;
    }

    public void onClick_Start(View view) {

        //데이터 쓰기
        write_Option();

        Set_Noti_On();

        Intent intent = new Intent(this, TopService.class);
        intent.putExtra("info", PK_N);
        intent.putExtra("position", PK_N);
        startService(intent);

        finish();
    }

    public void onClick_end(View view) {
        stopService(new Intent(this, TopService.class));
    }

    protected void makeDialog(final int i) {

       final PackageListDialog dl = new PackageListDialog(this, info_PI);

        dl.show();

        dl.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
           public void onDismiss(DialogInterface dialog) {

               if (0 <= i || i <= 5) {
                   PK_N[i] = Pk_Info.get(dl.getPosition());
                   text_List[i].setText(dl.getPosition());
               }
           }
       });


    }

    private void set_P() {
        //  설치되어 있는 어플 정보 받아오기
        packs = getPackageManager().getInstalledPackages(PackageManager.GET_SERVICES);

        //  배열 사이즈 = 어플정보 사이즈
        info_PI = new String[packs.size()];

        //  여기서 부터는 알림창의 속성 설정
        PackageManager pm = this.getPackageManager();

        //어플리케이션 정보 String 배열 넣기
        for (int i = 0; i < packs.size(); i++) {
            Log.i(packs.get(i).applicationInfo.loadLabel(pm).toString(), packs.get(i).packageName);

            info_PI[i] = packs.get(i).applicationInfo.loadLabel(pm).toString();                             //패키지 리스트 정보
            Pk_Info.put(packs.get(i).applicationInfo.loadLabel(pm).toString(),packs.get(i).packageName);    //맵
        }

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
                            else if (str.equals("PK_SN.txt")){
                                for(int i = 0 ; i < text_List.length ; i++) {
                                    temp = bufferReader.readLine();
                                    text_List[i].setText(temp);
                                }
                            }

                        Log.e(null, "" + content);

                        fis.close();
                        bufferReader.close();

                    } catch (Exception e) {
                }
            }
    }
    //데이터 쓰기
    private void write_Option(){

        // txt 파일 생성
        String pk_Name = "";
        String pk_Show_Name = "";
        for(int i = 0 ; i< PK_N.length ; i++) {
             pk_Name += PK_N[i] + "\n";
        }
        for(int i = 0 ; i < text_List.length ; i++){
            pk_Show_Name += text_List[i].getText() + "\n";
        }
        //경로 이름
        File savefile = new File(dirPath+"/PK_N.txt");

        //보여지는 파일 이름
        File savefile2 = new File(dirPath+"/PK_SN.txt");

        try{
            FileOutputStream fos = new FileOutputStream(savefile);
            FileOutputStream fos2 = new FileOutputStream(savefile2);

            fos.write(pk_Name.getBytes());
            fos2.write(pk_Show_Name.getBytes());

            fos.close();
            fos2.close();

            Toast.makeText(this, "Save Success", Toast.LENGTH_SHORT).show();
        } catch(IOException e){
            Log.e(" 파일에러1 ","파일을 생성하지 못하였습니다.");
        }
    }
    //버튼 클릭
    public void onClick_1_2(View view) {
        makeDialog(0);
    }
    public void onClick_1_3(View view) {
        makeDialog(1);
    }
    public void onClick_2_2(View view) {
        makeDialog(2);
    }
    public void onClick_2_3(View view) {
        makeDialog(3);
    }
    public void onClick_3_2(View view) {
        makeDialog(4);
    }
    public void onClick_3_3(View view) {
        makeDialog(5);
    }

    //초기화버튼
    public void onClock_Clear(View view) {

        for(int i = 0 ; i < PK_N.length; i++) {
            PK_N[i] = "null";
        }
        for(int i = 0 ; i < text_List.length ; i++) {
            text_List[i].setText(" 설정이 되어 있지 않습니다. ");
        }
    }

    private void Set_Noti_On(){
        //알림 객체 이것을 통해 서비스를 on off 할수있다.
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       // PendingIntent pending_Intent = PendingIntent.getService(this, 0 , new Intent(this, On_Noti_Activity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pending_Intent = PendingIntent.getService(this, 0, new Intent(this, Nofi_Off.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        mBuilder.setTicker("Notification.Builder");
        mBuilder.setWhen(System.currentTimeMillis());
        mBuilder.setNumber(10);
        mBuilder.setContentTitle("Notification.Builder Title");
        mBuilder.setContentText("Notification.Builder Massage");
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        mBuilder.setContentIntent(pending_Intent);
        mBuilder.setAutoCancel(false);

        mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);

        nm.notify(111, mBuilder.build());
    }
}




