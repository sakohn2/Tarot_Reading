package com.example.tarotreading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lib.TarotReading;
import com.example.lib.User;

import java.util.Calendar;

    /**
     * The initial activity that adds user input to influences the tarot reading.
     */
    public class SetupActivity extends AppCompatActivity {

        /**
         * String to store the favorite season of the user.
         */
        private String FAV_SEASON = "winter";
        /**
         * String which stores the favorite color of the user.
         */
        private String FAV_COLOR = "blue";
        /**
         * the birth_date currently in the edit_text.
         */
        private String BIRTH_DATE = "";

        /**
         * The function that loads the setup activity
         * @param savedInstanceState the instance which starts the loading of the setup
         */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_setup);

            // Colors spinner
            Spinner colorSpinner = findViewById(R.id.fav_colors);
            ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
                    R.array.fav_colors, android.R.layout.simple_spinner_item);
            colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            colorSpinner.setAdapter(colorAdapter);

            // Listen for when an item is selected and then store the value of the choice in the FAV_COLOR
            // string value.
            colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            FAV_COLOR = "blue";
                            break;
                        case 1:
                            FAV_COLOR = "red";
                            break;
                        case 2:
                            FAV_COLOR = "green";
                            break;
                        case 3:
                            FAV_COLOR = "yellow";
                            break;
                        case 4:
                            FAV_COLOR = "orange";
                            break;
                        case 5:
                            FAV_COLOR = "purple";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            // Seasons Spinner
            Spinner seasonSpinner = findViewById(R.id.fav_seasons);
            ArrayAdapter<CharSequence> seasonAdapter = ArrayAdapter.createFromResource(this,
                    R.array.fav_seasons, android.R.layout.simple_spinner_item);
            seasonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            seasonSpinner.setAdapter(seasonAdapter);

            // Listen for when an item is selected and then store the value of the choice in the FAV_SEASON
            // string value.
            seasonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            FAV_SEASON = "winter";
                            break;
                        case 1:
                            FAV_SEASON = "spring";
                            break;
                        case 2:
                            FAV_SEASON = "summer";
                            break;
                        case 3:
                            FAV_SEASON = "fall";
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) { }
            });

            // Birth date text editor
            // This block of code allows the format message DDMMYYY be replaced as the user types
            final EditText date = findViewById(R.id.birth_date);
            final TextWatcher tw = new TextWatcher() {
                private String current = "";
                private String mmddyyyy = "MMDDYYYY";
                private Calendar cal = Calendar.getInstance();
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!s.toString().equals(current)) {
                        String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                        String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                        int cl = clean.length();
                        int sel = cl;
                        for (int i = 2; i <= cl && i < 6; i += 2) {
                            sel++;
                        }
                        //Fix for pressing delete next to a forward slash
                        if (clean.equals(cleanC)) sel--;

                        if (clean.length() < 8){
                            clean = clean + mmddyyyy.substring(clean.length());
                        }else{
                            //This part makes sure that when we finish entering numbers
                            //the date is correct, fixing it otherwise
                            int mon  = Integer.parseInt(clean.substring(0,2));
                            int day  = Integer.parseInt(clean.substring(2,4));
                            int year = Integer.parseInt(clean.substring(4,8));

                            mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                            cal.set(Calendar.MONTH, mon-1);
                            year = (year<1903)?1903:(year>2019)?2019:year;
                            cal.set(Calendar.YEAR, year);
                            // ^ first set year for the line below to work correctly
                            //with leap years - otherwise, date e.g. 29/02/2012
                            //would be automatically corrected to 28/02/2012

                            day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                            clean = String.format("%02d%02d%02d",mon, day, year);
                        }

                        clean = String.format("%s/%s/%s", clean.substring(0, 2),
                                clean.substring(2, 4),
                                clean.substring(4, 8));

                        sel = sel < 0 ? 0 : sel;
                        current = clean;
                        date.setText(current);
                        date.setSelection(sel < current.length() ? sel : current.length());
                    }
                    BIRTH_DATE = date.getText().toString();
                }
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void afterTextChanged(Editable s) {}
            };
            date.addTextChangedListener(tw);


            // Button onClickListener
            findViewById(R.id.start_button).setOnClickListener(v -> startReading());
        }
        void startReading() {
            // Check if a birth-date was entered (and completely entered)
            if (BIRTH_DATE.equals("") || BIRTH_DATE.contains("M") || BIRTH_DATE.contains("D") || BIRTH_DATE.contains("Y")) {
                Toast.makeText(this, "Please enter your birthday~", Toast.LENGTH_SHORT).show();
                return;
            }
            // Set up a User to pass to the tarotReading (if birthday was entered)
            User.currentUser = new User(FAV_SEASON, FAV_COLOR, BIRTH_DATE);
            // This updates the static variable with the new currentUser.
            TarotReading newReading = new TarotReading();
            // Move to the activity_reading
            Intent readingIntent = new Intent(this, ReadingsActivity.class);
            readingIntent.putExtra("newReading", true);
            startActivity(readingIntent);
            finish();
        }
    }