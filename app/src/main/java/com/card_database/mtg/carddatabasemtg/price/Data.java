package com.card_database.mtg.carddatabasemtg.price;

public class Data {

    String name;
    String set;
    float cost;
    int count;

    public Data (String name, String set, float cost, int count) {
        this.name = name;
        this.set = set;
        this.cost = cost;
        this.count = count;
    }
}
