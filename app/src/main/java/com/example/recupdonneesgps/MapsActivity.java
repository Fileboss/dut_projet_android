package com.example.recupdonneesgps;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ClientDbHelper dbLoca;
    private int valeurId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        this.dbLoca = new ClientDbHelper(this);
        Intent myIntent = getIntent();
        this.valeurId = Integer.parseInt(myIntent.getStringExtra("id"));
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

        SQLiteDatabase readableDb = this.dbLoca.getReadableDatabase();
        Cursor curs = readableDb.rawQuery("SELECT long, lat FROM LOCATION WHERE id="+this.valeurId+";", null);
        curs.moveToFirst();

        mMap = googleMap;
        //Toast.makeText(this, this.valeurId, Toast.LENGTH_SHORT);

        // Add a marker in Sydney and move the camera
        Toast.makeText(this, curs.getString(curs.getColumnIndexOrThrow("long")), Toast.LENGTH_SHORT);
        LatLng positionVoiture = new LatLng(Float.parseFloat(curs.getString(curs.getColumnIndexOrThrow("lat"))), Float.parseFloat(curs.getString(curs.getColumnIndexOrThrow("long"))));
        mMap.addMarker(new MarkerOptions().position(positionVoiture).title("Marker is cabanac"));
        /*mMap.moveCamera(CameraUpdateFactory.newLatLng(positionVoiture));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionVoiture, 15f));*/
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(positionVoiture, 15f));
    }
}