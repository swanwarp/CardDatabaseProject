package com.card_database.mtg.carddatabasemtg;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.card_database.mtg.carddatabasemtg.price.PriceActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String[] MEL = {"", ">", "=", "<"};
    final String[] CMC = {"", "0",  "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"};
    final String[] PT = {"", "-1", "0",  "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "*", "1+*", "2+*", "7-*", "*^2"};
    Spinner cmcMel, cmcNum, powMel, powNum, tougMel, tougNum;

    public static String CMC_KEY1 = "cmc_mel_key";
    public static String CMC_KEY2 = "cmc_num_key";
    public static String POW_KEY1 = "pow_mel_key";
    public static String POW_KEY2 = "pow_num_key";
    public static String TOUG_KEY1 = "toug_mel_key";
    public static String TOUG_KEY2 = "toug_num_key";

    private DownloadJsonTask downloadJsonTask;
    private SQLiteDatabase database;
    private boolean DONE;


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
        ArrayAdapter<String> melAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, MEL);
        cmcMel = (Spinner) findViewById(R.id.cmcMEL);
        powMel = (Spinner) findViewById(R.id.powerMEL);
        tougMel = (Spinner) findViewById(R.id.toughnessMEL);

        cmcMel.setAdapter(melAdapter);
        powMel.setAdapter(melAdapter);
        tougMel.setAdapter(melAdapter);

        ArrayAdapter<String> cmcAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, CMC);
        cmcNum = (Spinner) findViewById(R.id.cmcNumbers);
        cmcNum.setAdapter(cmcAdapter);

        ArrayAdapter<String> ptAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, PT);
        powNum = (Spinner) findViewById(R.id.powerNumbers);
        tougNum = (Spinner) findViewById(R.id.toughnessNumbers);

        powNum.setAdapter(ptAdapter);
        tougNum.setAdapter(ptAdapter);
        //закончили с адаптерами

        if(savedInstanceState != null) {
            cmcMel.setSelection(savedInstanceState.getInt(CMC_KEY1));
            cmcNum.setSelection(savedInstanceState.getInt(CMC_KEY2));
            powMel.setSelection(savedInstanceState.getInt(POW_KEY1));
            powNum.setSelection(savedInstanceState.getInt(POW_KEY2));
            tougMel.setSelection(savedInstanceState.getInt(TOUG_KEY1));
            tougNum.setSelection(savedInstanceState.getInt(TOUG_KEY2));
            DONE = savedInstanceState.getBoolean("DONE");

            downloadJsonTask = (DownloadJsonTask) getLastCustomNonConfigurationInstance();
            downloadJsonTask.attachActivity(this);
            downloadJsonTask.attachContext(this);
        } else {
            downloadJsonTask = new DownloadJsonTask(this, this);
            downloadJsonTask.execute("https://www.dropbox.com/s/gs4r50rmd5jnw20/allcards.json?dl=0");
            DONE = false;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        saveInstanceState.putInt(CMC_KEY1, cmcMel.getSelectedItemPosition());
        saveInstanceState.putInt(CMC_KEY2, cmcNum.getSelectedItemPosition());
        saveInstanceState.putInt(POW_KEY1, powMel.getSelectedItemPosition());
        saveInstanceState.putInt(POW_KEY2, powNum.getSelectedItemPosition());
        saveInstanceState.putInt(TOUG_KEY1, tougMel.getSelectedItemPosition());
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
}
