package com.example.recupdonneesgps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class listeVoitures extends AppCompatActivity {

    private ClientDbHelper dbLoca;

    public void onClickAjouter(View v) {
        startActivityForResult(new Intent(this, MainActivity.class), 55);
    }

    public void onClickListViewItem(View v) {
        Intent myIntent = new Intent(this, MapsActivity.class);
        myIntent.putExtra("id", "2");

        startActivity(myIntent);
    }

    private void afficherVoitures() {
        SQLiteDatabase readableDb = this.dbLoca.getReadableDatabase();

        ListView listeVoiture = findViewById(R.id.textVoiture);
        List<String> valeursVoit = new ArrayList<>();
        Cursor curs = readableDb.rawQuery("SELECT * FROM LOCATION;", null);
        while(curs.moveToNext()) {
            valeursVoit.add(curs.getString(curs.getColumnIndexOrThrow("nomVoiture"))+" l'id est : "+curs.getString(curs.getColumnIndexOrThrow("id")));
        }
        ArrayAdapter<String> adapter = new VoituresAdapter(this,0, valeursVoit);
        listeVoiture.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

}
