package hwa.helloworld.datetimepicker_movie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Button btn_date, btn_time, btn_init, btn_cancel, btn_book;
    TextView tv_date, tv_time, tv_seat, tv_title, tv_director, tv_cast, tv_genre;
    SeekBar seekbar_seat;
    int year, month, day, hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 데이터 저장
        final String[] movie = {"블랙팬서", "궁합", "리틀포레스트", "월요일이사라졌다"};
        // 2. 리스트뷰 객체 만들기

        // Adapter 에게 전달할 데이터 구성하기

        GregorianCalendar littleforest = new GregorianCalendar(2018, 11, 11, 13, 30);
        GregorianCalendar blackpanther = new GregorianCalendar(2018, 12, 11, 13, 30);
        GregorianCalendar gunghap = new GregorianCalendar(2018, 12, 12, 13, 30);
        GregorianCalendar monday = new GregorianCalendar(2018, 11, 14, 13, 30);

        BookData item = new BookData(littleforest, "리틀포레스트", 3, 30, 28);
        final ArrayList<BookData> arrayList = new ArrayList<BookData>();
        arrayList.add(item);
        arrayList.add(new BookData(blackpanther, "블랙팬서", 2, 20, 20));
        arrayList.add(new BookData(gunghap, "궁합", 2.5, 30, 10));
        arrayList.add(new BookData(monday, "월요일이사라졌다", 3.5, 20, 20));

        btn_date = (Button)findViewById(R.id.btn_date);
        btn_time = (Button)findViewById(R.id.btn_time);
        btn_init = (Button)findViewById(R.id.btn_init);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_book = (Button)findViewById(R.id.btn_book);

        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_seat = (TextView)findViewById(R.id.tv_seat);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_director = (TextView)findViewById(R.id.tv_director);
        tv_cast = (TextView)findViewById(R.id.tv_cast);
        tv_genre = (TextView)findViewById(R.id.tv_genre);

        seekbar_seat = (SeekBar)findViewById(R.id.seekbar_seat);

        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);

        // 날짜 선택 버튼 누르면
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(MainActivity.this, dateSetListener, year, month, day).show();
            }
        });

        // 시간 선택 버튼 누르면
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(MainActivity.this, timeSetListener, hour, min, false).show();
            }
        });

        // 좌석 수 선택
        seekbar_seat.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int remain_seat = arrayList.get(0).getAll_seats() - arrayList.get(0).getBooked_seats();
                if(progress > remain_seat) {
                    Toast.makeText(MainActivity.this, "좌석이 없습니다!", Toast.LENGTH_LONG).show();
                } else {
                    tv_seat.setText("선택 좌석 수: " + progress);
                }
                tv_seat.setText("선택 좌석 수: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // 초기화 버튼 누르면 좌석 수 초기화
        btn_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekbar_seat.setProgress(0);
            }
        });

        // (나중에 수정하기) 영화 지정: 리틀포레스트
        tv_title.setText(arrayList.get(0).getMoive_title());
        tv_director.setText("임순례");
        tv_cast.setText("김태리, 류준열, 문소리 등");
        tv_genre.setText("드라마");
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // 월은 0부터 시작된다!
            GregorianCalendar littleforest = new GregorianCalendar(2018, 11, 11, 13, 30);
            //Toast.makeText(MainActivity.this, year + "/" + (month+1) + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
            String date = year + "년 " + (month+1) + "월 " + dayOfMonth + "일";
            if(year != 2018 && (month+1) != 11 && dayOfMonth != 11) {
                Toast.makeText(MainActivity.this, "상영일시가 아닙니다!", Toast.LENGTH_LONG).show();
            } else {
                tv_date.setText(date);  // 선택한 날짜 보여주기
            }

        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            GregorianCalendar littleforest = new GregorianCalendar(2018, 11, 11, 13, 30);
            //Toast.makeText(MainActivity.this, hourOfDay + " : " + minute, Toast.LENGTH_LONG).show();
            String hour = hourOfDay + "시 " + minute + "분";
            // 리틀포레스트: 13시 30분 ~ 15시 30분 (3시간)
            if(hourOfDay >= littleforest.HOUR_OF_DAY) {
                if (hourOfDay <= littleforest.HOUR_OF_DAY + 3 && minute <= littleforest.MINUTE) {
                    Toast.makeText(MainActivity.this, "상영시간이 아닙니다!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "상영시간이 아닙니다!", Toast.LENGTH_LONG).show();
                }
            } else {
                tv_time.setText(hour);  // 선택한 시간 보여주기
            }

        }
    };

}
