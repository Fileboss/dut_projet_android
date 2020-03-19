package com.example.recupdonneesgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;


public class OpenMapsActivity extends AppCompatActivity {

    private MapView myOpenMapView;
    private int valeurId;
    private String valeurNomVoiture;
    private ClientDbHelper dbLoca;

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "theme";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeUsed = preferences.getInt(PREF_DARK_THEME, R.style.AppTheme_Dark);
        this.setTheme(themeUsed);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        setContentView(R.layout.activity_open_maps);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.dbLoca = new ClientDbHelper(this);
        Intent myIntent = getIntent();
        this.valeurId = Integer.parseInt(myIntent.getStringExtra("id"));
        this.valeurNomVoiture = myIntent.getStringExtra("nomVoiture");

        SQLiteDatabase readableDb = this.dbLoca.getReadableDatabase();
        Cursor curs = readableDb.rawQuery("SELECT long, lat FROM LOCATION WHERE id="+this.valeurId+";", null);
        curs.moveToFirst();

        myOpenMapView = (MapView)findViewById(R.id.mapview);
        myOpenMapView.setBuiltInZoomControls(true);
        myOpenMapView.setClickable(true);
        myOpenMapView.getController().setZoom(15);
        myOpenMapView.setBuiltInZoomControls(true);

        GeoPoint test = new GeoPoint(Float.parseFloat(curs.getString(curs.getColumnIndexOrThrow("lat"))), Float.parseFloat(curs.getString(curs.getColumnIndexOrThrow("long"))));
        myOpenMapView.getController().setCenter(test);

        Marker tec = new Marker(myOpenMapView);
        tec.setPosition(test);
        tec.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        tec.setIcon(getDrawable(R.drawable.marker_medium));
        tec.setTitle(this.valeurNomVoiture);
        myOpenMapView.getOverlays().add(tec);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.dbLoca.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }


}
