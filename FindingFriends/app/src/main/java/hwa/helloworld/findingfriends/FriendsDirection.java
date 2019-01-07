package hwa.helloworld.findingfriends;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.MapFragment;

/**
 * Created by student on 2019-01-07.
 */

public class FriendsDirection extends Activity {
    Button btn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_direction);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name", "mike");


                setResult(RESULT_OK, resultIntent); //이 메소드 호출 시 응답보냄
                finish(); //이 메소드 호출 시 액티비티 종료
            }
        });
    }
}
