package hwa.helloworld.findingfriends;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by student on 2019-01-09.
 */

public class NotiActivity extends AppCompatActivity {
    Button btn_vib;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        btn_vib = (Button) findViewById(R.id.btn_vib);
        btn_vib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                vib.vibrate(1500);  // 1.5초 동안 진동

                Toast.makeText(NotiActivity.this, "진동이 울렸습니다.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
