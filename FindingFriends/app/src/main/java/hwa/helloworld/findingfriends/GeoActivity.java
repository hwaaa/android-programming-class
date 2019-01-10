package hwa.helloworld.findingfriends;

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
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static hwa.helloworld.findingfriends.MainActivity.REQUEST_CODE_ANOTHER;

/**
 * Created by student on 2019-01-10.
 */

public class GeoActivity extends AppCompatActivity implements OnMapReadyCallback {

    final int REV_GEO_MESSAGE = 1001;

    LocationManager locationManager;
    LocationListener locationListener;
    String selected_provider;
    Location location;
    TextView tv_result;
    EditText et_lng, et_lat, et_addr;
    Button btn_geo, btn_revGeo;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_layout);

        tv_result = (TextView)findViewById(R.id.tv_result);
        et_addr = (EditText)findViewById(R.id.et_addr);
        et_lat = (EditText)findViewById(R.id.et_lat);
        et_lng = (EditText)findViewById(R.id.et_lng);
        btn_geo = (Button)findViewById(R.id.btn_geo);
        btn_revGeo = (Button)findViewById(R.id.btn_revGeo);

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


        final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());

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
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.geo_map);
        mapFragment.getMapAsync(this);

        /*btn_geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GeoThread geoThread = new GeoThread(
                        et_addr.getText().toString());
                geoThread.start();
            }
        });*/

        btn_revGeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LatLng latLng1 = new LatLng(
                        Double.valueOf(et_lng.getText().toString()),
                        Double.valueOf(et_lat.getText().toString()));
                RevGeoThread revGeoThread = new RevGeoThread(latLng1);
                revGeoThread.start();
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap map) {
        this.googleMap = map;

        /*
        LatLng SEOUL = new LatLng(37.56, 126.97);
        map.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        map.addMarker(markerOptions); */

        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(selected_provider);
            if(location != null) {

            }
            locationManager.requestLocationUpdates(selected_provider, 10000, 10, locationListener);
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION }, 200);
            Toast.makeText(GeoActivity.this, "권한을 허용한 후 재시작 해주세요.", Toast.LENGTH_SHORT).show();
        }

        Location location = locationManager.getLastKnownLocation(selected_provider);
        final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());
        map.moveCamera(CameraUpdateFactory.newLatLng(NOW));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
        map.addMarker(new MarkerOptions().position(NOW));

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(final LatLng latLng) {
                ArrayList<LocationVO> location_list = new ArrayList<>();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + "," + latLng.longitude);
                map.addMarker(markerOptions);

                location_list.add(new LocationVO(latLng.latitude, latLng.longitude, "위치", "주소"));
                //Toast.makeText(SharingActivity.this, "lat " + location_list.get(i).getLat() + " / lng " + location_list.get(i).getLng(), Toast.LENGTH_SHORT).show();

                //tv_result.setText(latLng.latitude + " / " + latLng.longitude);

                String lat = Double.toString(latLng.latitude);
                String lng = Double.toString(latLng.longitude);
                et_lat.setText(lat);
                et_lng.setText(lng);

            }
        });
    }

    Handler geoHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {/*
                case GEO_MESSAGE:
                    LatLng latLng = (LatLng)msg.obj;
                    tv_result.setText("위도 : " + latLng.latitude + " 경도 : " + latLng.longitude);
                    googleMap.clear();

                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("선택된 장소");
                    markerOptions.snippet(et_addr.getText().toString());
                    googleMap.addMarker(markerOptions);
                    break;*/
                case REV_GEO_MESSAGE:
                   // googleMap.clear();

                    Address addr = (Address)msg.obj;
                    tv_result.setText("주소 : " + addr.getAddressLine(0));
                    LatLng latLng1 = new LatLng(
                            Double.valueOf(et_lng.getText().toString()),
                            Double.valueOf(et_lat.getText().toString()));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng1));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));

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
            Geocoder geocoder = new Geocoder(GeoActivity.this);
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

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*class GeoThread extends Thread {
        String address;

        public GeoThread(String address) {
            this.address = address;
        }

        @Override
        public void run() {
            Geocoder geocoder = new Geocoder(GeoActivity.this);
            List<Address> result = null;
            try {
                result = geocoder.getFromLocationName(address, 1);

                if(result != null && result.size() > 0) {
                    Address pin = result.get(0);
                    LatLng latLng = new LatLng(pin.getLatitude(), pin.getLongitude());

                    Message msg = new Message();
                    msg.obj = latLng;
                    msg.what = GEO_MESSAGE;
                    geoHandler.sendMessage(msg);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }*/
}
