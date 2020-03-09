package com.example.recupdonneesgps;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class EcouteurNomVoiture implements TextWatcher {

    private AppCompatActivity a;

    public EcouteurNomVoiture(AppCompatActivity a) {
        this.a = a;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Button bt1 = this.a.findViewById(R.id.buttonSave);
        EditText nomVoiture = this.a.findViewById(R.id.nomVoiture);
        if(nomVoiture.getText().toString().isEmpty()) bt1.setEnabled(false); else bt1.setEnabled(true);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }
}

