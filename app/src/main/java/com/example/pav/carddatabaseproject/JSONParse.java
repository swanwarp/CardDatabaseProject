package com.example.pav.carddatabaseproject;

import android.util.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

final class JSONParse {

    static ArrayList JsonReadCards(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return cardArray(reader);
        } finally {
            reader.close();
        }
    }

    static private ArrayList<Card> cardArray(JsonReader reader) throws IOException{
        ArrayList<Card> cards = new ArrayList<>();
        reader.beginArray();
        while (reader.hasNext()) {
            cards.add(createCard(reader));
        }
        reader.endArray();
        return cards;
    }

    static private String createArray(JsonReader reader) throws IOException{
        String ret = "";
        reader.beginArray();
        while (reader.hasNext()) {
            ret += reader.nextString();
        }
        reader.endArray();
        return ret;
    }

    static private Card createCard(JsonReader reader) throws IOException {
        reader.beginObject();

        String NAME = "";
        String MANACOST = "";
        String CMC = "";
        String COLORS = "";
        String TYPE = "";
        String SUPERTYPE = "";
        String TYPES = "";
        String SUBTYPE = "";
        String RARITY = "";
        String TEXT = "";
        String FLAVOR = "";
        String ARTIST = "";
        String NUMBER = "";
        String POWER = "";
        String TOUGHNESS = "";
        String MULTIVERSEID = "";
        String ID = "";


        while(reader.hasNext()) {
            switch (reader.nextName()) {
                case "name": NAME = reader.nextString(); break;
                case "manaCost": MANACOST = reader.nextString(); break;
                case "cmc": CMC = reader.nextString(); break;
                case "colors": COLORS = createArray(reader); break;
                case "type": TYPE = reader.nextString(); break;
                case "supertypes": SUPERTYPE = createArray(reader); break;
                case "types": TYPES = createArray(reader); break;
                case "subtypes": SUBTYPE = createArray(reader); break;
                case "rarity": RARITY = reader.nextString(); break;
                case "text": TEXT = reader.nextString(); break;
                case "flavor": FLAVOR = reader.nextString(); break;
                case "artist": ARTIST = reader.nextString(); break;
                case "number": NUMBER = reader.nextString(); break;
                case "power": POWER = reader.nextString(); break;
                case "toughness": TOUGHNESS = reader.nextString(); break;
                case "multiverseid": MULTIVERSEID = reader.nextString(); break;
                case "id": ID = reader.nextString(); break;

                default: reader.skipValue(); break;
            }
        }

        reader.endObject();

        return new Card(NAME, MANACOST, CMC, COLORS, TYPE, SUPERTYPE, TYPES, SUBTYPE, RARITY,
                TEXT, FLAVOR, ARTIST, NUMBER, POWER, TOUGHNESS, MULTIVERSEID, ID);
    }
}
