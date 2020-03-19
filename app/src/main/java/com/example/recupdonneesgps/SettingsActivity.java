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
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeUsed = preferences.getInt(PREF_DARK_THEME, R.style.AppTheme_Dark);
        this.setTheme(themeUsed);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        RadioGroup rg = findViewById(R.id.themeRadioGroup);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup rg, int i) {
                int id = 0;
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
                }
                editor.putInt(PREF_DARK_THEME, id);
                editor.apply();

                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        //////////////


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}
