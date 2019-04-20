package com.example.tarotreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity
        setContentView(R.layout.activity_card);

        // This will call the backToReadings() method once the back button is clicked
        findViewById(R.id.back).setOnClickListener(v -> backToReadings());

    }

    /**
     * This function is built to be activated on the click of the back button to send the user back
     * to the three card spread reading with the correct cards and the correct turnovers.
     */
    public void backToReadings() {
        Intent backup = new Intent(this, ReadingsActivity.class);
        // Extra data needs to be send (essentially, the tarot reading must be remembered, whether it's the string card names
        // or figuring out how to send a new class data type through the extra data.
        startActivity(backup);
        finish();
    }
}
