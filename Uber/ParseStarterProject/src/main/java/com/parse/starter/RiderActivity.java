package com.parse.starter;



import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class RiderActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Intent i;
    public void back(View view){
        Intent intent = new Intent(getApplicationContext(), ViewRequests.class);
        startActivity(intent);
    }

    public void acceptRequest(View view){


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Requests");
        query.whereEqualTo("requesterUsername", i.getStringExtra("username"));
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if ( e == null){
                    if(objects.size() > 0){
                        for (ParseObject object : objects){
                            object.put("driverUsername", "dname");
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e == null) {
                                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                                Uri.parse("http://maps.google.com/maps?daddr=" + i.getDoubleExtra("latitude", 0) + "," + i.getDoubleExtra("longitude", 0)));
                                        startActivity(intent);
                                    }
                                }
                            });


                        }
                    }
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rider_location);

        i = getIntent();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        ArrayList<Marker> markers = new ArrayList<Marker>();

        LatLng RiderLatLng = new LatLng(i.getDoubleExtra("latitude", 0), i.getDoubleExtra("longitude", 0));
        markers.add(mMap.addMarker(new MarkerOptions().position(RiderLatLng).title("Rider Location")));

        LatLng DriverLatLng = new LatLng(i.getDoubleExtra("Userlatitude", 0), i.getDoubleExtra("Userlongitude", 0));
        markers.add(mMap.addMarker(new MarkerOptions()
                        .position(DriverLatLng)
                        .title("Rider Location")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))));

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for(Marker marker : markers) {
            builder.include(marker.getPosition());
        }
        LatLngBounds bounds = builder.build();

        int padding = 150;

        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        mMap.animateCamera(cu);

    }
}
