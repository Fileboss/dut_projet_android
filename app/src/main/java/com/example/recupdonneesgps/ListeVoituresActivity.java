package com.example.recupdonneesgps;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class ListeVoituresActivity extends AppCompatActivity {

    private ClientDbHelper dbLoca;
    ArrayAdapter<String> adapter;
    //public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    //public static final int MY_PERMISSIONS_REQUEST_STORAGE = 98;

    //nom des clefs pour les préférences utilisateur
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "theme";


    //on click du bouton ajouter voiture
    public void onClickAjouter(View v) {
        startActivityForResult(new Intent(this, AjouterVoitureActivity.class), 55);
    }


    //méthode qui réaffiche la liste des voiture avec les données de la BDD
    private void afficherVoitures() {
        SQLiteDatabase readableDb = this.dbLoca.getReadableDatabase();

        ListView listeVoiture = findViewById(R.id.textVoiture);
        //on utilise une liste de "Couple" pour envoyer deux valeurs à l'adapteur
        List<Couple> valeursVoit = new ArrayList<>();
        Cursor curs = readableDb.rawQuery("SELECT * FROM LOCATION;", null);
        while(curs.moveToNext()) {
            Couple<String, String> myCouple = new Couple<>(curs.getString(curs.getColumnIndexOrThrow("id")), curs.getString(curs.getColumnIndexOrThrow("nomVoiture")));
            valeursVoit.add(myCouple);
        }
        this.adapter = new VoituresAdapter(this,0, valeursVoit);
        listeVoiture.setAdapter(this.adapter);
        curs.close();
    }

    //méthode permetant la suppression d'une voiture de la BDD
    //met aussi à jour la liste affichée
    public void supprimerVoiture(String id) {
        SQLiteDatabase writableDb = this.dbLoca.getWritableDatabase();
        writableDb.delete("Location", "id="+id, null);
        this.afficherVoitures();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //on recupère le theme sauvegardé dans les userpreferences pour l'appli (cf. SettingsActivity)
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeUsed = preferences.getInt(PREF_DARK_THEME, R.style.AppTheme_Dark);
        this.setTheme(themeUsed);
        //////////////////////////////////////////////////////////////////////////////////////////////

        setContentView(R.layout.activity_liste_voitures);
        this.dbLoca = new ClientDbHelper(this);
        //on affiche les voiture à la creation de l'activité
        this.afficherVoitures();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.dbLoca = new ClientDbHelper(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.dbLoca.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 55:
                //retour de l'activité AjouterVoiture
                //si une voiture a été ajoutée dans la BDD alors on rafraichit la liste des voitures
                boolean v55 = data.getBooleanExtra("addedInDB", false);
                if(v55) {
                    this.afficherVoitures();
                }
                break;
            case 56:
                //retour de l'activité Settings
                //(on ferme l'activité principale et on l'ouvre à nouveau pour mettre à jour le theme)
                finish();
                startActivity(getIntent());
                break;
        }
    }

    //barre menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    //ecouteurs des items du menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //selon l'item cliqué on démarre une activité différente
        switch (item.getItemId()) {
            case R.id.menu_parametres :
                Intent intentSettings = new Intent(this, SettingsActivity.class);
                startActivityForResult(intentSettings, 56);
                break;
            case R.id.menu_about :
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    // CODE TRAITANT DES PERMISSIONS APRES L'API 22 (inutile dans le cadre de l'évaluation donc commenté)
    /*
    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_location_permission)
                        .setMessage(R.string.text_location_permission)
                        .setPositiveButton(R.string.popup_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(listeVoitures.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }


        }
    }*/
/*
    public boolean checkStorageLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.title_storage_permission)
                        .setMessage(R.string.text_storage_permission)
                        .setPositiveButton(R.string.popup_positive, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(listeVoitures.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_STORAGE);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_STORAGE);
            }
            return false;
        } else {
            return true;
        }
    }
*/
}