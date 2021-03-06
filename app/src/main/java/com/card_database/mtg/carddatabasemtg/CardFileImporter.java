package com.card_database.mtg.carddatabasemtg;

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
        importoredCount = 0;
        this.db = db;
        this.statement = db.compileStatement(
                "INSERT INTO Cards(" +
                        DatabaseContract.CARD_ID_COLLUMN + "," +
                        DatabaseContract.CARD_NAME_COLLUMN + "," +
                        DatabaseContract.CARD_COST_COLLUMN + "," +
                        DatabaseContract.CARD_CMC_COLLUMN + "," +
                        DatabaseContract.CARD_COLORS_COLLUMN + "," +
                        DatabaseContract.CARD_SUPERTYPES_COLLUMN + "," +
                        DatabaseContract.CARD_TYPES_COLLUMN + "," +
                        DatabaseContract.CARD_SUBTYPES_COLLUMN + "," +
                        DatabaseContract.CARD_TEXT_COLLUMN + "," +
                        DatabaseContract.CARD_POWER_COLLUMN + "," +
                        DatabaseContract.CARD_TOUGHNESS_COLLUMN + "," +
                        DatabaseContract.CARD_SET_COLLUMN + ")" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
    }

    public void importCards(ArrayList<Card> cards) {
        try {
            if(!db.inTransaction())
                db.beginTransaction();

            for(int i = 0; i < cards.size(); i++) {
                onCardParsed(cards.get(i));
            }

            if(db.inTransaction())
                db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to parse Cards: " + e, e);
        } finally {
            db.endTransaction();
        }
    }

    public void closeStatment() {
        statement.close();
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
        statement.bindLong(1, importoredCount);
        statement.bindString(2, card.NAME);
        statement.bindString(3, card.MANACOST);
        statement.bindString(4, card.CMC);
        statement.bindString(5, card.COLORS);
        statement.bindString(6, card.SUPERTYPES);
        statement.bindString(7, card.TYPES);
        statement.bindString(8, card.SUBTYPES);
        statement.bindString(9, card.TEXT);
        statement.bindString(10, card.POWER);
        statement.bindString(11, card.TOUGHNESS);
        statement.bindString(12, card.SET);

        long rowId = statement.executeInsert();
        if(rowId < 0) {
            Log.w(LOG_TAG, "Failed to insert card: name=" + card.NAME);
            return false;
        }
        return true;
    }
}
