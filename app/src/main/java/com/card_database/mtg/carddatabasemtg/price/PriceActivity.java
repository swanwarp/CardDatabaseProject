package com.card_database.mtg.carddatabasemtg.price;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.card_database.mtg.carddatabasemtg.LifeCounterActivity;
import com.card_database.mtg.carddatabasemtg.MainActivity;
import com.card_database.mtg.carddatabasemtg.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class PriceActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private PriceAdapter trade_one, trade_two;
    private Vector<Data> card_data_one, card_data_two;
    private Data new_card;
    private EditText search;
    private TextView sum_one, sum_two;
    private ListView window_one, window_two;
    private Button button_one, button_two;
    private DownloadCardTask downloadCard;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_price);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_price);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_price);
        navigationView.setNavigationItemSelectedListener(this);

        search = (EditText) findViewById(R.id.search);
        window_one = (ListView) findViewById(R.id.trade_window_one);
        window_two = (ListView) findViewById(R.id.trade_window_two);
        sum_one = (TextView) findViewById(R.id.trade_sum_one);
        sum_two = (TextView) findViewById(R.id.trade_sum_two);
        button_one = (Button) findViewById(R.id.trade_button_one);
        button_two = (Button) findViewById(R.id.trade_button_two);

        button_one.setOnClickListener(this);
        button_two.setOnClickListener(this);

        trade_one = new PriceAdapter(this, card_data_one);
        trade_two = new PriceAdapter(this, card_data_two);
        window_one.setAdapter(trade_one);
        window_two.setAdapter(trade_two);

        update_sum();
    }

    @Override
    public void onClick(View view) {
        String name = search.getText().toString();
        if (!name.isEmpty()) {
            String sub_name = "";
            for (int i = 0; i < name.length(); i++) {
                if (name.charAt(i) == ' ')
                    sub_name += '+';
                else
                    sub_name += name.charAt(i);
            }
            try {
                /**
                 * Здесь указать адрес откуда будет загружаться стоимоть карты
                 */
                URL url = new URL("http://magic.tcgplayer.com/db/pricefinder.asp?CARD=" + sub_name);
                Log.d("button", url.toString());
                downloadCard = new DownloadCardTask(this, url, view);
                downloadCard.execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    void update(View view) {
        switch (view.getId()) {
            case R.id.trade_button_one:
                break;
            case R.id.trade_button_two:
                break;
        }
        update_sum();
    }

    void update_sum() {
        float trade_sum_one = 0, trade_sum_two = 0;
        int length_one, length_two;
        if (card_data_one != null)
            length_one = card_data_one.size();
        else
            length_one = 0;
        if (card_data_two != null)
            length_two = card_data_two.size();
        else
            length_two = 0;
        for (int i = 0; i < length_one; i++)
            trade_sum_one += card_data_one.get(i).count * card_data_one.get(i).cost;
        for (int i = 0; i < length_two; i++)
            trade_sum_one += card_data_two.get(i).count * card_data_two.get(i).cost;
        sum_one.setText("$" + String.valueOf(trade_sum_one));
        sum_two.setText("$" + String.valueOf(trade_sum_two));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_life_counter) {
            intent = new Intent(this, LifeCounterActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_price);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static class DownloadCardTask extends AsyncTask<Void, Void, Integer> {

        private URL url;
        private PriceActivity activity;
        private Context context;
        private View view;

        public DownloadCardTask(PriceActivity activity, URL url, View view) {
            this.activity = activity;
            this.context = activity.getApplicationContext();
            this.url = url;
            this.view = view;
        }

        public void attachActivity(PriceActivity activity) {
            this.activity = activity;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            try {
                activity.new_card = DownloadCard.downloadCard(url, CreateFile.createTempExternalFile(context, ".jpg"));
                return 1;
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer resultCode) {
            // Этот метод выполняется в UI потоке
            // Параметр resultCode -- это результат doInBackground
            activity.update(view);
        }
    }
}

