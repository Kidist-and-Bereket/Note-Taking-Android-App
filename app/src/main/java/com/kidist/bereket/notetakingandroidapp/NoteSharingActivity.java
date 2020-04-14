package com.kidist.bereket.notetakingandroidapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.io.IOException;

public class NoteSharingActivity extends Activity implements View.OnClickListener {

    Button btnShareFacebook;
    String NoteContentToShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_sharing);

        btnShareFacebook = findViewById(R.id.btnShareFacebook);
        btnShareFacebook.setOnClickListener(this);

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
    }

    private Bitmap ChangeGivenTextToBitmap(String text) throws IOException {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize((float) 40);
        paint.setColor(Color.WHITE);
        paint.setTextAlign(Paint.Align.LEFT);

        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = 350;//(int) (baseline + paint.descent() + 0.5f);

        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);

        return image;
    }
}
