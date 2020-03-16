package com.example.recupdonneesgps;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

public class VoituresAdapter extends ArrayAdapter {
    public VoituresAdapter(Context context, int resource, List<Couple> voitures) {
        super(context, 0, voitures);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Couple s = (Couple) getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_text_voiture, parent, false);
        }
        // Lookup view for data population
        TextView voitureName = convertView.findViewById(R.id.textViewYEET);
        // Populate the data into the template view using the data object
        voitureName.setText(s.getSecond().toString());
        // Return the completed view to render on screen

        return convertView;

    }
}
