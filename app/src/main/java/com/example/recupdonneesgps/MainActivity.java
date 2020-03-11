package com.example.recupdonneesgps;

import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener{

    protected LocationManager locationManager;
    TextView txtLat;
    private ClientDbHelper dbLoca;
    private Location currentLoca;

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
        e.setText("");

        SQLiteDatabase readableDb = this.dbLoca.getReadableDatabase();
        Cursor curs = readableDb.rawQuery("SELECT * FROM LOCATION WHERE id=1;", null);
        curs.moveToFirst();
        Toast.makeText(this, curs.getString(curs.getColumnIndexOrThrow("nomVoiture")), Toast.LENGTH_SHORT).show();

        closeWithResultBool(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = new Intent(this, MainActivity.class);
        setContentView(R.layout.activity_main);

        EditText textNomVoit = findViewById(R.id.nomVoiture);
        textNomVoit.addTextChangedListener(new EcouteurNomVoiture(this));


        this.dbLoca = new ClientDbHelper(this);

        this.txtLat = (TextView) findViewById(R.id.textview1);

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //if (checkPermission()
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        this.txtLat = (TextView) findViewById(R.id.textview1);
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


}
