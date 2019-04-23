package com.example.tarotreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class ReadingsActivity extends AppCompatActivity {

    private static boolean PAST_FLIPPED = false;
    private static boolean PRESENT_FLIPPED = false;
    private static boolean FUTURE_FLIPPED = false;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity
        setContentView(R.layout.activity_reading);

        ImageButton pastButton = findViewById(R.id.card_past);
        ImageButton presentButton = findViewById(R.id.card_present);
        ImageButton futureButton = findViewById(R.id.card_future);

        if (PAST_FLIPPED) {
            pastButton.setImageResource(R.drawable.ar01);
        }
        if (PRESENT_FLIPPED) {
            presentButton.setImageResource(R.drawable.ar02);
        }
        if (FUTURE_FLIPPED) {
            futureButton.setImageResource(R.drawable.ar02);
        }
        findViewById(R.id.finish_reading).setOnClickListener(v -> backToSetUp());

        pastButton.setOnClickListener(v -> toCardCloseUp());
        presentButton.setOnClickListener(v -> toCardPresent());
        futureButton.setOnClickListener(v -> toCardFuture());

    }
    public void backToSetUp() {
        Intent backup = new Intent(this, SetupActivity.class);
        startActivity(backup);
        finish();
    }
    public void toCardPresent() {
        if (!PRESENT_FLIPPED) {
            PRESENT_FLIPPED = true;
        }
        Intent backup = new Intent(this, CardActivity.class);
        startActivity(backup);
        finish();
    }
    public void toCardFuture() {
        if (!FUTURE_FLIPPED) {
            FUTURE_FLIPPED = true;
        }
        Intent backup = new Intent(this, CardActivity.class);
        startActivity(backup);
        finish();
    }
    public void toCardCloseUp() {
        if (!PAST_FLIPPED) {
            PAST_FLIPPED = true;
        }
        Intent backup = new Intent(this, CardActivity.class);
        startActivity(backup);
        finish();
    }
}
