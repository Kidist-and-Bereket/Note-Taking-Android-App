package com.kidist.bereket.notetakingandroidapp;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kidist.bereket.notetakingandroidapp.utilities.GeneralHelper;

import java.io.IOException;

public class NoteSharingActivity extends Activity implements View.OnClickListener {

    Button btnShareFacebook, btnSendEmail, btnShareTwitter, btnShareTelegram, btnSendSMS;
    String NoteCreatedDate, NoteContentToShare;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_sharing);

        context = this;

        btnShareFacebook = findViewById(R.id.btnShareFacebook);
        btnShareFacebook.setOnClickListener(this);
        btnSendEmail = findViewById(R.id.btnSendEmail);
        btnSendEmail.setOnClickListener(this);
        btnShareTwitter = findViewById(R.id.btnShareTwitter);
        btnShareTwitter.setOnClickListener(this);
        btnShareTelegram = findViewById(R.id.btnShareTelegram);
        btnShareTelegram.setOnClickListener(this);
        btnSendSMS = findViewById(R.id.btnSendSMS);
        btnSendSMS.setOnClickListener(this);

        NoteCreatedDate = getIntent().getStringExtra("NoteCreatedDate");
        NoteContentToShare = getIntent().getStringExtra("NoteContent");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
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
        else if(v.getId() == btnShareTwitter.getId()){
            GeneralHelper.ShareMessageOnTwitter(this, NoteContentToShare);
        }
        else if(v.getId() == btnShareTelegram.getId()){
            GeneralHelper.ShareMessageOnTelegram(this, NoteContentToShare);
        }
        else if(v.getId() == btnSendSMS.getId()){
            GeneralHelper.ShareMessageUsingSMS(this, NoteContentToShare);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        if (requestCode == 100) {
            GeneralHelper.ShareMessageUsingSMS(this, NoteContentToShare);
        } else {
            Toast.makeText(context, "Send SMS Permission is denied.", Toast.LENGTH_LONG).show();
        }
    }
}
