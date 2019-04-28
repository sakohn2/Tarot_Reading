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

    /**
     * The current User's short name for the past card.
     */
    private static String pastShort;
    /**
     * The current User's short name for the present card.
     */
    private static String presentShort;
    /**
     * The current User's short name for the future card.
     */
    private static String futureShort;

    /**
     * Returns the short name of the future card for this reading.
     * @return short name of future card
     */
    public static String getFutureShort() {
        return futureShort;
    }

    /**
     * Returns the short name of the past card for this reading.
     * @return short name of past card
     */
    public static String getPastShort() {
        return pastShort;
    }

    /**
     * Returns the short name of the present card for this reading.
     * @return short name of present card
     */
    public static String getPresentShort() {
        return presentShort;
    }

    /**
     * The set up of the reading. The constructor always set the current user as the currentUser in the User
     * class. This way, the proper tarot info for each reading will always be correct.
     */
    public TarotReading() {
        currentUser = User.currentUser;
        pastShort = someAlgorithm(currentUser);
        presentShort = someAlgorithm(currentUser);
        while (presentShort.equals(pastShort)) {
            presentShort = someAlgorithm(currentUser);
        }
        futureShort = someAlgorithm(currentUser);
        while (futureShort.equals(presentShort) || futureShort.equals(pastShort)) {
            futureShort = someAlgorithm(currentUser);
        }
    }


    /**
     * This is the one true fortune telling method.
     *
     * This method takes the User data (set earlier) spits out
     * a tarot card short name. The short name will be used by ReadingsActivity and then fed into CardActivity
     * as extra data which will then call the API and display the data.
     *
     * @param user the user whose cards are being chosen
     * @return the short name of the tarot card chosen
     */
    private String someAlgorithm(User user) {
        double magic = Math.random();
        switch (user.getFavColor()) {
            case "blue":
                magic = magic * 3;
                break;
            case "red":
                magic = magic * 5;
                break;
            case "green":
                magic = magic * 4;
                break;
            case "yellow":
                magic = magic * 9;
                break;
            case "orange":
                magic = magic * 2;
                break;
            case "purple":
                magic = magic * 7;
                break;
        }
        magic = magic * 100;
        switch (user.getStarSign()) {
            case "Aquarius":
                break;
            case "Capricorn":
                magic = magic + 1;
                break;
            case "Pisces":
                magic = magic + 2;
                break;
            case "Aries":
                magic = magic + 3;
                break;
            case "Taurus":
                magic = magic + 4;
                break;
            case "Gemini":
                magic = magic + 5;
                break;
            case "Cancer":
                magic = magic + 6;
                break;
            case "Leo":
                magic = magic + 7;
                break;
            case "Virgo":
                magic = magic + 8;
                break;
            case "Libra":
                magic = magic + 9;
                break;
            case "Scorpio":
                magic = magic + 10;
                break;
            case "Sagittarius":
                magic = magic + 11;
                break;
        }
        switch (user.getFavSeason()) {
            case "spring":
                magic = magic / 5.2;
                break;
            case "summer":
                break;
            case "winter":
                magic = magic / 3.3;
                break;
            case "fall":
                magic = magic / 2.4;
                break;
        }
        if (magic < 0) {
            magic = magic * -1;
        }
        int magicInt = (int) magic % 22;
        StringBuilder cardNum = new StringBuilder();
        if (magicInt < 10) {
            cardNum.append(0);
            cardNum.append(magicInt);
        } else {
            cardNum.append(magicInt);
        }
        return "ar" + cardNum;
    }
}
