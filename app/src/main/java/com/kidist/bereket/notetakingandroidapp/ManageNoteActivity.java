package com.kidist.bereket.notetakingandroidapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;
import com.kidist.bereket.notetakingandroidapp.dbhelpers.AppDatabase;
import com.kidist.bereket.notetakingandroidapp.entities.NoteEntity;
import com.kidist.bereket.notetakingandroidapp.utilities.NoteListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ManageNoteActivity extends Activity implements View.OnClickListener {

    Context context;
    EditText edtNote;
    Button btnSave, btnCancel;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_note);

        linearLayout = findViewById(R.id.linearLayout);
        edtNote = findViewById(R.id.edtNote);

        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        context = this;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnSave.getId()){
            if(edtNote.getText().toString().trim().equals("")){
                Snackbar.make(linearLayout, "Please type something before saving.", Snackbar.LENGTH_LONG).show();
            }
            else{
                new AgentAsyncTask().execute(edtNote.getText().toString());
                //finish();
                Intent intent = new Intent(context, ListOfNoteActivity.class);
                startActivity(intent);
            }
        }
        else {
            finish();
        }
    }

    private class AgentAsyncTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... params) {
            AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "NotesDB").build();

            NoteEntity noteEntity = new NoteEntity();
            noteEntity.CreatedDate = new Date();
            noteEntity.Content = params[0];

            db.noteDAO().InsertNote(noteEntity);

            return 0;
        }

        @Override
        protected void onPostExecute(Integer agentsCount) {

        }
    }
}
