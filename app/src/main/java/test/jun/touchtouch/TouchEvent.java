package test.jun.touchtouch;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by user on 15. 2. 8.
 */
public class TouchEvent extends View{

    private int x;
    private int y;

    // 초기화
    public TouchEvent(Context context){
        super(context);
    }

    // XML 코드에서 이 클래스를 배치할 때 반드시 있어야 하는 생성자
    public TouchEvent(Context c, AttributeSet attrs)
    {
        // View 클래스에 생성자로 넘어온 인자들을 전달한다.
        super(c, attrs);
    }

    // XML 코드에서 이 클래스에 theme 를 사용할 때 반드시 있어야 하는 생성자
    public TouchEvent(Context c, AttributeSet attrs, int defStyle)
    {
        // View 클래스에 생성자로 넘어온 인자들을 전달한다.
        super(c, attrs, defStyle);
    }

    // 사용자정의 뷰의 출력 형식을 정의하는 오버라이딩된 메소드

    //MotionEvent 로 터치 인식
    public boolean onTouchEvent(MotionEvent event){

        super.onTouchEvent(event);
//        Toast.makeText( getContext(), "현재좌표 (x,y) ("+ x + ", " + y + ")" , Toast.LENGTH_LONG ).show();

       // Log.v("터치", "화면클릭");
        int action = event.getAction();

        float x = event.getX();
        float y = event.getY();
        Log.v("Sevice, View onTouchEvent", "action: " + action + ", x:" + x + ", y:" + y);



        return super.onTouchEvent(event);
    }

}
