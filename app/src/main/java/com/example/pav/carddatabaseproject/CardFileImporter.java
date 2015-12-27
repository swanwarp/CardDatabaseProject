package com.example.pav.carddatabaseproject;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

/**
 * Created by Матвей on 25.12.2015.
 */
public class CardFileImporter {
    private SQLiteDatabase db;
    private int importoredCount;
    private SQLiteStatement statement;
    private static final String LOG_TAG = "IMPORTER";

    public CardFileImporter(SQLiteDatabase db) {
        this.db = db;
        this.statement = db.compileStatement(
                "INSERT INTO cards(" +
                        DatabaseContract.CARD_NAME_COLLUMN + "," +
                        DatabaseContract.CARD_CMC_COLLUMN + "," +
                        DatabaseContract.CARD_COLORS_COLLUMN + "," +
                        DatabaseContract.CARD_TYPE_COLLUMN + "," +
                        DatabaseContract.CARD_SUPERTYPE_COLLUMN + "," +
                        DatabaseContract.CARD_TYPES_COLLUMN + "," +
                        DatabaseContract.CARD_SUBTYPE_COLLUMN + "," +
                        DatabaseContract.CARD_RARITY_COLLUMN + "," +
                        DatabaseContract.CARD_TEXT_COLLUMN + "," +
                        DatabaseContract.CARD_FLAVOR_COLLUMN + "," +
                        DatabaseContract.CARD_ARTIST_COLLUMN + "," +
                        DatabaseContract.CARD_NUMBER_COLLUMN + "," +
                        DatabaseContract.CARD_POWER_COLLUMN + "," +
                        DatabaseContract.CARD_TOUGHNESS_COLLUMN + "," +
                        DatabaseContract.CARD_MULTIVERSEID_COLLUMN + "," +
                        DatabaseContract.CARD_ID_COLLUMN +  ")" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
    }

    public final synchronized void importCards(File srcFile)
            throws IOException {

        InputStream in = null;

        try {
            long fileSize = srcFile.length();
            in = new FileInputStream(srcFile);
            in = new BufferedInputStream(in);
            in = new ObservableInputStream(in, fileSize);
            in = new GZIPInputStream(in);
            importCards(in);

        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG, "Failed to close file: " + e, e);
                }
            }
        }
    }

    private void importCards(InputStream in) {
        try {
            if(!db.inTransaction())
                db.beginTransaction();

            ArrayList<Card> cards = JSONParse.JsonReadCards(in);
            for(int i = 0; i < cards.size(); i++) {
                if(cards.get(i).checkParse())
                    onCardParsed(cards.get(i));
                else
                    throw new Exception("failed to parse JSON");
            }

            if(db.inTransaction())
                db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to parse Cards: " + e, e);
        } finally {
            db.endTransaction();
            statement.close();
        }
    }

    public void onCardParsed(Card card) throws Exception {
        if(insertCard(card)) {
            importoredCount++;
            if(importoredCount % 1000 == 0) {
                Log.d(LOG_TAG, "Parsed " + importoredCount);
            }
        } else {
            throw new Exception("failed to parse");
        }
    }

    private boolean insertCard(Card card) {
        statement.bindString(1, card.NAME);
        statement.bindString(2, card.CMC);
        statement.bindString(3, card.COLORS);
        statement.bindString(4, card.TYPE);
        statement.bindString(5, card.SUPERTYPE);
        statement.bindString(6, card.TYPES);
        statement.bindString(7, card.SUBTYPE);
        statement.bindString(8, card.RARITY);
        statement.bindString(9, card.TEXT);
        statement.bindString(10, card.FLAVOR);
        statement.bindString(11, card.ARTIST);
        statement.bindString(12, card.NUMBER);
        statement.bindString(13, card.POWER);
        statement.bindString(14, card.TOUGHNESS);
        statement.bindString(15, card.MULTIVERSEID);
        statement.bindString(16, card.ID);

        long rowId = statement.executeInsert();
        if(rowId < 0) {
            Log.w(LOG_TAG, "Failed to insert card: name=" + card.NAME);
            return false;
        }
        return true;
    }
}
