package com.card_database.mtg.carddatabasemtg;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.io.File;

/**
 * Helper для создания базы данных
 */
public class CardbaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "appdatabase.db";
    public static final int DATABASE_VERSION = 0;
    private static volatile CardbaseHelper instance;
    private final Context context;

    public static CardbaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (CardbaseHelper.class) {
                if (instance == null) {
                    instance = new CardbaseHelper(context);
                }
            }
        }
        return instance;
    }

    public CardbaseHelper(Context context) {
        super(context, DATABASE_NAME, null /*factory*/, DATABASE_VERSION,
                new DatabaseCorruptionHandler(context, DATABASE_NAME));
        this.context = context.getApplicationContext();
    }

    public void dropDb() {
        SQLiteDatabase db = getWritableDatabase();
        if (db.isOpen()) {
            try {
                db.close();
            } catch (Exception e) {
                Log.w(LOG_TAG, "Failed to close DB");
            }
        }
        final File dbFile = context.getDatabasePath(DATABASE_NAME);
        try {
            Log.d(LOG_TAG, "deleting the database file: " + dbFile.getPath());
            if (!dbFile.delete()) {
                Log.w(LOG_TAG, "Failed to delete database file: " + dbFile);
            }
            Log.d(LOG_TAG, "Deleted DB file: " + dbFile);
        } catch (Exception e) {
            Log.w(LOG_TAG, "Failed to delete database file: " + dbFile, e);
        }
    }

    private static final String LOG_TAG = "CardBase";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DatabaseContract.ScriptCards.CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
