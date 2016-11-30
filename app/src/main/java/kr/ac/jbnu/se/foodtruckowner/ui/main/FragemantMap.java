package kr.ac.jbnu.se.foodtruckowner.ui.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hedgehog.ratingbar.RatingBar;
import com.squareup.picasso.Picasso;

import cn.pedant.SweetAlert.SweetAlertDialog;
import kr.ac.jbnu.se.foodtruckowner.R;
import kr.ac.jbnu.se.foodtruckowner.model.FoodTruckModel;
import kr.ac.jbnu.se.foodtruckowner.service.ApiService;
import kr.ac.jbnu.se.foodtruckowner.service.GpsService;
import kr.ac.jbnu.se.foodtruckowner.service.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragemantMap extends Fragment implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback, LocationListener{

    private GoogleApiClient mGoogleApiClient;
    private static final String STATE_RESOLVING_ERROR = "resolving_error";
    // Request code to use when launching the resolution activity
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    // Unique tag for the error dialog fragment
    private static final String DIALOG_ERROR = "dialog_error";
    // Bool to track whether the app is already resolving an error
    private boolean mResolvingError = false;

    private MapView mapview;
    private LatLng CuttrntLocation;
    private double USER_X;
    private double USER_Y;
    private GoogleMap map;
    private GpsService gpsService;
    private Switch loc_agree;
    private Switch turn_buss;
    private RatingBar mRatingBar;
    private FoodTruckModel myTruck;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        //푸드트럭 메인 이미지 홈 화면에 삽입
        ImageView truckMainImage = (ImageView)view.findViewById(R.id.truck_main_image);
        Picasso.with(getContext())
                .load(ServiceGenerator.API_BASE_URL + FoodTruckModel.getInstance().getFT_IMAGE_URL())
                .resize(300,150)
                .into(truckMainImage);

        mapview=(MapView)view.findViewById(R.id.map);
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mapview.onCreate(savedInstanceState);
        mapview.onResume();
        mapview.getMapAsync(this);

        mRatingBar = (RatingBar)view.findViewById(R.id.Ratingbar);
        initRatingBar();

        gpsService = new GpsService(getActivity());
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        map = mapview.getMap();
        turn_buss = (Switch)view.findViewById(R.id.sw_turn_buss);
        loc_agree = (Switch)view.findViewById(R.id.sw_loc_agree);

        turn_buss.setChecked(FoodTruckModel.getInstance().getFtStart());

        turn_buss.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton cb, boolean isChecking){
                String str = String.valueOf(isChecking);
                ApiService service = ServiceGenerator.createService(ApiService.class);
                if(isChecking)
                {
                    Call<Boolean> call = service.set_open(FoodTruckModel.getInstance().getFT_ID());
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean check = response.body();
                            if(check)
                                Toast.makeText(getActivity(), "영업 설정이 되었습니다. 영업 알림을 위해 위치 설정을 해주세요.", Toast.LENGTH_SHORT).show();
                            else
                                return;
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
                else{
                    Call<Boolean> call = service.set_close(FoodTruckModel.getInstance().getFT_ID());
                    call.enqueue(new Callback<Boolean>() {
                        @Override
                        public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                            Boolean check = response.body();
                            if(check)
                                Toast.makeText(getActivity(), "영업 종료", Toast.LENGTH_SHORT).show();
                            else
                                return;
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
            }
        });

        loc_agree.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            public void onCheckedChanged(CompoundButton cb, boolean isChecking){
                String str = String.valueOf(isChecking);
                if (isChecking) {
                    if (mapview.isActivated()== false) {
                        mapview.onResume();
                    }
                    Toast.makeText(getActivity(), "위치공개해라", Toast.LENGTH_LONG).show();
                } else {
                    mapview.onPause();
                    gpsService.stopUsingGPS();
                    Toast.makeText(getActivity(), "공개하지마라", Toast.LENGTH_LONG).show();
                }
            }


        });
        return view;
    }

    private void initRatingBar(){
        mRatingBar.setStarEmptyDrawable(getResources().getDrawable(R.drawable.ic_star_empty));
        mRatingBar.setStarFillDrawable(getResources().getDrawable(R.drawable.ic_star_fill));
        mRatingBar.setStarHalfDrawable(getResources().getDrawable(R.drawable.ic_star_half));
        mRatingBar.setStar(FoodTruckModel.getInstance().getFtRating());
    }

    private void init(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("GPS설정정보");
        alert.setMessage("현재위치를 사용하시려면 아니오를\n위치를 새로 설정하시려면 예를\n눌러주세요.");

        alert.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //사용자 위치 선택 받기
                if (gpsService.isGetLocation()) {
                    map.setOnMapClickListener(new com.google.android.gms.maps.GoogleMap.OnMapClickListener() {
                        @Override
                        public void onMapClick(LatLng arg0) {
                            // TODO Auto-generated method stub
                            MarkerOptions markerOptions = new MarkerOptions().position(new LatLng(arg0.latitude, arg0.longitude));
                            Log.d("GPS수신......X : ", String.valueOf(arg0.latitude));
                            Log.d("GPS수신......Y : ", String.valueOf(arg0.longitude));
                            map.addMarker(markerOptions).showInfoWindow();
                            USER_X = arg0.latitude;
                            USER_Y = arg0.longitude;
                            CuttrntLocation = new LatLng(USER_X, USER_Y);
                            // sharedPreference.put(sharedPreference.user_x, String.valueOf(arg0.latitude)); //서버에 넘겨줄 좌표값
                            //sharedPreference.put(sharedPreference.user_y, String.valueOf(arg0.longitude));
                        }
                    });
                    map.setOnMapLongClickListener(new com.google.android.gms.maps.GoogleMap.OnMapLongClickListener() {
                        @Override
                        public void onMapLongClick(LatLng latLng) {
                            map.clear();
                        }
                    });
                    map.moveCamera(CameraUpdateFactory.newLatLng(CuttrntLocation));
                    map.animateCamera(CameraUpdateFactory.zoomTo(17));
                }
            }
        });
        alert.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }
        });
        alert.show();
    }

    private void setCuttrntLocation(){
        if (gpsService.isGetLocation()) {
            USER_X = gpsService.getLatitude();
            USER_Y = gpsService.getLongitude();
            //sharedPreference.put(sharedPreference.user_x, String.valueOf(x)); //서버에 넘겨줄 좌표값
            //sharedPreference.put(sharedPreference.user_y, String.valueOf(y));
            // Creating a LatLng object for the current location
            CuttrntLocation = new LatLng(USER_X, USER_Y);
            Log.d("GPS수신......X : ", String.valueOf(USER_X));
            Log.d("GPS수신......Y : ", String.valueOf(USER_Y));
            map.moveCamera(CameraUpdateFactory.newLatLng(CuttrntLocation));

            map.animateCamera(CameraUpdateFactory.zoomTo(17));

            // 마커 설정.
            MarkerOptions optFirst = new MarkerOptions();
            optFirst.position(CuttrntLocation);// 위도 • 경도
            optFirst.title("회원님의 위치입니다.");// 제목 미리보기
            optFirst.snippet("요기!");
            optFirst.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
            map.addMarker(optFirst).showInfoWindow();
        } else {
            // GPS 를 사용할수 없으므로
            gpsService.showSettingsAlert();
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        if (!mResolvingError) { // more about this later
            Log.d("구글맵", "onStart connect");
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RESOLVING_ERROR, mResolvingError);
    }
    @Override
    public void onStop() {
        Log.d("구글맵", "온스탑 ");
        mGoogleApiClient.disconnect();
        stopGps();
        super.onStop();
    }

    //안맞는 버전때문에 넣어놈
    @Override
    public void onMapReady(GoogleMap googleMap) {
        //안드 6.0 달라진 퍼미션
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
        Log.d("구글맵", "온맵레디");
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }
    }

    //최초 한번만 현위치 잡음
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("구글맵", "온커넥티드");
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            CuttrntLocation = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            Log.d("구글맵", "현재위치 저장했음" + mLastLocation.getLatitude() + "/" + mLastLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLng(CuttrntLocation));
            // Map 을 zoom 합니다.
            map.animateCamera(CameraUpdateFactory.zoomTo(13));
            //map.setMyLocationEnabled(true);
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }


    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d("구글맵", "온커넥션서스펜드");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("구글맵", "온커넥션페일드");
    }
    //위치정보 바뀔때마다 위치 갱신함
    @Override
    public void onLocationChanged(Location location) {
        Log.d("구글맵", "온로케이션체인지드");
        gpsService.stopUsingGPS();
        stopGps();
    }

    @Override
    public void onDestroy()
    {
        Log.d("구글맵", "온디스트로이");
        gpsService.stopUsingGPS();
        stopGps();
        super.onDestroy();

    }
    public void stopGps()
    {
        gpsService.stopUsingGPS();
        Log.d("구글맵", "스탑지피에스");
        if (this.mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(this.mGoogleApiClient, this);
        }
        this.mGoogleApiClient.disconnect();
    }
}
