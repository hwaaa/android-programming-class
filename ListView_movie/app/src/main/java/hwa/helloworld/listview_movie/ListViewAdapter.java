package hwa.helloworld.listview_movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import hwa.helloworld.listview_movie.form.ListViewItem;

/**
 * Created by student on 2018-12-13.
 */

public class ListViewAdapter extends BaseAdapter {
    ArrayList<ListViewItem> list;   // 자료를 저장하고 있는 ArrayList
    Context context;
    int item_layout;
    LayoutInflater layoutInflater;

    public ListViewAdapter(ArrayList<ListViewItem> list, Context context, int item_layout) {
        this.list = list;
        this.context = context;
        this.item_layout = item_layout;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ListViewAdapter(ArrayList<ListViewItem> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        //return list.size();
        if(list.size() % 2 == 0) {
            return list.size() / 2;
        } else {
            return (list.size() / 2) + 1;
        }   // 한 페이지에 2개의 데이터 넣을 때
        // 홀수개, 짝수개
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        //return 0;
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //final int pos = i;
        // 데이터 개수
        final int pos_l;
        final int pos_r;
        pos_l = (i * 2);
        pos_r = (i * 2) + 1;

        if (view == null) {
            view = layoutInflater.inflate(item_layout, viewGroup, false);
        }


        ImageView iv_thumb = (ImageView) view.findViewById(R.id.iv_thumb);
        //iv_thumb.setImageResource(list.get(pos).getImg_id());
        iv_thumb.setImageResource(list.get(pos_l).getImg_id());
        iv_thumb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, list.get(pos_l).getTitle()+"을(를) 선택했습니다.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("movie_index", pos_l);
                intent.putExtra("movie_title", list.get(pos_l).getTitle());
                intent.putExtra("movie_date", list.get(pos_l).getDate());
                intent.putExtra("movie_img_id", list.get(pos_l).getImg_id());
                context.startActivity(intent);
            }
        });

        //out of memory
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4; // 1/inSampleSize resize 2, 4, 8, 16

        ImageView iv_thumb2 = (ImageView) view.findViewById(R.id.iv_thumb2);

        // 1/inSampleSize 비율로 리사이징
        Bitmap thumb_resize = BitmapFactory.decodeResource(view.getResources(), list.get(pos_l).getImg_id(), options);
        iv_thumb.setImageBitmap(thumb_resize);

        Bitmap thumb_resize2 = BitmapFactory.decodeResource(view.getResources(), list.get(pos_r).getImg_id(), options);
        iv_thumb2.setImageBitmap(thumb_resize2);


        /**
         * 텍스트뷰로 제목, 날짜 바꾸기
         */
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_date = (TextView) view.findViewById(R.id.tv_date);

        tv_title.setText(list.get(pos_l).getTitle());
        tv_date.setText(list.get(pos_l).getDate());

        TextView tv_title2 = (TextView)view.findViewById(R.id.tv_title2);
        TextView tv_date2 = (TextView)view.findViewById(R.id.tv_date2);

        if(pos_r < list.size()) {
            iv_thumb2.setImageResource(list.get(pos_r).getImg_id());
            iv_thumb2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,
                            list.get(pos_r).getTitle()+"를(을) 선택했습니다.",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra("movie_index", pos_r);
                    intent.putExtra("movie_title", list.get(pos_r).getTitle());
                    intent.putExtra("movie_date", list.get(pos_r).getDate());
                    intent.putExtra("movie_img_id", list.get(pos_r).getImg_id());
                    context.startActivity(intent);
                }
            });

            tv_title2.setText(list.get(pos_r).getTitle());
            tv_date2.setText(list.get(pos_r).getDate());
        } else {
            iv_thumb2.setVisibility(View.GONE);
            tv_title2.setVisibility(View.GONE);
            tv_date2.setVisibility(View.GONE);
        }

        return view;
    }
}
