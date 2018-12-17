package hwa.helloworld.sdcardrw;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by student on 2018-12-17.
 */

public class ReadFileActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file);

        TextView textView = (TextView)findViewById(R.id.fileResult);

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/myApp/myfile.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(file);
            }
            textView.setText(buffer.toString());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
