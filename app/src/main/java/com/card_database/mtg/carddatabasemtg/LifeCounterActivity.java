package com.card_database.mtg.carddatabasemtg;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.card_database.mtg.carddatabasemtg.price.PriceActivity;

public class LifeCounterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ISLIFE = "isLife";
    private static final int playerLifeCount = 20;
    private static final int playerPoisonCount = 0;
    //first player
    private static int firstPlayerLifeCount = 20;
    private static int secondPlayerLifeCount = 20;
    private TextView player1Name;
    private TextView player1Count;
    private Button player1Plus1;
    private Button player1Plus3;
    private Button player1Plus5;
    private Button player1Minus1;
    private Button player1Minus3;
    private Button player1Minus5;


    //second player
    private static int firstPlayerPoisonCount = 0;
    private static int secondPlayerPoisonCount = 0;
    private TextView player2Name;
    private TextView player2Count;
    private Button player2Plus1;
    private Button player2Plus3;
    private Button player2Plus5;
    private Button player2Minus1;
    private Button player2Minus3;
    private Button player2Minus5;

    //life button, poison button and reset

    private ImageButton lifeButton;
    private ImageButton poisonButton;
    private ImageButton resetButton;

    //
    private Boolean isLife = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_counter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_life_counter);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_life_counter);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_life_counter);
        navigationView.setNavigationItemSelectedListener(this);

        //

        player1Count = (TextView) findViewById(R.id.player_readout);
        player2Count = (TextView) findViewById(R.id.player2_readout);
        if (isLife) {
            player1Count.setText(Integer.toString(firstPlayerLifeCount));
            player2Count.setText(Integer.toString(secondPlayerLifeCount));
        } else {
            player1Count.setText(Integer.toString(firstPlayerPoisonCount));
            player2Count.setText(Integer.toString(secondPlayerPoisonCount));
        }
        //first player buttons
        player1Plus1 = (Button) findViewById(R.id.player_plus1);
        player1Plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife) {
                    firstPlayerLifeCount += 1;
                    player1Count.setText(Integer.toString(firstPlayerLifeCount));
                }
                else {
                    firstPlayerPoisonCount += 1;
                    player1Count.setText(Integer.toString(firstPlayerPoisonCount));
                }
            }
        });
        player1Plus3 = (Button) findViewById(R.id.player_plus3);
        player1Plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife) {
                    firstPlayerLifeCount += 3;
                    player1Count.setText(Integer.toString(firstPlayerLifeCount));
                }
            }
        });
        player1Plus5 = (Button) findViewById(R.id.player_plus5);
        player1Plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    firstPlayerLifeCount += 5;
                    player1Count.setText(Integer.toString(firstPlayerLifeCount));
                } else {
                    firstPlayerPoisonCount += 5;
                    player1Count.setText(Integer.toString(firstPlayerPoisonCount));
                }
            }
        });
        player1Minus1 = (Button) findViewById(R.id.player_minus1);
        player1Minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    firstPlayerLifeCount -= 1;
                    player1Count.setText(Integer.toString(firstPlayerLifeCount));
                } else {
                    firstPlayerPoisonCount -= 1;
                    player1Count.setText(Integer.toString(firstPlayerPoisonCount));
                }
            }
        });
        player1Minus3 = (Button) findViewById(R.id.player_minus3);
        player1Minus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    firstPlayerLifeCount -= 3;
                    player1Count.setText(Integer.toString(firstPlayerLifeCount));
                }
            }
        });
        player1Minus5 = (Button) findViewById(R.id.player_minus5);
        player1Minus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    firstPlayerLifeCount -= 5;
                    player1Count.setText(Integer.toString(firstPlayerLifeCount));
                } else {
                    firstPlayerPoisonCount -= 5;
                    player1Count.setText(Integer.toString(firstPlayerPoisonCount));
                }
            }
        });

        //second player buttons
        player2Plus1 = (Button) findViewById(R.id.player2_plus1);
        player2Plus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    secondPlayerLifeCount += 1;
                    player2Count.setText(Integer.toString(secondPlayerLifeCount));
                } else {
                    secondPlayerPoisonCount += 1;
                    player2Count.setText(Integer.toString(secondPlayerPoisonCount));
                }
            }
        });
        player2Plus3 = (Button) findViewById(R.id.player2_plus3);
        player2Plus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    secondPlayerLifeCount += 3;
                    player2Count.setText(Integer.toString(secondPlayerLifeCount));
                }
            }
        });
        player2Plus5 = (Button) findViewById(R.id.player2_plus5);
        player2Plus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    secondPlayerLifeCount += 5;
                    player2Count.setText(Integer.toString(secondPlayerLifeCount));
                } else {
                    secondPlayerPoisonCount += 5;
                    player2Count.setText(Integer.toString(secondPlayerPoisonCount));
                }
            }
        });
        player2Minus1 = (Button) findViewById(R.id.player2_minus1);
        player2Minus1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    secondPlayerLifeCount -= 1;
                    player2Count.setText(Integer.toString(secondPlayerLifeCount));
                } else {
                    secondPlayerPoisonCount -= 1;
                    player2Count.setText(Integer.toString(secondPlayerPoisonCount));
                }
            }
        });
        player2Minus3 = (Button) findViewById(R.id.player2_minus3);
        player2Minus3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    secondPlayerLifeCount -= 3;
                    player2Count.setText(Integer.toString(secondPlayerLifeCount));
                }
            }
        });
        player2Minus5 = (Button) findViewById(R.id.player2_minus5);
        player2Minus5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLife){
                    secondPlayerLifeCount -= 5;
                    player2Count.setText(Integer.toString(secondPlayerLifeCount));
                } else {
                    secondPlayerPoisonCount -= 5;
                    player2Count.setText(Integer.toString(secondPlayerPoisonCount));
                }
            }
        });

        lifeButton = (ImageButton) findViewById(R.id.button_life);
        lifeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1Count.setText(Integer.toString(firstPlayerLifeCount));
                player2Count.setText(Integer.toString(secondPlayerLifeCount));
                lifeButton.setImageResource(R.drawable.lc_life_enabled_dark);
                poisonButton.setImageResource(R.drawable.lc_poison_disabled_dark);
                player1Plus3.setVisibility(View.VISIBLE);
                player1Plus3.setClickable(true);
                player1Minus3.setVisibility(View.VISIBLE);
                player1Minus3.setClickable(true);
                player2Plus3.setVisibility(View.VISIBLE);
                player2Plus3.setClickable(true);
                player2Minus3.setVisibility(View.VISIBLE);
                player2Minus3.setClickable(true);
                isLife = true;
            }
        });
        poisonButton = (ImageButton) findViewById(R.id.button_posion);
        poisonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1Count.setText(Integer.toString(firstPlayerPoisonCount));
                player2Count.setText(Integer.toString(secondPlayerPoisonCount));
                lifeButton.setImageResource(R.drawable.lc_life_disabled_dark);
                poisonButton.setImageResource(R.drawable.lc_poison_enabled_dark);
                player1Plus3.setVisibility(View.INVISIBLE);
                player1Plus3.setClickable(false);
                player1Minus3.setVisibility(View.INVISIBLE);
                player1Minus3.setClickable(false);
                player2Plus3.setVisibility(View.INVISIBLE);
                player2Plus3.setClickable(false);
                player2Minus3.setVisibility(View.INVISIBLE);
                player2Minus3.setClickable(false);
                isLife = false;
            }
        });
        resetButton = (ImageButton) findViewById(R.id.button_reset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPlayerPoisonCount = playerPoisonCount;
                secondPlayerPoisonCount = playerPoisonCount;
                firstPlayerLifeCount = playerLifeCount;
                secondPlayerLifeCount = playerLifeCount;
                if (isLife){
                    player1Count.setText(Integer.toString(firstPlayerLifeCount));
                    player2Count.setText(Integer.toString(secondPlayerLifeCount));
                } else {
                    player1Count.setText(Integer.toString(firstPlayerPoisonCount));
                    player2Count.setText(Integer.toString(secondPlayerPoisonCount));
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ISLIFE,isLife);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Boolean life = savedInstanceState.getBoolean(ISLIFE);
        if (!life){
            poisonButton.callOnClick();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_prices) {
            intent = new Intent(this, PriceActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_life_counter);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
