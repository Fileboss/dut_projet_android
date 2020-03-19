package com.example.recupdonneesgps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Layout;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "prefs";
    private static final String PREF_DARK_THEME = "theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int themeUsed = preferences.getInt(PREF_DARK_THEME, R.style.AppTheme_Dark);
        this.setTheme(themeUsed);
        setContentView(R.layout.activity_about);
    }
}
