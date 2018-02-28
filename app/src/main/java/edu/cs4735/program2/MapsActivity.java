package edu.cs4735.program2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.File;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, pic.OnPicFragmentInteractionListener {

    public static final int REQUEST_ACCESS_onConnected = 1;

    public static final int REQUEST_PERM_ACCESS = 1;

    public SupportMapFragment mapFragment;

    public MapsActivity mapsActivity;

    private Marker previousMarker = null;

    private GoogleMap mMap;
    String TAG = "main/map";
    public String sLoc;

    Location mLastLocation;

    public pic mpic;

    private FusedLocationProviderClient mFusedLocationClient;

    double lat, lon;

    File file;

    @Override
    public void onPause()
    {

        //Map.
        super.onPause();
    }

    @Override
    public void onResume() {

        Log.e("AAAAAAAAAAAAA", "FUCK");
        //Map.
        onMapReady(mMap);
        super.onResume();

        //mapFragment = (SupportMapFragment) getSupportFragmentManager()
        //        .findFragmentById(R.id.map);
        //mapFragment.getMapAsync(this);

        //getSupportFragmentManager().beginTransaction().replace(R.id.container, mapsActivity).commit();
//        mMap.clear();
    }

    @Override
    public void onRestart() {

        //Map.
        super.onRestart();
        onMapReady(mMap);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapsActivity = this;

        CheckPerm();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //create FAB and if pressed, call popup
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camera c = new camera();
                addMark();
                c.s = sLoc;
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, c).addToBackStack(null).commit();



            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    public void CheckPerm() {
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            //I'm on not explaining why, just asking for permission.
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MapsActivity.REQUEST_PERM_ACCESS);
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            //I'm on not explaining why, just asking for permission.
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MapsActivity.REQUEST_ACCESS_onConnected);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mLastLocation = location;
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            Log.e(TAG, lat + " " + lon);

                            LatLng init = new LatLng(lat, lon);
                            //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(init));
                        }
                    }
                });

    }



    public void addMark(){

        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) &&
                (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            //I'm on not explaining why, just asking for permission.
            Log.v(TAG, "asking for permissions");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    MapsActivity.REQUEST_ACCESS_onConnected);
            return;
        }
        //mLoc = mMap.g
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            mLastLocation = location;
                            lat = location.getLatitude();
                            lon = location.getLongitude();
                            Log.e(TAG, lat + " " + lon);

                            LatLng newLoc = new LatLng(lat, lon);

                            mMap.addMarker(new MarkerOptions().position(newLoc)
                                    .title("Location " + lat + ", " + lon));

                            mMap.moveCamera(CameraUpdateFactory.newLatLng(newLoc));

                            sLoc = "Location " + lat + ", " + lon;
                        }
                    }
                });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                String locAddress = marker.getTitle();
                Log.e("FUCKASS", locAddress);
                if (previousMarker != null) {
                    previousMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }
                marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                previousMarker = marker;

                mpic = new pic(locAddress);
                //mpic.setTV(locAddress);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, mpic).addToBackStack(null).commit();

                return true;
            }
        });
    }


    @Override
    public void onPicFragmentInteraction() {
        Log.e("um", "hello");

    }
}
