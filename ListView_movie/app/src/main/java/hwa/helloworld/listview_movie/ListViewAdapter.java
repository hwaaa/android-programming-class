package hwa.helloworld.listview_movie;

import android.content.Context;
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


        ImageView iv_thumb = (ImageView) view.findViewById(R.id.iv_thumb);
        iv_thumb.setImageResource(list.get(pos).getImg_id());
        iv_thumb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, "그림을 선택했습니다.", Toast.LENGTH_LONG).show();
            }
        });

        //out of memory
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4; // 1/inSampleSize resize 2, 4, 8, 16

        // 1/inSampleSize 비율로 리사이징
        Bitmap thumb_resize = BitmapFactory.decodeResource(view.getResources(), list.get(pos).getImg_id(), options);
        iv_thumb.setImageBitmap(thumb_resize);


        /**
         * 텍스트뷰로 제목, 날짜 바꾸기
         */
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_date = (TextView) view.findViewById(R.id.tv_date);

        tv_title.setText(list.get(pos).getTitle());
        tv_date.setText(list.get(pos).getDate());

        return view;
    }
}
