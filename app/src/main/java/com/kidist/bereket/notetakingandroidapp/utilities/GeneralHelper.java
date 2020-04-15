package com.kidist.bereket.notetakingandroidapp.utilities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import com.kidist.bereket.notetakingandroidapp.NoteSharingActivity;

import java.io.IOException;
import java.util.List;

public class GeneralHelper {

    public static void SendEmail(Context context, String[] recipient, String[] carbonCopied, String subject, String message) {
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setType("message/rfc822");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private static Intent GetApplicationPostActivity(String activityName, Intent tweetIntent, Context context){
        PackageManager packManager = context.getPackageManager();
        List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent,  PackageManager.MATCH_ALL);

        for(ResolveInfo resolveInfo: resolvedInfoList){
            if(resolveInfo.activityInfo.packageName.startsWith(activityName)){
                tweetIntent.setClassName(
                        resolveInfo.activityInfo.packageName,
                        resolveInfo.activityInfo.name );

                return tweetIntent;
            }
        }

        return null;
    }

    public static boolean IsPackageExist(Context context, String targetPackage){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo info=pm.getPackageInfo(targetPackage,PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void ShareMessageOnTwitter(Context context, String msg)
    {
        final String appName = "com.twitter.android";
        final boolean result = IsPackageExist(context.getApplicationContext(), appName);
        if (result)
        {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, msg);//

            context.startActivity(Intent.createChooser(myIntent, "Share with"));
        }
        else
        {
            OpenTwitterOnline(context, msg);
        }
    }

    private static void OpenTwitterOnline(Context context, String shareText){
        String tweetUrl = "https://twitter.com/intent/tweet?text=" + shareText;
        Uri uri = Uri.parse(tweetUrl);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(Intent.createChooser(shareIntent, "Tweet your note . . ."));
    }

    public static void ShareMessageUsingSMS(Context context, String message){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"));
            sendIntent.putExtra("sms_body", message);

            context.startActivity(sendIntent);
        }
        else{
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setType("vnd.android-dir/mms-sms");
            sendIntent.putExtra("sms_body",message);

            context.startActivity(sendIntent);
        }
    }

    public static void ShareMessageOnTelegram(Context context, String message)
    {
        final String appName = "org.telegram.messenger";
        final boolean result = IsPackageExist(context.getApplicationContext(), appName);
        if (result)
        {
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("text/plain");
            myIntent.setPackage(appName);
            myIntent.putExtra(Intent.EXTRA_TEXT, message);//

            context.startActivity(Intent.createChooser(myIntent, "Share with"));
        }
        else
        {
            Toast.makeText(context, "Telegram not Installed", Toast.LENGTH_SHORT).show();
        }
    }

    public static Bitmap ChangeGivenTextToBitmap(String text) throws IOException {
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
