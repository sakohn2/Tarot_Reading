package com.example.tarotreading;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageButton;

import com.example.lib.TarotReading;
import com.example.lib.User;

public class ReadingsActivity extends AppCompatActivity {

    /**
     * A boolean which is true when the past card has been clicked on or "flipped".
     */
    private static boolean PAST_FLIPPED = false;
    /**
     * A boolean which is true when the present card has been clicked on or "flipped".
     */
    private static boolean PRESENT_FLIPPED = false;
    /**
     * A boolean which is true when the future card has been clicked on or "flipped".
     */
    private static boolean FUTURE_FLIPPED = false;
    /**
     * The current User for this tarot reading.
     */
    private static User currentU;
    /**
     * The short name of the past card.
     */
    private static String pastShortName = "ar01";
    /**
     * The short name of the present card.
     */
    private static String presentShortName = "ar02";
    /**
     * The short name of the future card.
     */
    private static String futureShortName = "ar03";

    private static final String TAG = "MP5:Card";

    /**
     * The main method for the creation of the Readings Activity.
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity
        setContentView(R.layout.activity_reading);

        if (currentU == null || !(currentU.equals(User.currentUser))) {
            currentU = User.currentUser;
            pastShortName = TarotReading.getPastShort();
            presentShortName = TarotReading.getPresentShort();
            futureShortName = TarotReading.getFutureShort();
            Log.i(TAG, pastShortName + ", " + presentShortName + ", " + futureShortName);
        }
        boolean newReading = getIntent().getBooleanExtra("newReading", false);
        ImageButton pastButton = findViewById(R.id.card_past);
        ImageButton presentButton = findViewById(R.id.card_present);
        ImageButton futureButton = findViewById(R.id.card_future);

        if (newReading) {
            PAST_FLIPPED = false;
            PRESENT_FLIPPED = false;
            FUTURE_FLIPPED = false;
        }
        if (PAST_FLIPPED) {
            int pastCard = getResources().getIdentifier(pastShortName, "drawable", getPackageName());
            pastButton.setImageResource(pastCard);
        }
        if (PRESENT_FLIPPED) {
            int presentCard = getResources().getIdentifier(presentShortName, "drawable", getPackageName());
            presentButton.setImageResource(presentCard);
        }
        if (FUTURE_FLIPPED) {
            int futureCard = getResources().getIdentifier(futureShortName, "drawable", getPackageName());
            futureButton.setImageResource(futureCard);
        }
        findViewById(R.id.finish_reading).setOnClickListener(v -> backToSetUp());

        pastButton.setOnClickListener(v -> toCardPast());
        presentButton.setOnClickListener(v -> toCardPresent());
        futureButton.setOnClickListener(v -> toCardFuture());

    }

    /**
     * This method returns the user to the setup activity. It is called by the on click listener.
     */
    public void backToSetUp() {
        Intent backup = new Intent(this, SetupActivity.class);
        startActivity(backup);
        finish();
    }

    /**
     * Changes over to the card activity using the current present card.
     */
    public void toCardPresent() {
        // This if statement will tell the class that this card has been flipped over so that it
        // remains flipped over after exiting the cardActivity.
        if (!PRESENT_FLIPPED) {
            PRESENT_FLIPPED = true;
        }
        Intent backup = new Intent(this, CardActivity.class);
        // First we need to send info about which card should be displayed, as well as which tense
        // the card is
        backup.putExtra("tense", "present");
        backup.putExtra("shortName", presentShortName);
        startActivity(backup);
        finish();
    }

    /**
     * Changes over to the card activity using the current future card.
     */
    public void toCardFuture() {
        if (!FUTURE_FLIPPED) {
            FUTURE_FLIPPED = true;
        }
        Intent backup = new Intent(this, CardActivity.class);
        backup.putExtra("tense", "future");
        backup.putExtra("shortName", futureShortName);
        startActivity(backup);
        finish();
    }

    /**
     * Changes over to the card activity using the current past card.
     */
    public void toCardPast() {
        if (!PAST_FLIPPED) {
            PAST_FLIPPED = true;
        }
        Intent backup = new Intent(this, CardActivity.class);
        backup.putExtra("tense", "past");
        backup.putExtra("shortName", pastShortName);
        startActivity(backup);
        finish();
    }
}
