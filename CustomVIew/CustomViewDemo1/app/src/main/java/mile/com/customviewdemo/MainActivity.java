package mile.com.customviewdemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ExplosinField explosionField = new ExplosinField(this, new FallingParticleFactory());
        explosionField.setClickCallback(clickCallback);
        explosionField.addListener(findViewById(R.id.text));
        explosionField.addListener(findViewById(R.id.image));
        explosionField.addListener(findViewById(R.id.layout));
    }

    ClickCallback clickCallback = new ClickCallback() {
        @Override
        public void onClick(View v) {
            Log.e("NetEase",v.toString());
        }
    };
}
