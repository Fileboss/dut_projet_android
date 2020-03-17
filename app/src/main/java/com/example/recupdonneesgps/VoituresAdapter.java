package com.example.recupdonneesgps;

import android.content.Context;
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
                Toast.makeText(getContext(), "MARIE", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(getContext(), MapsActivity.class);
                myIntent.putExtra("id", s.getFirst());
                //Envoyer le string à la map

                getContext().startActivity(myIntent);
            }
        });


        Button b = convertView.findViewById(R.id.btn_supr);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listeVoitures l = (listeVoitures) getContext();
                l.supprimerVoiture(s.getFirst());
            }
        });
        // Populate the data into the template view using the data object
        voitureName.setText(s.getSecond().toString());
        // Return the completed view to render on screen

        return convertView;

    }
}
