package com.card_database.mtg.carddatabasemtg;

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

    static private String makeSet(JsonReader reader) throws IOException {
        String ret = "";
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            String SHORT = "", FULL = "";
            while (reader.hasNext()) {
                switch (reader.nextName()) {
                    case "set": FULL = reader.nextString(); break;
                    case "set_id": SHORT = reader.nextString(); break;

                    default: reader.skipValue();
                }
            }
            reader.endObject();

            if(ret.length() == 0) {
                ret = SHORT + " " + FULL;
            }
        }
        reader.endArray();
        return ret;
    }

    static private Card createCard(JsonReader reader) throws IOException {
        reader.beginObject();

        String NAME = "";
        String MANACOST = "0";
        String CMC = "0";
        String COLORS = "";
        String SUPERTYPE = "";
        String TYPES = "";
        String SUBTYPE = "";
        String TEXT = "";
        String POWER = "-5";
        String TOUGHNESS = "-5";
        String SET = "";


        while(reader.hasNext()) {
            switch (reader.nextName()) {
                case "name": NAME = reader.nextString(); break;
                case "cost": MANACOST = reader.nextString(); break;
                case "cmc": CMC = reader.nextString(); break;
                case "colors": COLORS = createArray(reader); break;
                case "supertypes": SUPERTYPE = createArray(reader); break;
                case "types": TYPES = createArray(reader); break;
                case "subtypes": SUBTYPE = createArray(reader); break;
                case "text": TEXT = reader.nextString(); break;
                case "power": POWER = reader.nextString(); break;
                case "toughness": TOUGHNESS = reader.nextString(); break;
                case "editions": SET = makeSet(reader); break;

                default: reader.skipValue(); break;
            }
        }

        reader.endObject();

        return new Card(NAME, MANACOST, CMC, COLORS, SUPERTYPE, TYPES, SUBTYPE,
                TEXT, POWER, TOUGHNESS, SET);
    }
}
