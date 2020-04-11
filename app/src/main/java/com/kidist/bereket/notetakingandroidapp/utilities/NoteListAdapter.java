package com.kidist.bereket.notetakingandroidapp.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kidist.bereket.notetakingandroidapp.R;
import com.kidist.bereket.notetakingandroidapp.entities.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends ArrayAdapter<NoteEntity> {
    public NoteListAdapter(Context context, List<NoteEntity> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        NoteEntity noteEntity = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.note_list, parent, false);
        }

        // Lookup view for data population
        TextView tvwCreatedDate = (TextView) convertView.findViewById(R.id.tvwCreatedDate);
        TextView tvwContent = (TextView) convertView.findViewById(R.id.tvwContent);

        if(noteEntity != null){
            // Populate the data into the template view using the data object
            tvwCreatedDate.setText(noteEntity.CreatedDate.toString());
            tvwCreatedDate.setTag(noteEntity.Id);
            tvwContent.setText(noteEntity.Content);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}