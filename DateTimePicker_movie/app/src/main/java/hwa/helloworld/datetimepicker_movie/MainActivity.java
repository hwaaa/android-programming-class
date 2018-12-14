package hwa.helloworld.datetimepicker_movie;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Button btn_date, btn_time, btn_init;
    TextView tv_date, tv_time, tv_seat;
    SeekBar seekbar_seat;
    int year, month, day, hour, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_date = (Button)findViewById(R.id.btn_date);
        btn_time = (Button)findViewById(R.id.btn_time);
        btn_init = (Button)findViewById(R.id.btn_init);

        tv_date = (TextView)findViewById(R.id.tv_date);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_seat = (TextView)findViewById(R.id.tv_seat);

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
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // 월은 0부터 시작된다!
            Toast.makeText(MainActivity.this, year + "/" + (month+1) + "/" + dayOfMonth, Toast.LENGTH_LONG).show();
            String date = year + "년 " + (month+1) + "월 " + dayOfMonth + "일";
            tv_date.setText(date);  // 선택한 날짜 보여주기
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            Toast.makeText(MainActivity.this, hourOfDay + " : " + minute, Toast.LENGTH_LONG).show();
            String hour = hourOfDay + "시 " + minute + "분";
            tv_time.setText(hour);  // 선택한 시간 보여주기
        }
    };
}
