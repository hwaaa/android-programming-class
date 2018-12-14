package hwa.helloworld.listview_movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import hwa.helloworld.listview_movie.form.InformationItem;

/**
 * Created by student on 2018-12-14.
 */

public class InformationAdapter extends BaseAdapter {
    ArrayList<InformationItem> list;   // 자료를 저장하고 있는 ArrayList
    Context context;
    int item_layout;
    LayoutInflater layoutInflater;

    public InformationAdapter(ArrayList<InformationItem> list, Context context, int item_layout) {
        this.list = list;
        this.context = context;
        this.item_layout = item_layout;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public InformationAdapter(ArrayList<InformationItem> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final int pos = i;

        if (view == null) {
            view = layoutInflater.inflate(item_layout, viewGroup, false);
        }


        ImageView iv_thumb = (ImageView) view.findViewById(R.id.iv_poster);
        iv_thumb.setImageResource(list.get(pos).getInfo_img_id());
        iv_thumb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Toast.makeText(context, list.get(pos).getTitle()+"을(를) 선택했습니다.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("movie_index", pos);
                intent.putExtra("movie_title", list.get(pos).getInfo_title());
                intent.putExtra("movie_date", list.get(pos).getInfo_date());
                intent.putExtra("movie_img_id", list.get(pos).getInfo_img_id());
                context.startActivity(intent);
            }
        });

        //out of memory
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4; // 1/inSampleSize resize 2, 4, 8, 16


        // 1/inSampleSize 비율로 리사이징
        Bitmap thumb_resize = BitmapFactory.decodeResource(view.getResources(), list.get(pos).getInfo_img_id(), options);
        iv_thumb.setImageBitmap(thumb_resize);


        /**
         * 텍스트뷰로 제목, 날짜 바꾸기
         */
        TextView tv_title = (TextView) view.findViewById(R.id.tv_moviename);
        TextView tv_date = (TextView) view.findViewById(R.id.tv_moviedate);

        tv_title.setText(list.get(pos).getInfo_title());
        tv_date.setText(list.get(pos).getInfo_date());

        if(pos < list.size()) {
            iv_thumb.setImageResource(list.get(pos).getInfo_img_id());
            iv_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,
                            list.get(pos).getInfo_title()+"를(을) 선택했습니다.",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("movie_index", pos);
                    intent.putExtra("movie_title", list.get(pos).getInfo_title());
                    intent.putExtra("movie_date", list.get(pos).getInfo_date());
                    intent.putExtra("movie_img_id", list.get(pos).getInfo_img_id());
                    context.startActivity(intent);
                }
            });

            tv_title.setText(list.get(pos).getInfo_title());
            tv_date.setText(list.get(pos).getInfo_date());
        } else {
            iv_thumb.setVisibility(View.GONE);
            tv_title.setVisibility(View.GONE);
            tv_date.setVisibility(View.GONE);
        }

        return view;
    }
}
