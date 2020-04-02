package com.example.recupdonneesgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AjouterVoitureActivity extends AppCompatActivity implements LocationListener {

    protected LocationManager locationManager;
    TextView txtLat; //textview latitude longitude actuelle
    private ClientDbHelper dbLoca;
    private Location currentLoca;
    private boolean locationSetFirstTime;

    //nom des clefs préférences pour les préférences utilisateur
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "theme";


    //methode qui ferme l'activité avec un boolean b dans son Intent
    //on passe vrai à l'activité principale si : on a sauvegardé dans la BDD une position
    //faux si on a pas ajouté de position
    private void closeWithResultBool(Boolean b) {
        Intent iRetour = new Intent();
        iRetour.putExtra("addedInDB", b);
        this.setResult(55, iRetour);
        finish();
    }

    /* OnClick du bouton developpeur pour reset la BDD
    public void surLeClickClearDbDev(View v) {
        this.dbLoca.onUpgrade(this.dbLoca.getWritableDatabase(), 1,1);
        closeWithResultBool(true);
    }
    */

    //methode d'ajout d'une position d'une voiture dans la BDD
    //sur le clique du bouton "Sauvegarder"
    public void surLeClique(View v) {
        EditText e = findViewById(R.id.nomVoiture);
        SQLiteDatabase writableDb = this.dbLoca.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nomVoiture", e.getText().toString());
        cv.put("Long", this.currentLoca.getLongitude());
        cv.put("Lat", this.currentLoca.getLatitude());
        writableDb.insert("Location", null, cv);
        Toast.makeText(this, "Voiture " + e.getText().toString() + " ajoutée.", Toast.LENGTH_SHORT).show();
        //réinitialise le texte de la voiture
        e.setText("");

        closeWithResultBool(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //on recupère le theme sauvegardé dans les userpreferences pour l'appli (cf. SettingsActivity)
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeUsed = preferences.getInt(PREF_DARK_THEME, R.style.AppTheme_Dark);
        this.setTheme(themeUsed);
        //////////////////////////////////////////////////////////////////////////////////////////////

        setContentView(R.layout.activity_ajouter_voiture);

        //affichage du bouton retour(menu)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button b = findViewById(R.id.buttonSave);
        this.locationSetFirstTime = false;
        b.setEnabled(false);


        this.dbLoca = new ClientDbHelper(this);

        this.txtLat = findViewById(R.id.textview1);

        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //on ne check pas les permissions dans l'API 22 il suffit de les déclarer dans le manifest
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        if(!this.locationSetFirstTime) {
            EditText textNomVoit = findViewById(R.id.nomVoiture);
            //la zone de texte du nom de la voiture ne recoit pas son Listener
            //avant que la position soit mise à jour pour la première fois
            //cela rend le bouton "Sauvegardé" grisé tant que la position n'est pas récupérée
            textNomVoit.addTextChangedListener(new EcouteurNomVoiture(this));
            //permet de n'executer ce code qu'une seule fois
            this.locationSetFirstTime = true;
        }
        //affiche la position Latitude, Longitude à l'utilisateur
        this.txtLat = findViewById(R.id.textview1);
        this.txtLat.setText("Latitude:" + location.getLatitude() + "\nLongitude:" + location.getLongitude());
        this.currentLoca = location;
    }

    //Pour le debugging
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.d("Latitude","status");
    }

    //Pour le debugging
    @Override
    public void onProviderEnabled(String s) {
        Log.d("Latitude","enable");
    }

    //Pour le debugging
    @Override
    public void onProviderDisabled(String s) {
        Log.d("Latitude","disable");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.dbLoca.close();
    }

    //si le bouton retour est appuyé alors on ferme l'appli avec le boolean faux
    @Override
    public void onBackPressed() {
        closeWithResultBool(false);
    }

    //si le bouton retour(menu) est appuyé alors on ferme l'appli avec le boolean faux
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        closeWithResultBool(false);
        return true;
    }




}
