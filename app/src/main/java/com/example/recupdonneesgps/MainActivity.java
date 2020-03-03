package com.example.recupdonneesgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener{

    protected LocationManager locationManager;
    TextView txtLat;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this, MainActivity.class);
        setContentView(R.layout.activity_main);

        this.txtLat = (TextView) findViewById(R.id.textview1);

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //if (checkPermission()
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        this.txtLat = (TextView) findViewById(R.id.textview1);
        this.txtLat.setText("Latitude:" + location.getLatitude() + "\nLongitude:" + location.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }
}
