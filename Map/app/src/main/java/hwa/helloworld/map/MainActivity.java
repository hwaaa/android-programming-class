package hwa.helloworld.map;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * 지도 객체를 얻어오면 위도 경도를 가진 LatLng 객체를 생성하여
     * 지도의 중심을 이동하고 싶은 위치로 지정한 후에
     * 위치는 지도 객체의 moveCamera() 함수로 설정해야 한다.
     * 추가적으로 지도의 획대 수준을 animateCamera() 함수로 설정할 수 있다.
     * 2~21까지의 숫자로 매개변수를 입력할 수 있으며 숫자가 클수록 지도가 상세하게 나온다.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        ArrayList<LocationVO> list = new ArrayList<>();
        list.add(new LocationVO(33.510675, 126.491359, "제주공항", "제주특별자치도 제주시 공항로 2"));
        list.add(new LocationVO(37.504012, 127.039270, "멀티캠퍼스", "서울특별시 강남구"));
        list.add(new LocationVO(37.596565, 126.972202, "청와대", "서울특별시 종로구"));

        MarkerOptions markerOptions = new MarkerOptions();
        for(int i=0; i<list.size(); i++) {
            LatLng SEOUL = new LatLng(list.get(i).getLat(), list.get(i).getLng());
            markerOptions.position(SEOUL);
            markerOptions.title(list.get(i).getName());
            markerOptions.snippet(list.get(i).getAddr());
            googleMap.addMarker(markerOptions);
        }

        LatLng SEOUL = new LatLng(37.56, 126.97);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

    }


}
