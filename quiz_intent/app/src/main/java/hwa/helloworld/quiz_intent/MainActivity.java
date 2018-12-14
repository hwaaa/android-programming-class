package hwa.helloworld.quiz_intent;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edt_tel;
    Button btn_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_tel = (EditText)findViewById(R.id.edt_tel);
        btn_call = (Button)findViewById(R.id.btn_call);

        /*IntentBtnListener intentBtnListener = new IntentBtnListener();

        btn_call.setOnClickListener(intentBtnListener);*/

        btn_call.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = null;
                String pnum = edt_tel.getText().toString();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + pnum));
                startActivity(intent);
            }
        });
    }

    /*class IntentBtnListener implements View.OnClickListener {
        Intent intent = null;

        String pnum = edt_tel.getText().toString();

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_call:
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + pnum));
                    break;
            }
        }
    }*/
}
