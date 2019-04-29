package com.example.tarotreading;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


/**
 * This activity displays the picked up card along with info about it.
 * It will call the API to save the info of the card
 */
public class CardActivity extends AppCompatActivity {

    private static final String TAG = "MP5:Card";
    private static RequestQueue requestQueue;

    private String currentCardName = "The ";
    private String currentCardDesc = "this should be the description";
    private String currentKeyWords = "This should be the key words for the card";
    private boolean up = true;
    private String tense = "past";
    /**
     * Run this activity when a card is chosen
     *
     * @param savedInstanceState unused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);

        // Set the layout for this activity
        setContentView(R.layout.activity_card);

        Log.i(TAG, "We opened the card");

        // This gets the short name of the chosen card from ReadingsActivity when it was passed through
        String shortName = getIntent().getStringExtra("shortName");

        // Get whether the card is upright or not
        up = getIntent().getBooleanExtra("up", true);
        // getting the tense to use in the description of the card
        tense = getIntent().getStringExtra("tense");

        // This gets the id number for the card based on its name (short name)
        int currentCardNum = getResources().getIdentifier(shortName, "drawable", getPackageName());

        // First we create the ImageView of the card, then it
        // sets the close-up of the card, the ImageView in this activity, to the card clicked on with
        // The id number previously determined.
        ImageView currentCard = findViewById(R.id.viewCard);
        currentCard.setImageResource(currentCardNum);
        if (!up) {
            currentCard.setScaleY(-1f);
        }

        // As long as a shortName was actually passed (not null) this will put the API call in motion
        if (shortName != null) {
            Log.i(TAG, shortName);
            startAPICall(shortName);
        }

        // This will call the backToReadings() method once the back button is clicked
        findViewById(R.id.back).setOnClickListener(v -> backToReadings());
    }

    void startAPICall(final String shortName) {
        try {
            String URL = "https://rws-cards-api.herokuapp.com/api/v1/cards/" + shortName;
            Log.i(TAG, "Trying to call API to get String");
            StringRequest stringRequest = new StringRequest(
                    Request.Method.GET, URL, this::apiCallDone, this::handleApiError) {
            };
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the error by logging it.
     * @param error the error that came up from trying to call the API
     */
    void handleApiError(final VolleyError error) {
        // On failure just add the error to the log.
        Log.i(TAG, "Error: " + error.toString());
    }
    void apiCallDone(final String response) {
        try {
            // Example of how to pull a field off the returned JSON object
            Log.i(TAG, "Trying to finish API call");
            Log.i(TAG, response);
            getCardInfo(response);
        } catch (Exception ignored) { }
    }

    /**
     * The apiCallDone will call this method in order to parse out the JSON response into the meaningful
     * bits of data we want from the API call.
     *
     * @param response the JSONObject from the API call
     */
    private void getCardInfo(final String response) {
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(response).getAsJsonObject().getAsJsonObject("card");
        // The name of the current card is set up here
        currentCardName = jsonObject.get("name").getAsString();
        Log.i(TAG, currentCardName);
        if (!up) {
            currentCardName += "--Reversed";
        }
        TextView nameView = findViewById(R.id.card_fullName);
        nameView.setText(currentCardName);
        // Set up the description
        currentCardDesc = jsonObject.get("desc").getAsString();
        TextView descView = findViewById(R.id.full_description);
        descView.setText(currentCardDesc);
        String dirn;
        if (up) {
            currentKeyWords = jsonObject.get("meaning_up").getAsString();
            dirn = "rightside-up.";
        } else {
            currentKeyWords = jsonObject.get("meaning_rev").getAsString();
            dirn = "upside-down.";
        }
        TextView keyWords = findViewById(R.id.key_words);
        keyWords.setText(currentKeyWords);
        // Last part of explanation will hold the meaning for the particular tense, so it makes more
        // sense as a tarot reading.
        String lastPartExpln = "";
        if (tense.equals("past")) {
            lastPartExpln = " They represent either your past or some event that led you to where you are now:";
        } else if (tense.equals("present")) {
            lastPartExpln = " They represent either how your life is now or something " +
                    "around your current situation:";
        } else {
            lastPartExpln = " They represent either your future or some event " +
                    "that will lead you into your future:";
        }

        TextView expln = findViewById(R.id.word_expln);
        expln.setText(Html.fromHtml("These words are the meaning of this card when it appears "
                + dirn + lastPartExpln));

    }
    /**
     * This function is built to be activated on the click of the back button to send the user back
     * to the three card spread reading with the correct cards and the correct turnovers.
     */
    public void backToReadings() {
        Intent backup = new Intent(this, ReadingsActivity.class);

        startActivity(backup);
        finish();
    }
}
