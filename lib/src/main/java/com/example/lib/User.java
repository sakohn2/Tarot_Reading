package com.example.lib;

/**
 * This class stores the user's information for easier reading by TarotReading.
 * The class only takes the input from the user (not any real data from the phone).
 * A user is set up once the "Read My Fortune" button is hit.
 */
public class User {
    /**
     * the favSeason of the User
     */
    private String favSeason;
    /**
     * The favorite color of the User.
     */
    private String favColor;
    /**
     * The star-sign of the User based on their input birthday.
     */
    private String starSign = "";

    /**
     * The current User for the program.
     */
    public static User currentUser;
    /**
     * Constructor for the User once the reading is assigned.
     * The birth-date will be changed into a star-sign and then assigned.
     *
     * @param season    the user chosen, favorite season (from a list)
     * @param color     the user chosen, favorite color (from a list)
     * @param birthDate the user entered birth-date
     */
    public User(String season, String color, String birthDate) {
        this.favSeason = season;
        this.favColor = color;
        this.setStarSign(birthDate);
    }

    /**
     * Getter for the favorite season of the user
     *
     * @return the favorite season of the user
     */
    public String getFavSeason() {
        return favSeason;
    }

    /**
     * Getter for the favorite color of the user
     *
     * @return the favorite color of the user
     */
    public String getFavColor() {
        return favColor;
    }

    /**
     * Getter for the star-sign (based on birth-date) of the user.
     *
     * @return the user's star-sign
     */
    public String getStarSign() {
        return starSign;
    }

    /**
     * This function finds the star-sign corresponding to the birth-date of the user and sets it for the User.
     *
     * @param date The birth-date of the user (from the constructor in format MM/DD/YYYY)
     */
    private void setStarSign(String date) {
        if (date == null || date.length() != 10) {
            starSign = "bad";
            return;
        }
        String[] dateSectioned = date.split("/");
        int month = Integer.parseInt(dateSectioned[0]);
        int day = Integer.parseInt(dateSectioned[1]);
        // The next few lines are checking where the birth-falls for the star-sign
        switch (month) {
            case 1:
                if (day > 20) {
                    starSign = "Aquarius";
                    return;
                } else {
                    starSign = "Capricorn";
                    return;
                }
            case 2:
                if (day > 19) {
                    starSign = "Pisces";
                    return;
                } else {
                    starSign = "Aquarius";
                    return;
                }
            case 3:
                if (day <= 20) {
                    starSign = "Pisces";
                    return;
                } else {
                    starSign = "Aries";
                    return;
                }
            case 4:
                if (day <= 20) {
                    starSign = "Aries";
                    return;
                } else {
                    starSign = "Taurus";
                    return;
                }
            case 5:
                if (day <= 21) {
                    starSign = "Taurus";
                    return;
                } else {
                    starSign = "Gemini";
                    return;
                }
            case 6:
                if (day <= 21) {
                    starSign = "Gemini";
                    return;
                } else {
                    starSign = "Cancer";
                    return;
                }
            case 7:
                if (day <= 22) {
                    starSign = "Cancer";
                    return;
                } else {
                    starSign = "Leo";
                    return;
                }
            case 8:
                if (day <= 23) {
                    starSign = "Leo";
                    return;
                } else {
                    starSign = "Virgo";
                    return;
                }
            case 9:
                if (day <= 23) {
                    starSign = "Virgo";
                    return;
                } else {
                    starSign = "Libra";
                    return;
                }
            case 10:
                if (day <= 23) {
                    starSign = "Libra";
                    return;
                } else {
                    starSign = "Scorpio";
                    return;
                }
            case 11:
                if (day <= 22) {
                    starSign = "Scorpio";
                    return;
                } else {
                    starSign = "Sagittarius";
                    return;
                }
            case 12:
                if (day <= 21) {
                    starSign = "Sagittarius";
                } else {
                    starSign = "Capricorn";
                }
        }
    }
}
