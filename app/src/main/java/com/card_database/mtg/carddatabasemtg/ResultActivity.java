package com.card_database.mtg.carddatabasemtg;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    Cursor cursor;
    Button backButton;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        backButton = (Button) findViewById(R.id.button);

        String ask = getIntent().getStringExtra("ask");
        String[] searchConditions = getIntent().getStringArrayExtra("searchConditions");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = new CardbaseHelper(this).getReadableDatabase();

        try {
            database = new CardbaseHelper(this).getReadableDatabase();

            cursor = database.query(
                    DatabaseContract.ScriptCards.TABLE,
                    new String[]{
                            DatabaseContract.CARD_NAME_COLLUMN,
                            DatabaseContract.CARD_COST_COLLUMN,
                            DatabaseContract.CARD_CMC_COLLUMN,
                            DatabaseContract.CARD_COLORS_COLLUMN,
                            DatabaseContract.CARD_SUPERTYPES_COLLUMN,
                            DatabaseContract.CARD_TYPES_COLLUMN,
                            DatabaseContract.CARD_SUBTYPES_COLLUMN,
                            DatabaseContract.CARD_TEXT_COLLUMN,
                            DatabaseContract.CARD_POWER_COLLUMN,
                            DatabaseContract.CARD_TOUGHNESS_COLLUMN,
                            DatabaseContract.CARD_SET_COLLUMN
                    },
                    ask,
                    searchConditions,
                    null,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            Log.d("MainActivity", "Done");
        } catch (Exception e) {
            Log.d("MainActivity ", "Exception : " + e.getMessage());
        }

        //здесь создаем адаптер

        if (cursor.getCount() != 0) {
            Card[] data = new Card[cursor.getCount()];
            Card item;

            int i = 0;
            while (true) {
                item = new Card(cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_NAME_COLLUMN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_COST_COLLUMN)),
                        null, null, cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_SUPERTYPES_COLLUMN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_TYPES_COLLUMN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_SUBTYPES_COLLUMN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_TEXT_COLLUMN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_POWER_COLLUMN)),
                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_TOUGHNESS_COLLUMN)),
                        null);
                data[i] = item;
                i++;
                if (!cursor.moveToNext())
                    break;
            }
            MainAdapter mainAdapter = new MainAdapter(getApplicationContext(), data);

            ListView listView = (ListView) findViewById(R.id.listRes);
            listView.setAdapter(mainAdapter);
        } else {
            Toast toast = Toast.makeText(this, "Ничего не найдено",
                    Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
