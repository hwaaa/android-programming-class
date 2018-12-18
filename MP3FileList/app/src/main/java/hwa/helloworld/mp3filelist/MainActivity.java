package hwa.helloworld.mp3filelist;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    boolean bReadPerm = false;
    boolean bWritePerm = false;
    Button button_play, button_stop, button_prev, button_next;
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPermission();

        button_play = (Button)findViewById(R.id.button_play);
        button_stop = (Button)findViewById(R.id.button_stop);
        button_prev = (Button)findViewById(R.id.button_prev);
        button_next = (Button)findViewById(R.id.button_next);
        button_play.setOnClickListener(new MyButtonListener());
        button_stop.setOnClickListener(new MyButtonListener());
        button_prev.setOnClickListener(new MyButtonListener());
        button_next.setOnClickListener(new MyButtonListener());

        player = new MediaPlayer();

        String[] mp3list = {"4oclock.mp3", "iknow.mp3", "sofaraway.mp3", "wdtapt2.mp3"};

        if(bReadPerm && bWritePerm) {
            String state = Environment.getExternalStorageState();

            if(state.equals(Environment.MEDIA_MOUNTED)) {
                try {
                    String musicPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/4oclock.mp3";
                    player.setDataSource(musicPath);
                    player.prepare();
                    Log.d("PlayMP3", "mp3 file");
                } catch (Exception e) {
                    Log.d("PlayMP3", "mp3 file error");
                }
            }
        }
    }

    private void setPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            bReadPerm = true;
        }

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            bWritePerm = true;
        }

        if(!bReadPerm && !bWritePerm) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,}, 200);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 200 && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bReadPerm = true;
            }
            if(grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                bWritePerm = true;
            }
        }
    }

    class MyButtonListener implements View.OnClickListener {
        String[] mp3list = {"4oclock.mp3", "iknow.mp3", "sofaraway.mp3", "wdtapt2.mp3"};
        int cnt = 0;
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.button_play:
                    if(player.isPlaying()) {
                        player.pause();
                        button_play.setText("play");
                    } else {
                        player.start();
                        button_play.setText("pause");
                    }
                    break;
                case R.id.button_stop:
                    player.stop();
                    try {
                        player.prepare();
                    } catch (Exception e) {
                        Log.d("PlayMP3", "mp3 file error");
                    }
                    break;
                case R.id.button_prev:
                    try {
                        cnt--;
                        if(cnt<0) {
                            cnt = 4;
                        }
                        player.stop();
                        player.reset();
                        String musicPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mp3list[cnt].toString();
                        player.setDataSource(musicPath);
                        player.prepare();
                        player.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("PlayMP3", "mp3 list error");
                    }

                case R.id.button_next:
                    try {
                        cnt++;
                        if(cnt>4) {
                            cnt = 0;
                        }
                        player.stop();
                        player.reset();
                        String musicPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mp3list[cnt].toString();
                        player.setDataSource(musicPath);
                        player.prepare();
                        player.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("PlayMP3", "mp3 list error");
                    }
            }
        }
    }
}
