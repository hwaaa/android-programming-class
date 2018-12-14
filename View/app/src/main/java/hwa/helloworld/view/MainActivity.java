package hwa.helloworld.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // 텍스트뷰의 객체를 만든다
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 레이아웃의 뷰에서 tvName을 객체로 만든다
        tvName = (TextView)findViewById(R.id.tvName);

        //tvName.getText(); // 레이아웃에 적혀있는 글자를 가져옴
        //tvName.setText("안드로이드~~~"); // 텍스트를 설정해줌

        String message = tvName.getText().toString();
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
