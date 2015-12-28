package com.card_database.mtg.carddatabasemtg;

public class DatabaseContract {
    public static final String CARD_ID_COLLUMN = "_id";
    public static final String CARD_NAME_COLLUMN = "name";
    public static final String CARD_COST_COLLUMN = "manacost";
    public static final String CARD_CMC_COLLUMN = "cmc";
    public static final String CARD_COLORS_COLLUMN = "colors";
    public static final String CARD_SUPERTYPES_COLLUMN = "supertype";
    public static final String CARD_TYPES_COLLUMN = "types";
    public static final String CARD_SUBTYPES_COLLUMN = "subtype";
    public static final String CARD_TEXT_COLLUMN = "text";
    public static final String CARD_POWER_COLLUMN = "power";
    public static final String CARD_TOUGHNESS_COLLUMN = "toughness";
    public static final String CARD_SET_COLLUMN = "id";

    public static final class ScriptCards {
        public static  final String TABLE = "Cards";

        static final String CREATE = "CREATE TABLE " + TABLE +
                " (" +
                CARD_ID_COLLUMN + " INTEGER PRIMARY KEY," +
                CARD_NAME_COLLUMN + " TEXT, " +
                CARD_COST_COLLUMN + " TEXT, " +
                CARD_CMC_COLLUMN + " TEXT, " +
                CARD_COLORS_COLLUMN + " TEXT, " +
                CARD_SUPERTYPES_COLLUMN + " TEXT, " +
                CARD_TYPES_COLLUMN + " TEXT, " +
                CARD_SUBTYPES_COLLUMN + " TEXT, " +
                CARD_TEXT_COLLUMN + " TEXT, " +
                CARD_POWER_COLLUMN + " TEXT, " +
                CARD_TOUGHNESS_COLLUMN + " TEXT, " +
                CARD_SET_COLLUMN + " TEXT" +");";
    }
}
