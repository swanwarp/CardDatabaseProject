package com.example.pav.carddatabaseproject;

import android.provider.BaseColumns;

/**
 * Created by Матвей on 25.12.2015.
 */
public class DatabaseContract {
    public static final String CARD_NAME_COLLUMN = "name";
    public static final String CARD_CMC_COLLUMN = "cmc";
    public static final String CARD_COLORS_COLLUMN = "colors";
    public static final String CARD_TYPE_COLLUMN = "type";
    public static final String CARD_SUPERTYPE_COLLUMN = "supertype";
    public static final String CARD_TYPES_COLLUMN = "types";
    public static final String CARD_SUBTYPE_COLLUMN = "subtype";
    public static final String CARD_RARITY_COLLUMN = "rarity";
    public static final String CARD_TEXT_COLLUMN = "text";
    public static final String CARD_FLAVOR_COLLUMN = "flavor";
    public static final String CARD_ARTIST_COLLUMN = "artist";
    public static final String CARD_NUMBER_COLLUMN = "number";
    public static final String CARD_POWER_COLLUMN = "power";
    public static final String CARD_TOUGHNESS_COLLUMN = "toughness";
    public static final String CARD_MULTIVERSEID_COLLUMN = "multiverseid";
    public static final String CARD_ID_COLLUMN = "id";

    public static final class ScriptCards {
        public static  final String TABLE = "cards";

        static final String CREATE = "CREATE TABLE " + TABLE +
                " (" +
                CARD_NAME_COLLUMN + " TEXT, " +
                CARD_CMC_COLLUMN + " INTEGER, " +
                CARD_COLORS_COLLUMN + " TEXT, " +
                CARD_TYPE_COLLUMN + " TEXT, " +
                CARD_SUPERTYPE_COLLUMN + " TEXT, " +
                CARD_TYPES_COLLUMN + " TEXT, " +
                CARD_SUBTYPE_COLLUMN + " TEXT, " +
                CARD_RARITY_COLLUMN + " TEXT, " +
                CARD_TEXT_COLLUMN + " TEXT, " +
                CARD_FLAVOR_COLLUMN + " TEXT, " +
                CARD_ARTIST_COLLUMN + " TEXT, " +
                CARD_NUMBER_COLLUMN + " INTEGER, " +
                CARD_POWER_COLLUMN + " INTEGER, " +
                CARD_TOUGHNESS_COLLUMN + " INTEGER, " +
                CARD_MULTIVERSEID_COLLUMN + " INTEGER, " +
                CARD_ID_COLLUMN + " TEXT" + ");";
    }
}
