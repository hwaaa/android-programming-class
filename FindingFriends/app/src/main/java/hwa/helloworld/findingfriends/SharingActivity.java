package hwa.helloworld.findingfriends;

import android.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static hwa.helloworld.findingfriends.MainActivity.REQUEST_CODE_ANOTHER;

/**
 * Created by student on 2019-01-08.
 */

public class SharingActivity extends Activity implements OnMapReadyCallback {

    LocationManager locationManager;
    LocationListener locationListener;
    String selected_provider;
    Button btn_back1, btn_write;
    Location location;
    TextView tv_addr;
    GoogleMap googleMap;
    LatLng latLng;

    final int REV_GEO_MESSAGE = 1001;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_location);

        Thread thread = new client_thread();
        thread.start();

        tv_addr = (TextView)findViewById(R.id.tv_addr);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        // 현재 위치 받아오기 위한
        List<String> providers = locationManager.getAllProviders();
        List<String> enable_providers = locationManager.getProviders(true);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        selected_provider = locationManager.getBestProvider(criteria, true);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            location = locationManager.getLastKnownLocation(selected_provider);
        }


        //final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());



        btn_back1 = (Button) findViewById(R.id.btn_back1);
        btn_back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                //resultIntent.putExtra("name", "jane");
                setResult(RESULT_OK, resultIntent); //이 메소드 호출 시 응답보냄
                finish(); //이 메소드 호출 시 액티비티 종료
            }
        });

        btn_write = (Button) findViewById(R.id.btn_write);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 위치 정보 전달할 때 호출
                // 위치 정보를 얻을 수 있다.
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                // provider의 상태가 변경될 때마다 호출
            }

            @Override
            public void onProviderEnabled(String s) {
                // provider가 사용 가능한 상황이 되는 순간 호출
            }

            @Override
            public void onProviderDisabled(String s) {
                // provider가 사용 불가능한 상황이 되는 순간 호출
            }
        };

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.sharing_map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(final GoogleMap map) {
        this.googleMap = map;
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(selected_provider);
            if(location != null) {

            }
            locationManager.requestLocationUpdates(selected_provider, 10000, 10, locationListener);
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION }, 200);
            Toast.makeText(SharingActivity.this, "권한을 허용한 후 재시작 해주세요.", Toast.LENGTH_SHORT).show();
        }

        final Location location = locationManager.getLastKnownLocation(selected_provider);
        if(location == null) {

        } else {
            final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(NOW));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            googleMap.addMarker(new MarkerOptions().position(NOW));
        }


       /* final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(NOW));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
        map.addMarker(new MarkerOptions().position(NOW));*/

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(final LatLng latLng) {
                ArrayList<LocationVO> location_list = new ArrayList<>();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + "," + latLng.longitude);
                googleMap.addMarker(markerOptions);

                location_list.add(new LocationVO(latLng.latitude, latLng.longitude, "위치", "주소"));
                //Toast.makeText(SharingActivity.this, "lat " + location_list.get(i).getLat() + " / lng " + location_list.get(i).getLng(), Toast.LENGTH_SHORT).show();


                final Double lat = latLng.latitude;
                final Double lng = latLng.longitude;

                btn_write.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LatLng latLng = new LatLng(
                                Double.valueOf(lat),
                                Double.valueOf(lng));
                        SharingActivity.RevGeoThread revGeoThread = new SharingActivity.RevGeoThread(latLng);
                        revGeoThread.start();

                        Intent intent = new Intent(SharingActivity.this, DetailActivity.class);
                        intent.putExtra("now_lat", lat);
                        intent.putExtra("now_lng", lng);
                        //setResult(1, intent);
                        //finish();

                        // 메모작성 인텐트 띄우기
                        //Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        /*intent.putExtra("now_lat", latLng.latitude);
                        intent.putExtra("now_lng", latLng.longitude);*/
                        startActivityForResult(intent, REQUEST_CODE_ANOTHER);
                        finish();

                    }
                });
            }
        });
    }

    Handler geoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case REV_GEO_MESSAGE:
                    googleMap.clear();

                    Address addr = (Address)msg.obj;
                    tv_addr.setText("주소 : " + addr.getAddressLine(0));
                    final String now_addr = addr.getAddressLine(0);
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
            Geocoder geocoder = new Geocoder(SharingActivity.this);
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
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }


    public class client_thread extends Thread {

        @Override
        public void run() {
            Socket socket = null;
            try {
                socket = new Socket();
                System.out.println("[연결 요청]");
                socket.connect(new InetSocketAddress("70.12.110.68", 5001));
                System.out.println("[연결 성공]");

                // 데이터 보내고 받기
                byte[] bytes = null;
                String msg = null;
                String now_lat = null;

                @SuppressLint("MissingPermission")
                Location location = locationManager.getLastKnownLocation(selected_provider);

                OutputStream os = socket.getOutputStream();
                //msg = "Hello Server";

                msg = Double.toString(location.getLatitude()) + "/" + Double.toString(location.getLongitude()) + "/";

                //bytes = msg.getBytes("UTF-8");
                bytes = msg.getBytes("UTF-8");
                os.write(bytes);
                os.flush();
                System.out.println("[데이터 보내기 성공!]");

                InputStream is = socket.getInputStream();
                bytes = new byte[100];
                int readByteCount = is.read(bytes);
                /*msg = new String(bytes, 0, readByteCount, "UTF-8");
                System.out.println("[데이터 받기 성공]: " + msg);*/

                now_lat = new String(bytes, 0, readByteCount, "UTF-8");
                System.out.println("[데이터 받기 성공]: " + now_lat);


                String[] latlng = now_lat.split("/");
                for (int i=0; i<latlng.length; i++) {
                    final LatLng loc = new LatLng(Double.parseDouble(latlng[i]), Double.parseDouble(latlng[i+1]));

                    // loc를 message로 핸들러에 전달하기 (서버에 있는 위치 받아서 마커찍기)
                    Message msg1 = new Message();
                    msg1.what = 1;
                    msg1.obj = loc;
                    handler.sendMessage(msg1);
                }


                os.close();
                is.close();

            } catch (Exception e) {
                //Log.d("결과 ", "서버 연결 실패");
            }

            if(!socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e1) {
                    Log.d("결과 ", "소켓 닫기 실패");
                }
            }
        }
    };

    Handler handler = new Handler() {
        public void handleMessage(Message msg1) {
            super.handleMessage(msg1);
            if(msg1.what == 1) {
                googleMap.addMarker(new MarkerOptions().position((LatLng)msg1.obj));
            }
        }
    };



}
