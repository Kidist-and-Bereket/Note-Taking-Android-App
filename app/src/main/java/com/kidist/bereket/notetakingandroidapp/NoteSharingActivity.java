package com.kidist.bereket.notetakingandroidapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kidist.bereket.notetakingandroidapp.utilities.GeneralHelper;

import java.io.IOException;

public class NoteSharingActivity extends Activity implements View.OnClickListener {

    Button btnShareFacebook, btnSendEmail;
    String NoteCreatedDate, NoteContentToShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_sharing);

        btnShareFacebook = findViewById(R.id.btnShareFacebook);
        btnShareFacebook.setOnClickListener(this);
        btnSendEmail = findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(this);

        NoteCreatedDate = getIntent().getStringExtra("NoteCreatedDate");
        NoteContentToShare = getIntent().getStringExtra("NoteContent");
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btnShareFacebook.getId()){
            try {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("NoteContent", NoteContentToShare);
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);

                ShareLinkContent content = new ShareLinkContent.Builder()
                        .build();

                Toast.makeText(this, "The content is copied to clipboard", Toast.LENGTH_LONG).show();

                ShareDialog.show(this, content);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if(v.getId() == btnSendEmail.getId()){
            String subject = "Note taken on " + NoteCreatedDate;
            GeneralHelper.SendEmail(this, new String[]{""}, new String[]{""}, subject, NoteContentToShare);
        }
    }

}
