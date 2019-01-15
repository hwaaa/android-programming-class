package hwa.helloworld.findingfriends;

import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import hwa.helloworld.findingfriends.MainActivity;

/**
 * Created by student on 2019-01-10.
 */

public class BearingActivity extends AppCompatActivity implements OnMapReadyCallback {
    EditText et_lat1, et_lat2, et_lon1, et_lon2;
    Button btn_go;
    ImageView iv_img;

    LocationManager locationManager;
    LocationListener locationListener;
    String selected_provider;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bearing_test);

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

            //Location location = locationManager.getLastKnownLocation(selected_provider);
        }


        //final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());


        et_lat1 = (EditText)findViewById(R.id.et_lat1);
        et_lat2 = (EditText)findViewById(R.id.et_lat2);
        et_lon1 = (EditText)findViewById(R.id.et_lon1);
        et_lon2 = (EditText)findViewById(R.id.et_lon2);
        btn_go = (Button)findViewById(R.id.btn_go);
        iv_img = (ImageView)findViewById(R.id.iv_img);

        /*String nowlat = Double.toString(location.getLatitude());
        String nowlng = Double.toString(location.getLongitude());
        et_lat1.setText(nowlat);
        et_lon1.setText(nowlng);*/

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double lat1 = Double.valueOf(et_lat1.getText().toString());
                double lat2 = Double.valueOf(et_lat2.getText().toString());
                double lon1 = Double.valueOf(et_lon1.getText().toString());
                double lon2 = Double.valueOf(et_lon2.getText().toString());

                short result = bearingP1toP2(lat1, lon1, lat2, lon2);

                //Toast.makeText(BearingActivity.this, "result : " + result, Toast.LENGTH_SHORT).show();

                iv_img.setVisibility(View.VISIBLE);
                iv_img.setRotation((float)result);

            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.bearing_map);
        mapFragment.getMapAsync(this);

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
    }

    public static short bearingP1toP2(
            double P1_latitude, double P1_longitude,
            double P2_latitude, double P2_longitude)
    {
        // 현재 위치 : 위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에 라디안 각도로 변환한다.
        double Cur_Lat_radian = P1_latitude * (3.141592 / 180);
        double Cur_Lon_radian = P1_longitude * (3.141592 / 180);


        // 목표 위치 : 위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에 라디안 각도로 변환한다.
        double Dest_Lat_radian = P2_latitude * (3.141592 / 180);
        double Dest_Lon_radian = P2_longitude * (3.141592 / 180);

        // radian distance
        double radian_distance = 0;
        radian_distance = Math.acos(
                Math.sin(Cur_Lat_radian) * Math.sin(Dest_Lat_radian)
                        + Math.cos(Cur_Lat_radian) * Math.cos(Dest_Lat_radian)
                        * Math.cos(Cur_Lon_radian - Dest_Lon_radian));

        // 목적지 이동 방향을 구한다.(현재 좌표에서 다음 좌표로 이동하기 위해서는 방향을 설정해야 한다. 라디안값이다.
        double radian_bearing = Math.acos(
                (Math.sin(Dest_Lat_radian) - Math.sin(Cur_Lat_radian)
                        * Math.cos(radian_distance)) / (Math.cos(Cur_Lat_radian)
                        * Math.sin(radian_distance)));
        // acos의 인수로 주어지는 x는 360분법의 각도가 아닌 radian(호도)값이다.

        double true_bearing = 0;
        if (Math.sin(Dest_Lon_radian - Cur_Lon_radian) < 0)
        {
            true_bearing = radian_bearing * (180 / 3.141592);
            true_bearing = 360 - true_bearing;
        }
        else
        {
            true_bearing = radian_bearing * (180 / 3.141592);
        }

        return (short)true_bearing;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION )
                == PackageManager.PERMISSION_GRANTED) {
            Location location = locationManager.getLastKnownLocation(selected_provider);
            if(location != null) {

            }
            locationManager.requestLocationUpdates(selected_provider, 5000, 10, locationListener);
        } else {
            ActivityCompat.requestPermissions(
                    this, new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION }, 200);
            Toast.makeText(BearingActivity.this, "권한을 허용한 후 재시작 해주세요.", Toast.LENGTH_SHORT).show();
        }

        final Location location = locationManager.getLastKnownLocation(selected_provider);
        if(location == null) {

            final LatLng NOW = new LatLng(37.45811060, 126.96140851);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(NOW));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));
            googleMap.addMarker(new MarkerOptions().position(NOW));

            String nowlat = Double.toString(37.45811060);
            String nowlng = Double.toString(126.96140851);

            et_lat1.setText(nowlat);
            et_lon1.setText(nowlng);

        } else {
            final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(NOW));
            googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
            googleMap.addMarker(new MarkerOptions().position(NOW));
        }

        /*String nowlat1 = Double.toString(location.getLatitude());
        String nowlng1 = Double.toString(location.getLongitude());
        Log.d("위치1 ", nowlat1 + " / " + nowlng1);*/

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(final LatLng latLng) {
                /*if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION )
                        == PackageManager.PERMISSION_GRANTED) {
                    Location location = locationManager.getLastKnownLocation(selected_provider);
                }*/
                ArrayList<LocationVO> location_list = new ArrayList<>();

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude + "," + latLng.longitude);
                googleMap.addMarker(markerOptions);

                String lat2 = Double.toString(latLng.latitude);
                String lng2 = Double.toString(latLng.longitude);
                Log.d("위치2 ", lat2 + " / " + lng2);

                et_lat2.setText(lat2);
                et_lon2.setText(lng2);

                location_list.add(new LocationVO(latLng.latitude, latLng.longitude, "위치", "주소"));
                //Toast.makeText(SharingActivity.this, "lat " + location_list.get(i).getLat() + " / lng " + location_list.get(i).getLng(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}
