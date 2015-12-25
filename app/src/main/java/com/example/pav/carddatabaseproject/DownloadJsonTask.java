package com.example.pav.carddatabaseproject;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class DownloadJsonTask extends AsyncTask<String, Void, Void> {
    ArrayList<Card> cards;
    MainActivity activity = null;
    Context context;
    SQLiteDatabase database;

    public static final String TAG = "DownloadJsonTask";

    DownloadJsonTask(MainActivity activity, Context context) {
        attachActivity(activity);
        attachContext(context);
    }

    public void attachActivity(MainActivity activity) {
        this.activity = activity;
    }

    public void attachContext(Context context) {
        this.context = context;
    }

    public SQLiteDatabase returnDatabase() {
        return database;
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            Log.d(TAG, "Start download");
            Log.d(TAG, "Creating file");
            File dest = File.createTempFile("allcards", ".json");

            Log.d(TAG, "Start download in temp file with utils");

            DownloadUtils.downloadFile(strings[0], dest);

            Log.d(TAG, "Begin parsing");

            Log.d(TAG, "Want to begin creating base");
            database = CardbaseHelper.getInstance(context).getWritableDatabase();
            CardFileImporter cardFileImporter = new CardFileImporter(database);
            cardFileImporter.importCards(dest);
        } catch (Exception e) {
            Log.d(TAG, "Exception : " + e.getMessage());
        }

        return null;
    }

    protected void onPostExecute(Void result) {
        activity.setDatabase(this.database);
    }
}
