package com.example.tarotreading;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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

        // This gets the id number for the card based on its name (short name)
        int currentCardNum = getResources().getIdentifier(shortName, "drawable", getPackageName());

        // First we create the ImageView of the card, then it
        // sets the close-up of the card, the ImageView in this activity, to the card clicked on with
        // The id number previously determined.
        ImageView currentCard = findViewById(R.id.viewCard);
        currentCard.setImageResource(currentCardNum);

        // As long as a shortName was actually passed (not null) this will put the API call in motion
        if (shortName != null) {
            Log.i(TAG, shortName);
            startAPICall(shortName);
        }

        // After the API is called, the full name of the card should have updated
        // and we will change the full name textView to the name given
        // TextView fullName = findViewById(R.id.card_fullName);

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
        currentCardName = jsonObject.get("name").getAsString();
        Log.i(TAG, currentCardName);
        TextView nameView = findViewById(R.id.card_fullName);
        nameView.setText(currentCardName);
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
