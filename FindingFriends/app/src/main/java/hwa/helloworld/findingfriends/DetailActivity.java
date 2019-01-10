package hwa.helloworld.findingfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import org.w3c.dom.Text;

import static hwa.helloworld.findingfriends.MainActivity.REQUEST_CODE_ANOTHER;

/**
 * Created by student on 2019-01-08.
 */

public class DetailActivity extends Activity {
    TextView tv_subject, tv_detail, tv_latlng;
    Button btn_ok, btn_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);

        Intent intent = getIntent();
        intent.getStringExtra("now_latlng");

        tv_latlng = (TextView)findViewById(R.id.tv_latlng);
        tv_latlng.setText(intent.getStringExtra("now_latlng"));

        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(DetailActivity.this, "작성 ok", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == REQUEST_CODE_ANOTHER) {
            Toast toast = Toast.makeText(getBaseContext(), "onActivityResult() 메소드 호출됨.", Toast.LENGTH_LONG);
            toast.show();

            if(resultCode == RESULT_OK) {
                String name = intent.getExtras().getString("name");
                toast = Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}
