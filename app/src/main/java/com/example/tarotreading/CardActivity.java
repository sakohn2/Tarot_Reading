package com.example.tarotreading;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This activity displays the picked up card along with info about it.
 * It will call the API to save the info of the card
 */
public class CardActivity extends AppCompatActivity {

    private static final String TAG = "MP5:Card";
    private static RequestQueue requestQueue;

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

        String shortName = getIntent().getStringExtra("shortName");
        if (shortName != null) {
            Log.i(TAG, shortName);
            startAPICall(shortName);
        }

        // This will call the backToReadings() method once the back button is clicked
        findViewById(R.id.back).setOnClickListener(v -> backToReadings());

    }

    void startAPICall(final String shortName) {
        try {
            Log.i(TAG, "we got here at least?");
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://rws-cards-api.herokuapp.com/api/v1/cards/" + shortName,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            apiCallDone(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.e(TAG, error.toString());
                }
            });
            jsonObjectRequest.setShouldCache(false);
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    void apiCallDone(final JSONObject response) {
        try {
            // Example of how to pull a field off the returned JSON object
            Log.i(TAG, response.get("card").toString());
        } catch (JSONException ignored) { }
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
