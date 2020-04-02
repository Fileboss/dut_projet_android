package com.example.recupdonneesgps;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class EcouteurNomVoiture implements TextWatcher {

    private AppCompatActivity app;

    public EcouteurNomVoiture(AppCompatActivity a) {
        this.app = a;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //permet d'activer/de désactiver le bouton sauvegarder de l'activité AjouterVoiture
        Button bt1 = this.app.findViewById(R.id.buttonSave);
        EditText nomVoiture = this.app.findViewById(R.id.nomVoiture);
        bt1.setEnabled(!nomVoiture.getText().toString().isEmpty());

    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

}

