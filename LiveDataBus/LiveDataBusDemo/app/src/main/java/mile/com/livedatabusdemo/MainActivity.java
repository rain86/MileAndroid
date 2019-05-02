package mile.com.livedatabusdemo;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView lipsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        lipsView = findViewById(R.id.lipsView);
        Button btView = findViewById(R.id.btView);
        Button jumpView = findViewById(R.id.jumpView);
        btView.setOnClickListener(this);
        jumpView.setOnClickListener(this);

        LiveDataBus.get().with("mile",HuaWei.class).observe(this, new Observer<HuaWei>() {
            @Override
            public void onChanged(@Nullable HuaWei huaWei) {
                if (huaWei != null){
                    Toast.makeText(MainActivity.this,huaWei.getName()+"",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btView:
                HuaWei huaWei = new HuaWei("华为","mete10");
                LiveDataBus.get().with("mile",HuaWei.class).postValue(huaWei);
                break;
            case R.id.jumpView:
                Intent intent = new Intent(this,SecActivity.class);
                startActivity(intent);
                break;
        }
    }
}
