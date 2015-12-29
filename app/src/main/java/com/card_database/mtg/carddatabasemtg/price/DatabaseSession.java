package com.card_database.mtg.carddatabasemtg.price;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.card_database.mtg.carddatabasemtg.CardbaseHelper;
import com.card_database.mtg.carddatabasemtg.DatabaseContract;

public class DatabaseSession {

    static Cursor cursor;
    private static SQLiteDatabase db;

    static String getSet(Context context, String name) {
        String set = "";
        try {
            db = new CardbaseHelper(context).getReadableDatabase();
            cursor = db.query(
                    DatabaseContract.ScriptCards.TABLE,
                    new String[]{DatabaseContract.CARD_NAME_COLLUMN, DatabaseContract.CARD_SET_COLLUMN},
                    DatabaseContract.CARD_NAME_COLLUMN + " = ?",
                    new String[]{name},
                    null,
                    null,
                    null,
                    null
            );
            cursor.moveToFirst();
        } catch (Exception e) {
            Log.d("Price ", "Exception : " + e.getMessage());
        }
        Log.d("fgh", cursor.getCount() + "");
        if (cursor.getCount() != 0) {
            do {
                set = cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_SET_COLLUMN));
                Log.d("fgh", set);
            } while (cursor.moveToNext());
            String sub_set = "";
            int i = 0;
            while (set.charAt(i) != ' ')
                i++;
            i++;
            while (i < set.length())
                sub_set += set.charAt(i++);
            set = sub_set;
        }
        return set;
    }
}