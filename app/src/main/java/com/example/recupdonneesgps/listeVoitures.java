package com.example.recupdonneesgps;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class listeVoitures extends AppCompatActivity {

    private ClientDbHelper dbLoca;
    ArrayAdapter<String> adapter;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 98;

    public void onClickAjouter(View v) {
        startActivityForResult(new Intent(this, MainActivity.class), 55);
    }

    /*public void onClickListViewItem(View v) {
        Intent myIntent = new Intent(this, MapsActivity.class);
        myIntent.putExtra("id", "2");

        startActivity(myIntent);
    }*/

    private void afficherVoitures() {
        Toast.makeText(this, "Afficher voiture", Toast.LENGTH_SHORT).show();
        SQLiteDatabase readableDb = this.dbLoca.getReadableDatabase();

        ListView listeVoiture = findViewById(R.id.textVoiture);
        List<Couple> valeursVoit = new ArrayList<>();
        Cursor curs = readableDb.rawQuery("SELECT * FROM LOCATION;", null);
        while(curs.moveToNext()) {
            Couple<String, String> myCouple = new Couple<>(curs.getString(curs.getColumnIndexOrThrow("id")), curs.getString(curs.getColumnIndexOrThrow("nomVoiture")));
            valeursVoit.add(myCouple);
        }
        this.adapter = new VoituresAdapter(this,0, valeursVoit);
        listeVoiture.setAdapter(this.adapter);
    }

    public void supprimerVoiture(String id) {
        Toast.makeText(this, "suprimer voiture", Toast.LENGTH_SHORT).show();
        SQLiteDatabase writableDb = this.dbLoca.getWritableDatabase();
        writableDb.delete("Location", "id="+id, null);
        this.afficherVoitures();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(checkLocationPermission())
        checkStorageLocation();


        setContentView(R.layout.activity_liste_voitures);
        this.dbLoca = new ClientDbHelper(this);
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
        if(requestCode==55);{
            boolean valeur = data.getBooleanExtra("addedInDB", false);
            if(valeur) {
                ListView listeVoiture = findViewById(R.id.textVoiture);
                /*if (listeVoiture.getChildCount() > 0)
                    listeVoiture.removeAllViews(); */
                this.afficherVoitures();
                Toast.makeText(this, "WOW GENE", Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_parametres :
                break;
            case R.id.menu_about :
                Intent myIntent = new Intent(this, AboutActivity.class);
                startActivity(myIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // Code copié d'internet permettant de demander proprement l'autorisation d'accès à la position GPS
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
    }

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

}