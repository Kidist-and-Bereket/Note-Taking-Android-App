package com.kidist.bereket.notetakingandroidapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.kidist.bereket.notetakingandroidapp.dbhelpers.AppDatabase;
import com.kidist.bereket.notetakingandroidapp.entities.NoteEntity;
import com.kidist.bereket.notetakingandroidapp.utilities.NoteListAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListOfNoteActivity extends AppCompatActivity {

    public Context context;
    public AppDatabase db;
    public ListView lstvwNoteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManageNoteActivity.class);
                startActivity(intent);
            }
        });

        LoadNotes();
    }

    public void LoadNotes(){
        lstvwNoteList = (ListView) findViewById(R.id.lstvwNoteList);
        db = Room.databaseBuilder(this,
                AppDatabase.class, "NotesDB").build();

        new AgentAsyncTask().execute();
    }

    private class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            List<NoteEntity> noteEntities = new ArrayList<>();
            noteEntities = db.noteDAO().GetAll();

            NoteListAdapter adapter = new NoteListAdapter(context, noteEntities);

            lstvwNoteList.setAdapter(adapter);

            return 0;
        }

        @Override
        protected void onPostExecute(Integer agentsCount) {

        }
    }
}
