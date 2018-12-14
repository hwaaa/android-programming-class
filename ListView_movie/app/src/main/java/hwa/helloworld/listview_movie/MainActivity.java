package hwa.helloworld.listview_movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hwa.helloworld.listview_movie.form.ListViewItem;

public class MainActivity extends AppCompatActivity {

    ListView lvItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String[] movie = {"블랙팬서", "궁합", "리틀포레스트", "월요일이사라졌다"};
        // 2. 리스트뷰 객체 만들기
        lvItem = (ListView)findViewById(R.id.lvItem);

        // Adapter 에게 전달할 데이터 구성하기
        ListViewItem item = new ListViewItem("블랙펜서", "2018. 3", R.drawable.blackpanther);
        ArrayList<ListViewItem> arrayList = new ArrayList<ListViewItem>();
        arrayList.add(item);
        arrayList.add(new ListViewItem("궁합", "2018. 1", R.drawable.gh));
        arrayList.add(new ListViewItem("리틀포레스트", "2018. 11", R.drawable.littleforest));
        arrayList.add(new ListViewItem("월요일이사라졌다", "2018. 12", R.drawable.monday));


        /**
         * 첫번째 매개변수: 액티비티 정보 (context 객체)
         * 두번째 매개변수: 리스트뷰 항목의 레이아웃 (안드로이드 제공)
         * 세번째 매개변수: 표시할 데이터들
         */
        // 3. 리스트뷰에 Adapter 등록하기
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, movie);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movie);*/
        //lvItem.setAdapter(adapter);

        // 3-1. 새로 만든 어댑터 등록하기
        ListViewAdapter listViewAdapter = new ListViewAdapter( arrayList, MainActivity.this, R.layout.listview_item_horizontal);
        lvItem.setAdapter(listViewAdapter);

        /*InformationAdapter informationAdapter = new InformationAdapter( arrayList, MainActivity.this, R.layout.information);
        lvItem.setAdapter(informationAdapter);*/

        // 4. 리스트 뷰에 OnItemClickListener 등록하기
        lvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                    Toast.makeText(MainActivity.this, i + " 선택함", Toast.LENGTH_SHORT).show();
                }
            });

    }
}
