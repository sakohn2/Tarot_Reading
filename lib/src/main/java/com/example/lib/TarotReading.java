package com.example.lib;

/**
 * This class is responsible for taking the user information and some mystical insight and turning that
 * into a classic three-card tarot reading. The cards represent the past, present, and future of the
 * user. This class with use the tarot card API here: https://github.com/ekelen/tarot-api to receive
 * information on each card.
 */
public class TarotReading {
    /**
     * The User of the current reading-run. Will contain all of the information from the setup of the User,
     * including favorite color, season, and star-sign.
     */
    private User currentUser;

    public TarotReading(User user) {
        currentUser = user;
    }
}
