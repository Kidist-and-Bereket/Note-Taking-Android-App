package com.kidist.bereket.notetakingandroidapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NoteSharingActivity extends Activity implements View.OnClickListener {

    Button btnShareFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_sharing);

        btnShareFacebook = findViewById(R.id.btnShareFacebook);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnShareFacebook.getId()){

        }
    }
}
