package com.example.recupdonneesgps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManager;
    TextView txtLat;
    private ClientDbHelper dbLoca;
    private Location currentLoca;
    private boolean locationSetFirstTime;

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "theme";


    private void closeWithResultBool(Boolean b) {
        Intent iRetour = new Intent();
        iRetour.putExtra("addedInDB", b);
        this.setResult(55, iRetour);
        finish();
    }

    public void surLeClickClearDbDev(View v) {
        this.dbLoca.onUpgrade(this.dbLoca.getWritableDatabase(), 1,1);
        closeWithResultBool(true);
    }

    public void surLeClique(View v) {
        EditText e = findViewById(R.id.nomVoiture);
        SQLiteDatabase writableDb = this.dbLoca.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nomVoiture", e.getText().toString());
        cv.put("Long", this.currentLoca.getLongitude());
        cv.put("Lat", this.currentLoca.getLatitude());
        writableDb.insert("Location", null, cv);
        Toast.makeText(this, "Voiture " + e.getText().toString() + " ajoutée.", Toast.LENGTH_SHORT).show();
        e.setText("");

        closeWithResultBool(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeUsed = preferences.getInt(PREF_DARK_THEME, R.style.AppTheme_Dark);
        this.setTheme(themeUsed);
        Intent i = new Intent(this, MainActivity.class);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button b = findViewById(R.id.buttonSave);
        this.locationSetFirstTime = false;
        b.setEnabled(false);


        this.dbLoca = new ClientDbHelper(this);

        this.txtLat = findViewById(R.id.textview1);

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //if (checkPermission()

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        if(!this.locationSetFirstTime) {
            Button b = findViewById(R.id.buttonSave);
            EditText textNomVoit = findViewById(R.id.nomVoiture);
            textNomVoit.addTextChangedListener(new EcouteurNomVoiture(this));
            this.locationSetFirstTime = true;
        }
        this.txtLat = findViewById(R.id.textview1);
        this.txtLat.setText("Latitude:" + location.getLatitude() + "\nLongitude:" + location.getLongitude());
        this.currentLoca = location;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.dbLoca.close();
    }

    @Override
    public void onBackPressed() {
        closeWithResultBool(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        closeWithResultBool(false);
        return true;
    }




}
