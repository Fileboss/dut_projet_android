package com.example.recupdonneesgps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class VoituresAdapter extends ArrayAdapter {
    public VoituresAdapter(Context context, int resource, List<Couple> voitures) {
        super(context, 0, voitures);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        // Get the data item for this position
        final Couple<String, String> s = (Couple) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_text_voiture, parent, false);
        }
        // Lookup view for data population
        TextView voitureName = convertView.findViewById(R.id.textViewYEET);
        voitureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(), "MARIE", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getContext(), OpenMapsActivity.class);
                myIntent.putExtra("id", s.getFirst());
                myIntent.putExtra("nomVoiture", s.getSecond());
                getContext().startActivity(myIntent);
            }
        });


        Button b = convertView.findViewById(R.id.btn_supr);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder myPopup = new AlertDialog.Builder(getContext());
                myPopup.setTitle(getContext().getString(R.string.popup_title)+" "+s.getSecond()+" ?");
                myPopup.setPositiveButton(R.string.popup_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listeVoitures l = (listeVoitures) getContext();
                        l.supprimerVoiture(s.getFirst());
                    }
                });
                myPopup.setNegativeButton(R.string.popup_negative, null);
                myPopup.show();


            }
        });
        // Populate the data into the template view using the data object
        voitureName.setText(s.getSecond());
        // Return the completed view to render on screen

        return convertView;

    }
}