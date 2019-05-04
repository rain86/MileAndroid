package mile.com.hotstartdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.ref.WeakReference;

/**
 * Created by mile on 2019/5/3.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int START_OTHER_ACTIVITY = 100;
    private Handler handler = new MyHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Log.d("mile","onCreate");
        handler.sendEmptyMessageDelayed(START_OTHER_ACTIVITY,1500);

    }

    static class MyHandler extends Handler{
        WeakReference<SplashActivity> reference;

        public MyHandler(SplashActivity activity){
            reference = new WeakReference<SplashActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.d("mile","msg.what:"+msg.what);
            SplashActivity activity = reference.get();
            if (activity != null){
                switch (msg.what){
                    case START_OTHER_ACTIVITY:
                        Intent intent = new Intent(activity,MainActivity.class);
                        activity.startActivity(intent);
                        break;
                    default:

                        break;
                }
            }
        }
    }
}
