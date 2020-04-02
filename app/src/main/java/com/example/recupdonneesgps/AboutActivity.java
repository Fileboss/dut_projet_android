package com.example.recupdonneesgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

public class AboutActivity extends AppCompatActivity {

    //nom des clefs préférences pour les préférences utilisateur
    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //on recupère le theme sauvegardé dans les userpreferences pour l'appli (cf. SettingsActivity)
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeUsed = preferences.getInt(PREF_DARK_THEME, R.style.AppTheme_Dark);
        this.setTheme(themeUsed);
        //////////////////////////////////////////////////////////////////////////////////////////////

        setContentView(R.layout.activity_about);

        //affichage du bouton retour(menu)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    //action du bouton retour(menu)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
