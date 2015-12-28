package com.card_database.mtg.carddatabasemtg;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.card_database.mtg.carddatabasemtg.price.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DownloadJsonTask extends AsyncTask<String, Void, Void> {
    int pages = 158;
    MainActivity activity = null;
    Context context;
    SQLiteDatabase database;

    public static boolean WantToDownload;

    public static final String TAG = "DownloadJsonTask";

    DownloadJsonTask(MainActivity activity, Context context, boolean isWantToDownload) {
        attachActivity(activity);
        attachContext(context);
        setWantToDownload(isWantToDownload);
        activity.search.setEnabled(false);
        activity.nameField.setEnabled(false);
    }

    public void setWantToDownload(boolean wantToDownload) {
        WantToDownload = wantToDownload;
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
            if(!WantToDownload)
                return null;
            Log.d(TAG, "Start download");
            ArrayList<Card> cards = new ArrayList<Card>();
            CardbaseHelper cardbaseHelper = new CardbaseHelper(context);
            cardbaseHelper.dropDb();
            database = cardbaseHelper.getWritableDatabase();
            CardFileImporter cardFileImporter = new CardFileImporter(database);

            for(int i = 0; i < pages; i++) {
                URL url = new URL(strings[0] + "?page=" + i);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();

                cards = JSONParse.JsonReadCards(in);
                cardFileImporter.importCards(cards);
            }
            cardFileImporter.closeStatment();

            database = CardbaseHelper.getInstance(context).getReadableDatabase();
            /*
            String[] fields = {DatabaseContract.CARD_NAME_COLLUMN};
            String[] args = {"'%_____%'", "'%1996 World Champion" + "%'"};

            Cursor cursor = database.query(
                    DatabaseContract.ScriptCards.TABLE,
                    fields,
                    DatabaseContract.CARD_NAME_COLLUMN + " IN (?, ?)",
                    args,
                    null,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            String cardName = cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_NAME_COLLUMN));
            */
            Log.d(TAG, "Done");
        } catch (Exception e) {
            Log.d(TAG, "Exception : " + e.getMessage());
        }

        return null;
    }

    protected void onPostExecute(Void result) {
        activity.search.setEnabled(true);
        activity.nameField.setEnabled(true);
        activity.setDatabase(this.database);
    }
}
