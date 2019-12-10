package com.emna.snowtamer.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.emna.snowtamer.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity {
    LatLng aero ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //and to get the X & Y ( in this activity ) use this:
        Bundle extras = this.getIntent().getExtras();
        double X = extras.getDouble("X");
        double Y = extras.getDouble("Y");

        aero = new LatLng(X, Y);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                //ajoute un marker sur aeroport
                googleMap.addMarker(new MarkerOptions().title("Aero").position(aero));

                //centre la google map sur aeroport (avec animation de zoom)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(aero, 15));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

            }
        });
    }
}
