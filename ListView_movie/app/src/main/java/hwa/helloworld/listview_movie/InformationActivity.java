package hwa.helloworld.listview_movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import hwa.helloworld.listview_movie.form.InformationItem;

public class InformationActivity extends AppCompatActivity {

    ImageView iv_poster;
    TextView tv_moviename, tv_moviedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        iv_poster = (ImageView)findViewById(R.id.iv_poster);

        tv_moviename = (TextView)findViewById(R.id.tv_moviename);
        tv_moviedate = (TextView)findViewById(R.id.tv_moviedate);

        ArrayList<InformationItem> info_arrayList = new ArrayList<InformationItem>();
        InformationAdapter InformationAdapter = new InformationAdapter( info_arrayList, InformationActivity.this, R.layout.activity_information);

        info_arrayList.add(new InformationItem("블랙펜서", "2018. 3", R.drawable.blackpanther));
        info_arrayList.add(new InformationItem("궁합", "2018. 1", R.drawable.gh));
        info_arrayList.add(new InformationItem("리틀포레스트", "2018. 11", R.drawable.littleforest));
        info_arrayList.add(new InformationItem("월요일이사라졌다", "2018. 12", R.drawable.monday));
        //lvItem.setAdapter(InformationAdapter);

        final int info_pos;

        Intent intent = getIntent();

        if(intent != null) {
            //iv_poster = intent.getIntArrayExtra(info_arrayList.get(info_pos).getInfo_img_id());
        }
    }
}
