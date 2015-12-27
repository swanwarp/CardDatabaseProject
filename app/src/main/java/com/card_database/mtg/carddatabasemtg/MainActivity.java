package com.card_database.mtg.carddatabasemtg;

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
    SimpleCursorAdapter adapter;
    Spinner cmcNum, powNum, tougNum;
    Button search;
    TextView supertype, type, text, flavor;
    CheckBox w,u,b,r,g;

    private static final String [] PROJECTION = {
    DatabaseContract.CARD_NAME_COLLUMN,
    DatabaseContract.CARD_CMC_COLLUMN,
    DatabaseContract.CARD_COLORS_COLLUMN,
    DatabaseContract.CARD_SUPERTYPES_COLLUMN,
    DatabaseContract.CARD_TYPES_COLLUMN,
    DatabaseContract.CARD_SUBTYPES_COLLUMN,
    DatabaseContract.CARD_TEXT_COLLUMN,
    DatabaseContract.CARD_FLAVOR_COLLUMN,
    DatabaseContract.CARD_POWER_COLLUMN,
    DatabaseContract.CARD_TOUGHNESS_COLLUMN,
    DatabaseContract.CARD_ID_COLLUMN
    };

    private static final int INDEX_NAME = 1;

    public static String CMC_KEY2 = "cmc_num_key";
    public static String POW_KEY2 = "pow_num_key";
    public static String TOUG_KEY2 = "toug_num_key";

    private DownloadJsonTask downloadJsonTask;
    private SQLiteDatabase database;
    private boolean DONE;
    private String ask;


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
        nameField.setEnabled(false);
        supertype = (TextView) findViewById(R.id.supertypeField);
        type = (TextView) findViewById(R.id.typeField);
        text = (TextView) findViewById(R.id.cardtextField);
        flavor = (TextView) findViewById(R.id.flavorField);
        w = (CheckBox) findViewById(R.id.wCheckBox);
        u = (CheckBox) findViewById(R.id.uCheckBox);
        b = (CheckBox) findViewById(R.id.bCheckBox);
        r = (CheckBox) findViewById(R.id.rCheckBox);
        g = (CheckBox) findViewById(R.id.gCheckBox);

        search = (Button) findViewById(R.id.searchButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ask = generateAsk();

                
            }
        });

        search.callOnClick();


        if(savedInstanceState != null) {
            cmcNum.setSelection(savedInstanceState.getInt(CMC_KEY2));
            powNum.setSelection(savedInstanceState.getInt(POW_KEY2));
            tougNum.setSelection(savedInstanceState.getInt(TOUG_KEY2));
            DONE = savedInstanceState.getBoolean("DONE");

            downloadJsonTask = (DownloadJsonTask) getLastCustomNonConfigurationInstance();
            downloadJsonTask.attachActivity(this);
            downloadJsonTask.attachContext(this);
        } else {
            downloadJsonTask = new DownloadJsonTask(this, this);
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
        nameField.setEnabled(true);
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


    private String generateAsk() {
        String ans = "";
        if(!nameField.getText().toString().equals("")) {
            ans += DatabaseContract.CARD_NAME_COLLUMN + " LIKE " + nameField.getText().toString().toLowerCase()+"%";
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
                ans += DatabaseContract.CARD_SUPERTYPES_COLLUMN + " LIKE " + supertypes+"%";
            }
            if(!ans.equals("") && ans.charAt(ans.length() - 1) != ' ')
                ans += " AND ";
            if(types.length() != 0) {
                ans += DatabaseContract.CARD_TYPES_COLLUMN + " LIKE " + types +"%";
            }
        }

        if(!type.getText().toString().equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            ans += DatabaseContract.CARD_SUBTYPES_COLLUMN + " LIKE " + type.getText().toString().toLowerCase()+"%";
        }

        if(!cmcNum.getItemAtPosition(cmcNum.getSelectedItemPosition()).equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            ans += DatabaseContract.CARD_CMC_COLLUMN + " LIKE " + cmcNum.getItemAtPosition(cmcNum.getSelectedItemPosition()).toString()+"%";
        }

        if(!powNum.getItemAtPosition(powNum.getSelectedItemPosition()).equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            ans += DatabaseContract.CARD_POWER_COLLUMN + " LIKE " + powNum.getItemAtPosition(powNum.getSelectedItemPosition()).toString()+"%";
        }

        if(!tougNum.getItemAtPosition(tougNum.getSelectedItemPosition()).equals("")) {
            if(!ans.equals(""))
                ans += " AND ";
            ans += DatabaseContract.CARD_TOUGHNESS_COLLUMN + " LIKE " + tougNum.getItemAtPosition(tougNum.getSelectedItemPosition()).toString()+"%";
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
            ans += DatabaseContract.CARD_COLORS_COLLUMN + " LIKE " + colors + "%";
        }


        return ans;
    }
}
