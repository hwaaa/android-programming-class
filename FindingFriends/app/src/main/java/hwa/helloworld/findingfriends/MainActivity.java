package hwa.helloworld.findingfriends;

import android.Manifest;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    LocationManager locationManager;
    LocationListener locationListener;
    String selected_provider;
    TextView tv_dis, tv_cnt;
    int cnt = 0;

    // 상수를 통해 띄운 액티비티를 구분
    public static final int REQUEST_CODE_ANOTHER = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_dis = (TextView)findViewById(R.id.tv_dis);
        tv_cnt = (TextView)findViewById(R.id.tv_cnt);
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_direction) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), FriendsDirection.class);
            startActivityForResult(intent, REQUEST_CODE_ANOTHER); //두 번째 파라미터로 띄울 액티비티 구분
        } else if (id == R.id.nav_recom) {
            Intent intent = new Intent(getBaseContext(), SharingActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ANOTHER); //두 번째 파라미터로 띄울 액티비티 구분
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(getBaseContext(), GeoActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ANOTHER); //두 번째 파라미터로 띄울 액티비티 구분
        } else if (id == R.id.nav_manage) {
            Intent intent = new Intent(getBaseContext(), BearingActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ANOTHER);
        } /*else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            Toast.makeText(MainActivity.this, "권한을 허용한 후 재시작 해주세요.", Toast.LENGTH_SHORT).show();
        }

        final Location location = locationManager.getLastKnownLocation(selected_provider);
        final LatLng NOW = new LatLng(location.getLatitude(), location.getLongitude());
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(NOW));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        googleMap.addMarker(new MarkerOptions().position(NOW));

        String lat1 = Double.toString(location.getLatitude());
        String lng1 = Double.toString(location.getLongitude());
        Log.d("위치1 ", lat1 + " / " + lng1);

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

                location_list.add(new LocationVO(latLng.latitude, latLng.longitude, "위치", "주소"));
                //Toast.makeText(SharingActivity.this, "lat " + location_list.get(i).getLat() + " / lng " + location_list.get(i).getLng(), Toast.LENGTH_SHORT).show();

                if(distance(location.getLatitude(), location.getLongitude(), latLng.latitude, latLng.longitude) < 10) {
                    // 거리 위치 진동....
                    Vibrator vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
                    vib.vibrate(500);  // 0.5초 동안 진동

                    Toast.makeText(MainActivity.this, "친구가 근처에 있습니다.", Toast.LENGTH_LONG).show();
                    cnt++;  // 횟수 카운트
                } else {
                    Toast.makeText(MainActivity.this, "10 이상", Toast.LENGTH_LONG).show();
                }

                String dis = Double.toString(distance(location.getLatitude(), location.getLongitude(), latLng.latitude, latLng.longitude));
                tv_dis.setText(dis);
                tv_cnt.setText(Integer.toString(cnt));
            }
        });
    }

    /**
     * 주어진 도(degree)값을 라디언으로 변환
     * @param deg
     * @return
     */
    private double deg2rad(double deg){
        return (double)(deg * Math.PI / (double)180d);
    }

    /**
     * 주어진 라디언(radian)값을 도(degree)값으로 변환
     * @param rad
     * @return
     */
    private double rad2deg(double rad){
        return (double)(rad * (double)180d / Math.PI);
    }

    /**
     * 두 점 사이의 거리 구하기
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public double distance(double lat1, double lon1, double lat2, double lon2){

        //두점의 위도 경도가 주어진 경우 두점사이의 거리를 미터단위로 계산하여 되돌림
        double theta, dist;
        theta = lon1 - lon2;

        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        dist = dist * 1609.344;
        
        return dist;
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
