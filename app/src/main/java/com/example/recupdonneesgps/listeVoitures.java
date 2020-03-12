package com.example.recupdonneesgps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class listeVoitures extends AppCompatActivity {

    private ClientDbHelper dbLoca;

    public void onClickAjouter(View v) {
        startActivityForResult(new Intent(this, MainActivity.class), 55);
    }

    private void afficherVoitures() {
        SQLiteDatabase readableDb = this.dbLoca.getReadableDatabase();

        ListView listeVoiture = findViewById(R.id.dansLeScroll);
        List<String> valeursVoit = new ArrayList<>();
        Cursor curs = readableDb.rawQuery("SELECT * FROM LOCATION;", null);
        while(curs.moveToNext()) {
            valeursVoit.add(curs.getString(curs.getColumnIndexOrThrow("nomVoiture")));
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
            /*
            if(valeur) {
                ConstraintLayout listeVoiture = findViewById(R.id.dansLeScroll);
                if (listeVoiture.getChildCount() > 0)
                    listeVoiture.removeAllViews();
                this.afficherVoitures();
                //Toast.makeText(this, "WOW GENE", Toast.LENGTH_SHORT).show();


             */

        }
    }
}
