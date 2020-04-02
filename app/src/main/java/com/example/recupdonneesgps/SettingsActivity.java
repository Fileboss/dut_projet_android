package com.example.recupdonneesgps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import androidx.appcompat.app.AppCompatActivity;


public class SettingsActivity extends AppCompatActivity {

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

        setContentView(R.layout.activity_settings);
        //affichage du bouton retour(menu)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //quand on arrive dans l'activité Settings on récupère le theme en cours d'utilisation
        //pour bien afficher le bon bouton sélectionné du RadioGroup
        switch(themeUsed) {
            case R.style.AppTheme_Dark:
                ((RadioButton) findViewById(R.id.radioDarkTheme)).setChecked(true);
                break;
            case R.style.AppTheme_Light:
                ((RadioButton) findViewById(R.id.radioLightTheme)).setChecked(true);
                break;
            case R.style.AppTheme_Crazy:
                ((RadioButton) findViewById(R.id.radioCrazyTheme)).setChecked(true);
                break;
        }

        ////////////
        //creation d'un ecouteur de changement de bouton dans le radioGroup
        //il change l'id de la map "theme" pour le theme qui a été selectionné dans le radioGroup
        RadioGroup rg = findViewById(R.id.themeRadioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg, int i) {
                int id;
                SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                switch(rg.getCheckedRadioButtonId()) {
                    case R.id.radioLightTheme:
                        id = R.style.AppTheme_Light;
                        break;
                    case R.id.radioDarkTheme:
                        id = R.style.AppTheme_Dark;
                        break;
                    case R.id.radioCrazyTheme:
                        id = R.style.AppTheme_Crazy;
                        break;
                    default:
                        id = -1;
                        break;
                }
                editor.putInt(PREF_DARK_THEME, id);
                //sauvegarde dans les préférences utilisateur
                editor.apply();

                //permet de recharger la page courante (et donc de mettre à jour le theme)
                finish();
                overridePendingTransition(0, 0); //supprime l'animation de fermeture
                startActivity(getIntent());
                overridePendingTransition(0, 0); //supprime l'animation d'ouverture
            }
        });
        //////////////


    }

    //action du bouton retour(menu)
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}
