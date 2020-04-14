package com.kidist.bereket.notetakingandroidapp.utilities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.room.Room;

import com.kidist.bereket.notetakingandroidapp.ListOfNoteActivity;
import com.kidist.bereket.notetakingandroidapp.ManageNoteActivity;
import com.kidist.bereket.notetakingandroidapp.NoteSharingActivity;
import com.kidist.bereket.notetakingandroidapp.R;
import com.kidist.bereket.notetakingandroidapp.dbhelpers.AppDatabase;
import com.kidist.bereket.notetakingandroidapp.entities.NoteEntity;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends ArrayAdapter<NoteEntity> {
    private Context innerContext;
    private AppDatabase db;

    public NoteListAdapter(Context context, List<NoteEntity> users) {
        super(context, 0, users);
        innerContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final NoteEntity noteEntity = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.note_list, parent, false);
        }

        // Lookup view for data population
        final TextView tvwCreatedDate = (TextView) convertView.findViewById(R.id.tvwCreatedDate);
        final TextView tvwContent = (TextView) convertView.findViewById(R.id.tvwContent);

        if(noteEntity != null){
            // Populate the data into the template view using the data object
            tvwCreatedDate.setText(noteEntity.CreatedDate.toString());
            tvwCreatedDate.setTag(noteEntity.Id);
            tvwContent.setText(noteEntity.Content);
        }

        ImageButton btnEdit = convertView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteID = tvwCreatedDate.getTag().toString();

                Intent intent = new Intent(innerContext, ManageNoteActivity.class);
                intent.putExtra("NoteID", noteID);
                innerContext.startActivity(intent);
            }
        });

        ImageButton btnShare = convertView.findViewById(R.id.btnShare);
        btnShare.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noteContent = tvwContent.getText().toString();

                Intent intent = new Intent(innerContext, NoteSharingActivity.class);
                intent.putExtra("NoteContent", noteContent);
                innerContext.startActivity(intent);
            }
        });

        ImageButton btnDelete = convertView.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String noteID = tvwCreatedDate.getTag().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(innerContext);
                builder.setMessage("Are you sure you want to delete?")
                        .setCancelable(false)
                        .setTitle("Note Taking")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        DeleteNote(Integer.parseInt(noteID));
                                        dialog.cancel();

                                        Intent intent = new Intent(innerContext, ListOfNoteActivity.class);
                                        innerContext.startActivity(intent);
                                    }
                                })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // cancel the dialog box
                                        dialog.cancel();
                                    }
                                });
                builder.show();
            }
        });
        // Return the completed view to render on screen
        return convertView;
    }

    public void DeleteNote(Integer NoteId){
        db = Room.databaseBuilder(innerContext,
                AppDatabase.class, "NotesDB").build();

        new AgentAsyncTask().execute(NoteId.toString());
    }

    private class AgentAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            NoteEntity noteEntity = new NoteEntity();
            noteEntity = db.noteDAO().GetSingleNote(Integer.parseInt(params[0]));

            db.noteDAO().DeleteNote(noteEntity);

            return 0;
        }

        @Override
        protected void onPostExecute(Integer agentsCount) {

        }
    }

}