package hwa.helloworld.findingfriends;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.List;

import static hwa.helloworld.findingfriends.MainActivity.REQUEST_CODE_ANOTHER;

/**
 * Created by student on 2019-01-08.
 */

public class DetailActivity extends Activity {
    final int REV_GEO_MESSAGE = 1001;
    TextView tv_subject, tv_detail, tv_lat, tv_lng, tv_addr;
    Button btn_ok, btn_cancel;
    GoogleMap googleMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_detail);

        tv_lat = (TextView)findViewById(R.id.tv_lat);
        tv_lng = (TextView)findViewById(R.id.tv_lng);
        tv_addr = (TextView)findViewById(R.id.tv_addr);

        Intent intent = getIntent();
        Double nowlat = intent.getExtras().getDouble("now_lat");
        Double nowlng = intent.getExtras().getDouble("now_lng");
        /*intent.getStringExtra("now_latlng");*/

        tv_lat.setText(Double.toString(nowlat));
        tv_lng.setText(Double.toString(nowlng));

        LatLng latLng = new LatLng(
                Double.valueOf(tv_lat.getText().toString()),
                Double.valueOf(tv_lng.getText().toString()));
        DetailActivity.RevGeoThread revGeoThread = new DetailActivity.RevGeoThread(latLng);
        revGeoThread.start();

        btn_ok = (Button)findViewById(R.id.btn_ok);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 내용 보내기
                Toast.makeText(DetailActivity.this, "작성 ok", Toast.LENGTH_SHORT).show();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Handler geoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case REV_GEO_MESSAGE:

                    Address addr = (Address)msg.obj;
                    tv_addr.setText(addr.getAddressLine(0));
                    /*LatLng latLng1 = new LatLng(
                            Double.valueOf(et_lng.getText().toString()),
                            Double.valueOf(et_lat.getText().toString()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));*/

                    /*MarkerOptions markerOptions1 = new MarkerOptions();
                    markerOptions1.position(latLng1);
                    markerOptions1.title("선택된 장소");
                    markerOptions1.snippet(addr.getAddressLine(0));
                    googleMap.addMarker(markerOptions1);*/
                    break;
            }
        }
    };

    class RevGeoThread extends Thread {
        LatLng latLng;

        public RevGeoThread(LatLng latLng) {
            this.latLng = latLng;
        }

        @Override
        public void run() {
            Geocoder geocoder = new Geocoder(DetailActivity.this);
            List<Address> addr = null;

            try {
                addr = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                if(addr != null && addr.size() > 0) {
                    Address pin = addr.get(0);

                    Message msg = new Message();
                    msg.obj = pin;
                    msg.what = REV_GEO_MESSAGE;
                    geoHandler.sendMessage(msg);
                } else {
                    Log.d("결과","검색 결과가 없습니다.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == REQUEST_CODE_ANOTHER) {
            Toast toast = Toast.makeText(getBaseContext(), "onActivityResult() 메소드 호출됨.", Toast.LENGTH_LONG);
            toast.show();

            if(resultCode == RESULT_OK) {
                //String name = intent.getExtras().getString("name");
                toast = Toast.makeText(getBaseContext(), "OK", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

}
