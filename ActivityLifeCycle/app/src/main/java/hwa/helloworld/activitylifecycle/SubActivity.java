package hwa.helloworld.activitylifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by student on 2018-12-17.
 */

public class SubActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activity_cycle", "SubActivity에서 onStart() 호출");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activity_cycle", "SubActivity에서 onStop() 호출");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity_cycle", "SubActivity에서 onDestroy() 호출");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activity_cycle", "SubActivity에서 onPause() 호출");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activity_cycle", "SubActivity에서 onResume() 호출");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("activity_cycle", "SubActivity에서 onRestart() 호출");
    }
}
