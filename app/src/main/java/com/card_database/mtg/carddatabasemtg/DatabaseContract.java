package com.card_database.mtg.carddatabasemtg;

public class DatabaseContract {
    public static final String CARD_NAME_COLLUMN = "name";
    public static final String CARD_CMC_COLLUMN = "cmc";
    public static final String CARD_COLORS_COLLUMN = "colors";
    public static final String CARD_SUPERTYPES_COLLUMN = "supertype";
    public static final String CARD_TYPES_COLLUMN = "types";
    public static final String CARD_SUBTYPES_COLLUMN = "subtype";
    public static final String CARD_TEXT_COLLUMN = "text";
    public static final String CARD_FLAVOR_COLLUMN = "flavor";
    public static final String CARD_POWER_COLLUMN = "power";
    public static final String CARD_TOUGHNESS_COLLUMN = "toughness";
    public static final String CARD_ID_COLLUMN = "id";

    public static final class ScriptCards {
        public static  final String TABLE = "Cards";

        static final String CREATE = "CREATE TABLE " + TABLE +
                " (" +
                CARD_NAME_COLLUMN + " TEXT, " +
                CARD_CMC_COLLUMN + " TEXT, " +
                CARD_COLORS_COLLUMN + " TEXT, " +
                CARD_SUPERTYPES_COLLUMN + " TEXT, " +
                CARD_TYPES_COLLUMN + " TEXT, " +
                CARD_SUBTYPES_COLLUMN + " TEXT, " +
                CARD_TEXT_COLLUMN + " TEXT, " +
                CARD_FLAVOR_COLLUMN + " TEXT, " +
                CARD_POWER_COLLUMN + " TEXT, " +
                CARD_TOUGHNESS_COLLUMN + " TEXT, " +
                CARD_ID_COLLUMN + " TEXT" +");";
    }
}
