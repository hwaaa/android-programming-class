package hwa.helloworld.sdcardrw;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by student on 2018-12-17.
 */

public class ReadImgFileActivity extends Activity {

    ImageView iv_img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_img_file);

        iv_img = (ImageView) findViewById(R.id.iv_result);

        try {
            Bitmap bitMapImage = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myapp/myImage.png");
            iv_img.setImageBitmap(bitMapImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
