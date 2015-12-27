package com.card_database.mtg.carddatabasemtg;

public class Card {
    public  String NAME;
    public  String MANACOST;
    public  String CMC;
    public String COLORS;
    public  String TYPE;
    public  String SUPERTYPE;
    public  String TYPES;
    public  String SUBTYPE;
    public  String RARITY;
    public  String TEXT;
    public  String FLAVOR;
    public  String ARTIST;
    public  String NUMBER;
    public  String POWER;
    public  String TOUGHNESS;
    public  String MULTIVERSEID;
    public  String ID;

    Card(String name, String manacost, String cmc, String colors, String type, String supertype, String types, String subtypes, String rarity,
            String text, String flavor, String artist, String number, String power, String toughness, String multyid, String id) {
        NAME = name;
        MANACOST = manacost;
        CMC = cmc;
        COLORS = colors;
        TYPE = type;
        SUPERTYPE = supertype;
        TYPES = types;
        SUBTYPE = subtypes;
        RARITY = rarity;
        TEXT = text;
        FLAVOR = flavor;
        ARTIST = artist;
        NUMBER = number;
        POWER = power;
        TOUGHNESS = toughness;
        MULTIVERSEID = multyid;
        ID = id;
    }

    public boolean checkParse() {
        return !NAME.equals("") && !MANACOST.equals("") && !CMC.equals("") && COLORS != null && !TYPE.equals("") && !SUPERTYPE.equals("") && !TYPES.equals("") &&
                !SUBTYPE.equals("") && !RARITY.equals("") && !TEXT.equals("") && !FLAVOR.equals("") && !ARTIST.equals("") && !NUMBER.equals("") && !POWER.equals("") &&
                !TOUGHNESS.equals("") && !MULTIVERSEID.equals("") && !ID.equals("");
    }
}
