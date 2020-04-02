package com.example.recupdonneesgps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import java.util.List;

public class VoituresAdapter extends ArrayAdapter {
    public VoituresAdapter(Context context, int resource, List<Couple> voitures) {
        super(context, 0, voitures);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // recupere les valeurs pour cette position
        final Couple<String, String> valeurs = (Couple) getItem(position);
        // on regarde si il y a deja une view sinon on en creer une
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_text_voiture, parent, false);
        }
        // on recupere le textview de convertView (du layout layout_text_voiture)
        TextView voitureName = convertView.findViewById(R.id.textViewYEET);
        voitureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getContext(), OpenMapsActivity.class);
                myIntent.putExtra("id", valeurs.getFirst());
                myIntent.putExtra("nomVoiture", valeurs.getSecond());
                getContext().startActivity(myIntent);
            }
        });


        Button b = convertView.findViewById(R.id.btn_supr);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myPopup = new AlertDialog.Builder(getContext());
                myPopup.setTitle(getContext().getString(R.string.popup_title)+" "+valeurs.getSecond()+" ?");
                myPopup.setPositiveButton(R.string.popup_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ListeVoituresActivity l = (ListeVoituresActivity) getContext();
                        l.supprimerVoiture(valeurs.getFirst());
                    }
                });
                myPopup.setNegativeButton(R.string.popup_negative, null);
                myPopup.show();


            }
        });

        voitureName.setText(valeurs.getSecond());

        // on revoie la vue à afficher
        return convertView;

    }
}