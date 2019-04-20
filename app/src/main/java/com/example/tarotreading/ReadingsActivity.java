package com.example.tarotreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ReadingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity
        setContentView(R.layout.activity_reading);


        findViewById(R.id.finish_reading).setOnClickListener(v -> backToSetUp());
        /*
        findViewById(R.id.card_past).setOnClickListener(v -> toCardCloseUp());

        findViewById(R.id.card_present).setOnClickListener(v -> toCardCloseUp());

        findViewById(R.id.card_future).setOnClickListener(v -> toCardCloseUp());
        */

    }
    public void backToSetUp() {
        Intent backup = new Intent(this, SetupActivity.class);
        startActivity(backup);
        finish();
    }
    /*
    public void toCardCloseUp() {
        Intent backup = new Intent(this, CardActivity.class);
        startActivity(backup);
        finish();
    }
    */
}
