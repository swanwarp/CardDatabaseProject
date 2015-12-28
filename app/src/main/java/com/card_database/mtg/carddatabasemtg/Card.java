package com.card_database.mtg.carddatabasemtg;

public class Card {
    public  String NAME;
    public  String MANACOST;
    public  String CMC;
    public  String COLORS;
    public  String SUPERTYPES;
    public  String TYPES;
    public  String SUBTYPES;
    public  String TEXT;
    public  String POWER;
    public  String TOUGHNESS;
    public  String SET;

    Card(String name, String manacost, String cmc, String colors, String supertypes, String types, String subtypes,
            String text, String power, String toughness, String set) {
        NAME = name;
        MANACOST = manacost;
        CMC = cmc;
        COLORS = colors;
        SUPERTYPES = supertypes;
        TYPES = types;
        SUBTYPES = subtypes;
        TEXT = text;
        POWER = power;
        TOUGHNESS = toughness;
        SET = set;
    }
}
