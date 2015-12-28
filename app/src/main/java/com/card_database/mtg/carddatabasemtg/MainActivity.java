package com.card_database.mtg.carddatabasemtg;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.card_database.mtg.carddatabasemtg.price.PriceActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String[] CMC = {"", "0",  "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
    final String[] PT = {"", "-1", "0",  "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "*", "1+*", "2+*", "7-*", "*^2"};

    AutoCompleteTextView nameField;
    Cursor cursor;
    Spinner cmcNum, powNum, tougNum;
    Button search;
    TextView supertype, type, text;
    CheckBox w,u,b,r,g;
    ScrollView scrollView;
    Context context = this;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BlankFragment fragment;

    public static String CMC_KEY2 = "cmc_num_key";
    public static String POW_KEY2 = "pow_num_key";
    public static String TOUG_KEY2 = "toug_num_key";

    private DownloadJsonTask downloadJsonTask;
    private SQLiteDatabase database;
    private boolean DONE;
    public static String ask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Создаем адаптеры для спиннеров
        ArrayAdapter<String> cmcAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CMC);
        cmcNum = (Spinner) findViewById(R.id.cmcNumbers);
        cmcNum.setAdapter(cmcAdapter);

        ArrayAdapter<String> ptAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PT);
        powNum = (Spinner) findViewById(R.id.powerNumbers);
        tougNum = (Spinner) findViewById(R.id.toughnessNumbers);

        powNum.setAdapter(ptAdapter);
        tougNum.setAdapter(ptAdapter);
        //закончили с адаптерами


        nameField = (AutoCompleteTextView) findViewById(R.id.nameField);
        supertype = (TextView) findViewById(R.id.supertypeField);
        type = (TextView) findViewById(R.id.typeField);
        text = (TextView) findViewById(R.id.cardtextField);
        w = (CheckBox) findViewById(R.id.wCheckBox);
        u = (CheckBox) findViewById(R.id.uCheckBox);
        b = (CheckBox) findViewById(R.id.bCheckBox);
        r = (CheckBox) findViewById(R.id.rCheckBox);
        g = (CheckBox) findViewById(R.id.gCheckBox);
        scrollView = (ScrollView) findViewById(R.id.scrollView);

        if(!Setting.WantToDownload)database = new CardbaseHelper(this).getReadableDatabase();

        search = (Button) findViewById(R.id.searchButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] searchConditions = generateAsk();

                if (generateAsk().length == 0) {
                    return;
                }

                try {
                    database = new CardbaseHelper(context).getReadableDatabase();

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

                if(cursor.getCount() != 0) {


                    fragmentManager = getFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();

                    fragment = new BlankFragment();
                    fragmentTransaction.add(R.id.fragment, fragment);
                    ArrayList<String> arrayList = new ArrayList<String>();

                    while (true) {
                        arrayList.add(
                                cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_NAME_COLLUMN)) + " " +
                                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_COST_COLLUMN)) + "\n" +
                                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_SUPERTYPES_COLLUMN)) + " " +
                                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_TYPES_COLLUMN)) + " - " +
                                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_SUBTYPES_COLLUMN)) + "\n" +
                                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_TEXT_COLLUMN)) + "\n" +
                                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_POWER_COLLUMN)) + "/" +
                                        cursor.getString(cursor.getColumnIndex(DatabaseContract.CARD_TOUGHNESS_COLLUMN))
                        );
                        if(!cursor.moveToNext())
                            break;
                    }

                    searchConditions = arrayList.toArray(searchConditions);

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, searchConditions);

                    scrollView.setVisibility(View.INVISIBLE);

                    ListView listView = (ListView) findViewById(R.id.listRes);
                    listView.setAdapter(adapter1);



                    fragmentTransaction.commit();
                } else {
                    nameField.setHint("No Cards found");
                }
            }
        });


        if(savedInstanceState != null) {
            cmcNum.setSelection(savedInstanceState.getInt(CMC_KEY2));
            powNum.setSelection(savedInstanceState.getInt(POW_KEY2));
            tougNum.setSelection(savedInstanceState.getInt(TOUG_KEY2));
            DONE = savedInstanceState.getBoolean("DONE");

            downloadJsonTask = (DownloadJsonTask) getLastCustomNonConfigurationInstance();
            downloadJsonTask.attachActivity(this);
            downloadJsonTask.attachContext(this);
        } else {
            downloadJsonTask = new DownloadJsonTask(this, this, Setting.WantToDownload);
            downloadJsonTask.execute("https://api.deckbrew.com/mtg/cards");
            DONE = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putInt(CMC_KEY2, cmcNum.getSelectedItemPosition());
        saveInstanceState.putInt(POW_KEY2, powNum.getSelectedItemPosition());
        saveInstanceState.putInt(TOUG_KEY2, tougNum.getSelectedItemPosition());
        saveInstanceState.putBoolean("DONE", DONE);
        super.onSaveInstanceState(saveInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            return true;
        } else if (id == R.id.action_load) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    @SuppressWarnings("deprecation")
    public Object onRetainCustomNonConfigurationInstance() {
        return downloadJsonTask;
    }

    public void setDatabase(SQLiteDatabase database) {
        this.database = database;
        DONE = true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;

        int id = item.getItemId();

        if (id == R.id.nav_prices) {
            intent = new Intent(this, PriceActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_life_counter) {
            intent = new Intent(this, LifeCounterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private String[] generateAsk() {
        String ans = "";

        ArrayList<String> searchConditions = new ArrayList<>();

        if(!nameField.getText().toString().equals("")) {
            ans += DatabaseContract.CARD_NAME_COLLUMN + " = " + "?";
            searchConditions.add(nameField.getText().toString());
        }

        if(!supertype.getText().toString().equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            String[] s = supertype.getText().toString().split(" ");
            String supertypes = "", types = "";
            for(int i = 0; i < s.length; i++) {
                switch (s[i].toLowerCase()) {
                    case "legendary":case "basic":case "snow":case "world":
                        supertypes += s[i];
                        break;
                    default:
                        types += s[i];
                        break;
                }
            }
            if(supertypes.length() != 0) {
                ans += DatabaseContract.CARD_SUPERTYPES_COLLUMN + " = " + "?";
                searchConditions.add(supertypes);
            }
            if(!ans.equals("") && ans.charAt(ans.length() - 1) != ' ')
                ans += " AND ";
            if(types.length() != 0) {
                ans += DatabaseContract.CARD_TYPES_COLLUMN + " = " + "?";
                searchConditions.add(types);
            }
        }

        if(!type.getText().toString().equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            ans += DatabaseContract.CARD_SUBTYPES_COLLUMN + " = " + "?";
            searchConditions.add(type.getText().toString());
        }

        if(!cmcNum.getItemAtPosition(cmcNum.getSelectedItemPosition()).equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            ans += DatabaseContract.CARD_CMC_COLLUMN + " = " + "?";
            searchConditions.add(cmcNum.getItemAtPosition(cmcNum.getSelectedItemPosition()).toString());
        }

        if(!powNum.getItemAtPosition(powNum.getSelectedItemPosition()).equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            ans += DatabaseContract.CARD_POWER_COLLUMN + " = " + "?";
            searchConditions.add(powNum.getItemAtPosition(powNum.getSelectedItemPosition()).toString());
        }

        if(!tougNum.getItemAtPosition(tougNum.getSelectedItemPosition()).equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            ans += DatabaseContract.CARD_TOUGHNESS_COLLUMN + " = " + "?";
            searchConditions.add(tougNum.getItemAtPosition(tougNum.getSelectedItemPosition()).toString());
        }

        String colors = "";

        if(b.isChecked())
            colors+= "black";
        if(u.isChecked())
            colors+= "blue";
        if(r.isChecked())
            colors+= "red";
        if(g.isChecked())
            colors+= "green";
        if(w.isChecked())
            colors+= "white";
        if(colors != "") {
            if(!ans.equals(""))
                ans+= " AND ";
            ans += DatabaseContract.CARD_COLORS_COLLUMN + " = " + "?";
            searchConditions.add(colors);
        }

        if(text.getText().toString().length() != 0) {
            if(!ans.equals(""))
                ans+= " AND ";
            ans += DatabaseContract.CARD_TEXT_COLLUMN + " LIKE " + "?";
            searchConditions.add(text.getText().toString());
        }

        ask = ans;

        String[] T = new String[searchConditions.size()];
        T = searchConditions.toArray(T);

        return T;
    }
}
