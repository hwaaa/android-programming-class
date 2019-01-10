package hwa.helloworld.findingfriends;

import android.*;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharing_location);

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


        final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());



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
    public void onMapReady(final GoogleMap googleMap) {
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

        Location location = locationManager.getLastKnownLocation(selected_provider);
        final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(NOW));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        googleMap.addMarker(new MarkerOptions().position(NOW));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(final LatLng latLng) {
                ArrayList<LocationVO> location_list = new ArrayList<>();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + "," + latLng.longitude);
                googleMap.addMarker(markerOptions);

                location_list.add(new LocationVO(latLng.latitude, latLng.longitude, "위치", "주소"));
                //Toast.makeText(SharingActivity.this, "lat " + location_list.get(i).getLat() + " / lng " + location_list.get(i).getLng(), Toast.LENGTH_SHORT).show();


                btn_back1.setVisibility(View.VISIBLE);
                btn_write.setVisibility(View.VISIBLE);

                tv_addr.setText(latLng.latitude + " / " + latLng.longitude);

                btn_write.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 메모작성 인텐트 띄우기
                        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
                        intent.putExtra("now_latlng", latLng.latitude + " / " + latLng.longitude);
                        startActivityForResult(intent, REQUEST_CODE_ANOTHER);

                    }
                });
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }
}
